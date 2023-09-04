package com.yanolja.clone.lego.service;

import com.yanolja.clone.lego.entity.Booking;
import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.BusinessImage;
import com.yanolja.clone.lego.entity.RoomImage;
import com.yanolja.clone.lego.repository.BusinessImageRepository;
import com.yanolja.clone.lego.repository.BusinessRepository;
import com.yanolja.clone.lego.repository.RoomImageRepository;
import com.yanolja.clone.lego.repository.RoomRepository;
import com.yanolja.clone.lego.util.Path;
import com.yanolja.clone.lego.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessService {
    // BusinessRepository 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessRepository businessRepository;

    // BusinessImageRepository 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessImageRepository businessImageRepository;

    // business 유저 검색
    public Business findBusiness(String id){
        // 파라미터로 넘어온 id 값으로 business 객체 검색
        Business business = businessRepository.findById(id);
        // 검색된 business 객체 반환
        return business;
    }

    // business 유저 검색
    public Business findBusiness(Long idx){
        // 파라미터로 넘어온 idx 값으로 business 객체 검색
        Business business = businessRepository.findByIdx(idx);
        // 검색된 business 객체 반환
        return business;
    }

    // 숙소 리스트 검색(결제가 가장 많은 순)
    public List<Business> businessList(){
        // business 객체들 중 결제가 많은 순으로 정렬 후 4개만 검색
        List<Business> businessList = businessRepository.findBusinessListOrderByBookingCount();
        // 검색된 business 객체를 가지고 있는 리스트 반환
        return businessList;
    }

    public List<Business> findPlaceCategory(String category){
        List<Business> businessList = businessRepository.findByCategory(category);
        return businessList;
    }

    public List<BusinessImage> findPlaceCategoryImage(String category){
        List<Business> businessList = findPlaceCategory(category);
        List<BusinessImage> businessImageList = new ArrayList<>();
        for(int i = 0; i < businessList.size(); i++){
            BusinessImage businessImage = businessImageRepository.findByBusinessIdxLimitOne(businessList.get(i).getIdx());
            businessImageList.add(businessImage);
        }
        return businessImageList;
    }

    // 사진 추가
    public String imageAdd(BusinessImage businessImage){
        MultipartFile imageFile = businessImage.getImageFile();
        // 이미지 파일이 아니면 파일을 생성하지 않게 설정해야함
        String fileName = imageFile.getOriginalFilename();
        // 파일의 확장자를 확인하기 위한 문자열 생성
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 파일의 확장자가 이미지인지 아닌지 판단할 변수 생성
        boolean isImage = false;
        // 미리 지정해둔 이미지 타입이 아니면 isImage 는 true 로 변경되지 않음
        for(int i = 0; i < Type.IMAGE_TYPE.length; i++){
            if(fileExt.equalsIgnoreCase(Type.IMAGE_TYPE[i])){
                isImage = true;
            }
        }
        // isImage 가 true 가 아니라면 이미지 파일이 아니므로 noImage 을 리턴
        if(isImage != true){
            return "noImage";
        }
        String image = null;
        // 만약 이미지 파일이 존재한다면
        if(!imageFile.isEmpty()){
            // 이미지 파일의 이름을 프로필 사진 이름으로 지정
            image = imageFile.getOriginalFilename();
            // 이미지 파일 객체 생성하기
            File saveFile = new File(Path.BUSINESS_IMAGE_PATH, image);
            // 만약 파일이 없다면
            if(!saveFile.exists()){
                // 상위 폴더 생성 및 파일 생성
                saveFile.mkdirs();
            }else{
                // 파일이 있다면 현재 시간을 이용해 이름 변경하기
                long time = System.currentTimeMillis();
                image = String.format("%d-%s", time, image);
                saveFile = new File(Path.BUSINESS_IMAGE_PATH, image);
            }
            try {
                imageFile.transferTo(saveFile);
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        businessImage.setImage(image);
        BusinessImage afterBusinessImage = businessImageRepository.save(businessImage);
        if(afterBusinessImage == null){
            return "no";
        }else{
            return "yes";
        }
    }

    // 전체 숙소 사진 리스트 검색
    public List<BusinessImage> findBusinessImageList(){
        List<BusinessImage> businessImageList = businessImageRepository.findAll();
        return businessImageList;
    }

    // 찾고싶은 숙소 사진 리스트 검색
    public List<BusinessImage> findBusinessImageList(Long businessIdx){
        List<BusinessImage> businessImageList = businessImageRepository.findByBusinessIdx(businessIdx);
        return businessImageList;
    }

    // 이미지 삭제
    public String imageDelete(BusinessImage businessImage){
        // 삭제할 이미지 객체를 검색한다.
        BusinessImage deleteBusinessImage = businessImageRepository.findByIdx(businessImage.getIdx());
        // 삭제할 이미지 파일을 파일 객체로 생성
        File imageFile = new File(Path.BUSINESS_IMAGE_PATH + "/" + deleteBusinessImage.getImage());
        // 이미지 파일이 객체로 생성 되었는지 확인
        if(imageFile.exists()){
            // 이미지 파일을 삭제하고 결과값을 저장
            boolean isDelete = imageFile.delete();
            // 이미지 정보를 데이터페이스에서 삭제
            businessImageRepository.delete(deleteBusinessImage);
            // 이미지 정보가 잘 삭제되었는지 삭제한 이미지의 idx 로 객체 검색
            BusinessImage afterDeleteBusinessImage = businessImageRepository.findByIdx(deleteBusinessImage.getIdx());
            // 이미지 파일을 삭제한 결과값이 true 이고 이미지 정보가 데이터베이스에 없는지 확인
            if(isDelete == true && afterDeleteBusinessImage == null){
                return "yes";
            }else {
                return "no";
            }
        }else{
            return "noImage";
        }
    }
}

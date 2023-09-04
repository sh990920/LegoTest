package com.yanolja.clone.lego.service;

import com.yanolja.clone.lego.entity.Booking;
import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.Member;
import com.yanolja.clone.lego.repository.BookingRepository;
import com.yanolja.clone.lego.repository.BusinessRepository;
import com.yanolja.clone.lego.repository.MemberRepository;
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
public class MyPageService {
    // MemberRepository 를 사용하기 위해 어노테이션으로 연결
    @Autowired
    MemberRepository memberRepository;

    // BookingRepository 를 사용하기 위해 어노테이션으로 연결
    @Autowired
    BookingRepository bookingRepository;

    // BusinessRepository 를 사용하기 위해 어노테이션으로 연결
    @Autowired
    BusinessRepository businessRepository;

    // member 유저 검색
    public Member findMember(String id){
        // 파라미터로 들어온 id 값으로 member 유저 검색
        Member member = memberRepository.findById(id);
        return member;
    }

    // member 프로필사진 변경
    public String profileImageUpdate(Member member, String id){
        // 변경할 멤버 객체 검색
        Member updateMember = memberRepository.findById(id);
        // 이미지 파일 받아오기
        MultipartFile imageFile = member.getImageFile();
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
        // 프로필사진 이름 받아오기
        String profileImage = member.getProfileImage();
        // 만약 이미지 파일이 존재한다면
        if(!imageFile.isEmpty()){
            // 이미지 파일의 이름을 프로필 사진 이름으로 지정
            profileImage = imageFile.getOriginalFilename();
            // 이미지 파일 객체 생성하기
            File saveFile = new File(Path.PROFILE_IMAGE_PATH, profileImage);
            // 만약 파일이 없다면
            if(!saveFile.exists()){
                // 상위 폴더 생성 및 파일 생성
                saveFile.mkdirs();
            }else{
                // 파일이 있다면 현재 시간을 이용해 이름 변경하기
                long time = System.currentTimeMillis();
                profileImage = String.format("%d-%s", time, profileImage);
                saveFile = new File(Path.PROFILE_IMAGE_PATH, profileImage);
            }
            try {
                imageFile.transferTo(saveFile);
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        // 변경할 멤버 객체에 프로필 사진 이름 변경 후 저장
        updateMember.setProfileImage(profileImage);
        Member afterUpdateMember = memberRepository.save(updateMember);
        if(afterUpdateMember == null){
            return "no";
        }else {
            return "yes";
        }
    }

    // booking 리스트 검색
    public List<Booking> bookingCheckList(Long memberIdx){
        List<Booking> bookingList = bookingRepository.findByMemberIdx(memberIdx);
        return bookingList;
    }

    // booking 리스트에 관련된 business 리스트 생성
    public List<Business> businessCheckList(Long memberIdx){
        List<Booking> bookingList = bookingCheckList(memberIdx);
        List<Business> businessList = new ArrayList<>();
        for(int i = 0; i < bookingList.size(); i++){
            Business business = businessRepository.findByIdx(bookingList.get(i).getBusinessIdx());
            businessList.add(business);
        }
        return businessList;
    }

    // 닉네임 중복 검사
    public String nicknameCheck(String nickname){
        Member member = memberRepository.findByNickname(nickname);
        if(member != null){
            return "no";
        }else{
            return "yes";
        }
    }

    // 닉네임 변경
    public void nicknameChange(Member member){
        memberRepository.updateNickname(member.getNickname(), member.getIdx());
    }
}

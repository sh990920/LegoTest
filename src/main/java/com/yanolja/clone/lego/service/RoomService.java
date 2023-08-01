package com.yanolja.clone.lego.service;

import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.Room;
import com.yanolja.clone.lego.entity.RoomImage;
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
public class RoomService {
    // RoomRepository 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomImageRepository roomImageRepository;

    // BusinessService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessService businessService;

    // business 의 id 로 숙소의 방 리스트 검색
    public List<Room> roomList(String id){
        // 파라미터로 들어온 id 로 business 객체 검색
        Business business = businessService.findBusiness(id);
        // business 객체의 idx 로 숙소에 맞는 방 리스트를 검색
        List<Room> roomList = roomRepository.findByBusinessIdx(business.getIdx());
        // 감색한 방 리스트 리턴
        return roomList;
    }

    // business 의 idx 로 숙소의 방 리스트 검색
    public List<Room> roomList(Long businessIdx){
        // businessIdx 로 숙소에 맞는 방 리스트룰 검색
        List<Room> roomList = roomRepository.findByBusinessIdx(businessIdx);
        // 검색한 방 리스트 리턴
        return roomList;
    }

    // 방 추가
    public String roomAdd(Room room){
        // 파라미터로 들어온 room 객체를 데이터베이스에 저장
        Room addRoom = roomRepository.save(room);
        // 데이터베이스에 저장되고 객체를 가져왔을 때 값이 제대로 저장되지 않았다면 'no' 라는 값을 리턴
        if(addRoom == null){
            return "no";
        }
        // 값이 제대로 저장되었다면 'yes' 라는 값을 리턴
        return "yes";
    }

    // 방 검색
    public Room findRoom(Long idx){
        // 파라미터로 들어온 idx 로 room 객체 검색
        Room room = roomRepository.findByIdx(idx);
        // 검색된 room 객체 리턴
        return room;
    }

    // 방 업데이트
    public String roomUpdate(Room room){
        // 파라미터로 들어온 room 객체로 데이터베이스에 저장된 기존의 방 정보를 수정
        Room updateRoom = roomRepository.save(room);
        // 데이터베이스에 저장되고 객체를 가져왔을 때 값이 제대로 저장되지 않았다면 'no' 라는 값을 리턴
        if(updateRoom == null){
            return "no";
        }
        // 값이 제대로 저장되었다면 'yes' 라는 값을 리턴
        return "yes";
    }

    // 방 삭제
    public String roomDelete(Room room){
        // 파라미터로 들어온 room 객체의 idx 로 삭제할 room 객체 검색
        Room deleteRoom = findRoom(room.getIdx());
        // 검색된 room 객체를 데이터베이스에서 삭제
        roomRepository.delete(deleteRoom);
        // 데이터베이스에서 값이 잘 삭제 되었는지 확인하기 위해서 다시 room 객체 검색
        Room isDelete = findRoom(room.getIdx());
        // 만약 객체가 검색이 되어 값이 있다면 삭제가 제대로 이루어지지 않은것이기 때문에 'no' 라는 값을 리턴
        if(isDelete != null){
            return "no";
        }
        // 객체가 검색되지 않아 값이 비어있다면 삭제가 제대로 이루어진 것이기 때문에 'yes' 라는 값을 리턴
        return "yes";
    }

    // 이미지 추가
    public String imageAdd(RoomImage roomImage){
        MultipartFile imageFile = roomImage.getImageFile();
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
            File saveFile = new File(Path.ROOM_IMAGE_PATH, image);
            // 만약 파일이 없다면
            if(!saveFile.exists()){
                // 상위 폴더 생성 및 파일 생성
                saveFile.mkdirs();
            }else{
                // 파일이 있다면 현재 시간을 이용해 이름 변경하기
                long time = System.currentTimeMillis();
                image = String.format("%d-%s", time, image);
                saveFile = new File(Path.ROOM_IMAGE_PATH, image);
            }
            try {
                imageFile.transferTo(saveFile);
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        roomImage.setImage(image);
        RoomImage afterRoomImage = roomImageRepository.save(roomImage);
        if(afterRoomImage == null){
            return "no";
        }else{
            return "yes";
        }
    }

    // 전체 방 사진 검색
    public List<RoomImage> findRoomImageList(){
        List<RoomImage> roomImageList = roomImageRepository.findAll();
        return roomImageList;
    }

    // 숙소 상세보기에서 필요한 메서드
    public List<RoomImage> findRoomImageList(Long businessIdx, Long roomIdx){
        List<RoomImage> roomImageList = roomImageRepository.findByBusinessIdxAndRoomIdx(businessIdx, roomIdx);
        return roomImageList;
    }

    public List<List> findDetailRoomImageList(Long businessIdx){
        List<List> roomImageListList = new ArrayList<>();
        List<Room> roomList = roomRepository.findByBusinessIdx(businessIdx);
        for(int i = 0; i < roomList.size(); i++){
            List<RoomImage> roomImageList = findRoomImageList(businessIdx, roomList.get(i).getIdx());
            roomImageListList.add(roomImageList);
        }
        return roomImageListList;
    }

    // 찾고싶은 방 사진 검색
    public List<RoomImage> findRoomImageList(Long roomIdx){
        List<RoomImage> roomImageList = roomImageRepository.findByRoomIdx(roomIdx);
        return roomImageList;
    }

    // 숙소 방 전체 사진 검색
    public List<RoomImage> findRoomImageList2(Long businessIdx){
        List<RoomImage> roomImageList = roomImageRepository.findByBusinessIdx(businessIdx);
        return roomImageList;
    }

    // 이미지 삭제
    public String imageDelete(RoomImage roomImage){
        RoomImage deleteRoomImage = roomImageRepository.findByIdx(roomImage.getIdx());
        File imageFile = new File(Path.ROOM_IMAGE_PATH + "/" + deleteRoomImage.getImage());
        if(imageFile.exists()){
            boolean isDelete = imageFile.delete();
            roomImageRepository.delete(deleteRoomImage);
            RoomImage afterDeleteRoomImage = roomImageRepository.findByIdx(deleteRoomImage.getIdx());
            if(isDelete == true && afterDeleteRoomImage == null){
                return "yes";
            }else {
                return "no";
            }
        }else {
            return "noImage";
        }
    }
}

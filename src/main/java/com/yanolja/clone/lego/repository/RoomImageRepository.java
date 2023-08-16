package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Object> {
    // roomIdx 로 RoomImage 객체들을 검색한 이후 결과를 List 로 반환
    List<RoomImage> findByRoomIdx(Long roomIdx);

    // businessIdx 와 roomIdx 로 RoomImage 객체들을 검색한 이후 결과를 List 로 반환
    List<RoomImage> findByBusinessIdxAndRoomIdx(Long businessIdx, Long roomIdx);

    // businessIdx 로 RoomImage 객체들을 검색한 이후 결과를 List 로 반환
    List<RoomImage> findByBusinessIdx(Long businessIdx);

    // idx 로 RoomImage 객체 검색
    RoomImage findByIdx(Long idx);
}

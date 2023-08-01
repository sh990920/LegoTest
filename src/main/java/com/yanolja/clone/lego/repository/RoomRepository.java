package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Room;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Object> {
    // idx 로 Room 객체 검색
    Room findByIdx(Long idx);

    // businessIdx 로 Room 객체들을 검색한 이후 결과를 List 로 반환
    List<Room> findByBusinessIdx(Long businessIdx);

    int countByBusinessIdx(Long businessIdx);


}

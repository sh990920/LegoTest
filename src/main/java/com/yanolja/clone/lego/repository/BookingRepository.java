package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Object> {
    // businessIdx 와 roomIdx 로 Booking 객체들을 검색한 이후 결과를 List 로 반환
    List<Booking> findByBusinessIdxAndRoomIdx(Long businessIdx, Long roomIdx);

    // businessIdx 로 Booking 객체들을 검색한 이후 결과를 List 로 반환
    List<Booking> findByBusinessIdx(Long businessIdx);

    // roomIdx 로 Booking 객체들을 검색한 이후 결과를 List 로 반환
    List<Booking> findByRoomIdx(Long roomIdx);

    // memberIdx 로 Booking 객체들을 검색한 이후 결과를 List 로 반환
    List<Booking> findByMemberIdx(Long memberIdx);

    Booking findByIdx(Long idx);
}

package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Object> {
    List<Booking> findByBusinessIdxAndRoomIdx(Long businessIdx, Long roomIdx);

    List<Booking> findByBusinessIdx(Long businessIdx);

    List<Booking> findByRoomIdx(Long roomIdx);
}

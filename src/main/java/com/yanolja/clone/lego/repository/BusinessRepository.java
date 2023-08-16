package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Object> {
    // id 로 Business 객체 검색
    Business findById(String id);

    // idx 로 Business 객체 검색
    Business findByIdx(Long idx);

    Business findByPlaceName(String placeName);

    Business findByPlaceCall(String placeCall);

    List<Business> findByAuthentication(int authentication);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Business SET authentication=:authentication WHERE idx=:idx", nativeQuery = true)
    void updateAuthentication(@Param("authentication") int authentication, @Param("idx") Long idx);
}

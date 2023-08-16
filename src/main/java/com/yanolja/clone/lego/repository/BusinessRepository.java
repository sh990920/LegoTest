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

    // placeName 으로 Business 객체 검색
    Business findByPlaceName(String placeName);

    // placeCall 로 Business 객체 검색
    Business findByPlaceCall(String placeCall);

    // authentication 로 Business 객체들을 검색한 이후 결과를 List 로 반환
    List<Business> findByAuthentication(int authentication);

    // idx 로 찾은 Business 객체의 authentication 을 변경한 이후 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE Business SET authentication=:authentication WHERE idx=:idx", nativeQuery = true)
    void updateAuthentication(@Param("authentication") int authentication, @Param("idx") Long idx);
}

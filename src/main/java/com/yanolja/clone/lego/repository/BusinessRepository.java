package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Object> {
    // id 로 Business 객체 검색
    Business findById(String id);

    // idx 로 Business 객체 검색
    Business findByIdx(Long idx);

    Business findByPlaceName(String placeName);

    Business findByPlaceCall(String placeCall);
}

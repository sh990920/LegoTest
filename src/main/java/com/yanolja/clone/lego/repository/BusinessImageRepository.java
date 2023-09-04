package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.BusinessImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessImageRepository extends JpaRepository<BusinessImage, Object> {
    // businessIdx 로 BusinessImage 객체들을 검색한 이후 결과를 List 로 반환
    List<BusinessImage> findByBusinessIdx(Long businessIdx);

    // idx 로 BusinessImage 객체 검색
    BusinessImage findByIdx(Long idx);

    // 원하는 이미지 한장 뽑아오기
    @Query(value = "select * from BusinessImage where businessIdx=:businessIdx Limit 1;", nativeQuery = true)
    BusinessImage findByBusinessIdxLimitOne(@Param("businessIdx") Long businessIdx);
}

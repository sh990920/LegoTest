package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Object> {
    // id 로 admin 객체 검색
    Admin findById(String id);
}

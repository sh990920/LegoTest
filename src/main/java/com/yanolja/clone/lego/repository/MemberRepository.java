package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Object> {
    // id 로 Member 객체 검색
    Member findById(String id);

    // idx 로 Member 객체 검색
    Member findByIdx(Long idx);
}

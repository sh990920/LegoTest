package com.yanolja.clone.lego.repository;

import com.yanolja.clone.lego.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Object> {
    // id 로 Member 객체 검색
    Member findById(String id);

    // idx 로 Member 객체 검색
    Member findByIdx(Long idx);

    // 닉네임으로 Member 객체 검색
    Member findByNickname(String nickname);

    // 핸드폰 번호로 Member 객체 검색
    Member findByPhone(String phone);

    // idx 로 찾은 Member 객체의 nickname 을 변경한 이후 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE Member SET nickname=:nickname WHERE idx=:idx", nativeQuery = true)
    void updateNickname(@Param("nickname") String nickname, @Param("idx") Long idx);
}

package com.yanolja.clone.lego.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity(name = "Member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Member {
    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 를 설정
    private Long idx; // PK

    @Column(length = 50, nullable = false, unique = true)
    private String id; // id

    @Column(length = 255)
    private String password; // 비밀번호

    @Column(length = 20, nullable = false)
    private String name; // 이름

    @Column(length = 20, nullable = false, unique = true)
    private String nickname; // 닉네임

    @Column(length = 20, nullable = false)
    private String phone; // 전화번호

    @Column(length = 50, nullable = false)
    private String rollName; // 유저 권한

    @Column(length = 200)
    private String profileImage; // 유저 프로필 사진

    @Transient // 데이터베이스 컬럼으로 사용하지 않기 위해서 어노테이션 작성
    private MultipartFile imageFile; // 이미지 객체를 받아오기 위한 변수(데이터베이스 컬럼으로 사용하지 않음)

}

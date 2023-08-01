package com.yanolja.clone.lego.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity(name = "RoomImage")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoomImage {

    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 를 설정
    private Long idx; // pk

    @Column(nullable = false)
    private Long roomIdx; // 포린키 연결(Room 클래스의 idx 와)

    @Column(nullable = false)
    private Long businessIdx; // 포린키 연결(Business 클래스의 idx 와)

    @Column(length = 255, nullable = false)
    private String image; // 이미지 저장 경로

    @Transient // 데이터베이스 컬럼으로 사용하지 않기 위해서 어노테이션 작성
    private MultipartFile imageFile; // 이미지 객체를 받아오기 위한 변수(데이터베이스 컬럼으로 사용하지 않음)
}

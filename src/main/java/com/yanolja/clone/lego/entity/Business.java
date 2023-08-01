package com.yanolja.clone.lego.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Business")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Business {
    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 를 설정
    private Long idx; // PK

    @Column(length = 50, nullable = false, unique = true)
    private String id; //id

    @Column(length = 255, nullable = false)
    private String password; // 비밀번호

    @Column(length = 30, nullable = false)
    private String name; // 사업자 이름

    @Column(length = 20, nullable = false, unique = true)
    private String placeCall; // 사업자 전화번호

    @Column(length = 50, nullable = false)
    private String placeName; // 숙소 이름

    @Column(length = 255, nullable = false)
    private String placeAddress; // 숙소 주소

    @Column(length = 20, nullable = false)
    private String category; // 숙소 종류
    // 모텔, 호텔/리조트, 펜션/풀빌라, 캠핑/글램핑, 게스트하우스/한옥

    @Column(nullable = false)
    private int authentication; // 가입 승인
    // 0 - 가입 거절, 1 - 가입 승인 대기중, 2 - 가입 승인

    @Column(nullable = false)
    private int parkingOption; // 주차 옵션
    // 0 - 없음, 1 - 있음

    @Column(nullable = false)
    private int smokingOption; // 흡연 옵션
    // 0 - 불가능, 1 - 쌉가능

    @Column(nullable = false)
    private int wifiOption; // 와이파이 옵션
    // 0 - 없음, 1 - 있음

    @Column(nullable = false)
    private int ottOption; // OTT 옵션
    // 0 - 없음, 1 - 넷플릭스, 2 - 웨이브, 3- 티빙

    @Column(length = 50, nullable = false)
    private String rollName; // 유저 권한
}

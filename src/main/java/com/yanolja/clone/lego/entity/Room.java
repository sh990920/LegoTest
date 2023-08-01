package com.yanolja.clone.lego.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Room")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 를 설정
    private Long idx; // PK

    @Column(length = 20, nullable = false)
    private String roomName; // 방 이름

    @Column(nullable = false)
    private Long businessIdx; // FK(bussiness 의 idx)

    @Column(nullable = false)
    private int price; // 방 가격

    @Column(nullable = false)
    private int minPersonnel; // 최소 인원

    @Column(nullable = false)
    private int maxPersonnel; // 최대 인원

    @Column(length = 20, nullable = false)
    private String checkInTime; // 체크인 시간

    @Column(length = 20, nullable = false)
    private String checkOutTime; // 체크아웃 시간

}

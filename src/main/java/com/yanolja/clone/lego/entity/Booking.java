package com.yanolja.clone.lego.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Booking")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 를 설정
    private Long idx; // pk

    @Column(nullable = false)
    private Long memberIdx; // 포린키 연결(Member 클래스의 idx 와)

    @Column(nullable = false)
    private Long businessIdx; // 포린키 연결(Business 클래스의 idx 와)

    @Column(nullable = false)
    private Long roomIdx; // 포린키 연결(Room 클래스의 idx 와)

    @Column(length = 200, nullable = false)
    private String paymentId; // 사용자가 사용할 id

    @Column(length = 50, nullable = false)
    private String pgProvider; // 결제사 이름

    @Column(length = 300)
    private String payMethod; // 결제 수단

    @Column(length = 30, nullable = false)
    private String checkInDate; // 체크인 날짜

    @Column(length = 30, nullable = false)
    private String checkOutDate; // 체크아웃 날짜

    @Column(nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int personnel; // 인원 수

    @Column(nullable = false)
    private String buyerName; // 구매자 이름

    @Column(length = 20, nullable = false)
    private String buyerTel; // 구매자 전화번호

    @Column(nullable = false)
    private int isPaid;
    // 0 : 결제 완료
    // 1 : 이용 중 -> 입실
    // 2 : 이용 완료 -> 퇴실
    // 3 : 환불 완료
}

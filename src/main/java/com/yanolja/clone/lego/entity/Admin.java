package com.yanolja.clone.lego.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Admin")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Admin {
    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 를 설정
    private Long idx; // PK

    @Column(length = 50, nullable = false, unique = true)
    private String id; // id

    @Column(length = 30, nullable = false)
    private String nickname; // 닉네임은 관리자로 통일

    @Column(length = 255, nullable = false, unique = true)
    private String password; // 비밀번호

    @Column(length = 50, nullable = false)
    private String rollName; // 권한은 'ADMIN' 으로 설정
}

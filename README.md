# LegoTest
<img src="https://img.shields.io/badge/Springboot-6DB33F?style=flat&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS-232F3E?style=flat&logo=amazonaws&logoColor=white"/>
<img src="https://img.shields.io/badge/BootStrap-7952B3?style=flat&logo=bootstrap&logoColor=white"/>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=white"/>
<img src="https://img.shields.io/badge/PostMan-FF6C37?style=flat&logo=postman&logoColor=white"/>

## 숙박 예약 웹 페이지 만들기
### 2023.07 ~ 2023.10

## 목차
- [기능](#기능)
- [환경설정](#환경설정)
- [데이터베이스](#데이터베이스)

## 기능
### 1. 사용자
- 회원가입 및 로그인
- 숙박업소 예약
- 마이페이지

### 2. 사업자
- 회원가입 및 로그인
- 숙소 목록 생성(방 생성)
- 사용자 입실, 퇴실, 환불 관리

### 3. 관리자
- 회원가입 및 로그인
- 사업자 회원가입 승인

## 환경설정
### 운영체제
- MacOS
- Ubuntu

### 사용 언어
- Java
    - version : 1.8.0
- HTML5
- CSS3
- JavaScript

### 프레임워크
- Spring-Boot
    - version : 2.7.13
- BootStrap

### DB
- MySQL
    - version : 8.1.0 for macos12.6 on arm64 (HomeBrew)
### IDE 및 툴
- IntelliJ
- VS_Code
- PostMan

## 데이터베이스
- [Admin](#Admin)
- [Booking](#Booking)
- [Business](#Business)
- [BusinessImage](#BusinessImage)
- [Member](#Member)
- [Room](#Room)
- [RoomImage](#RoomImage)

### Admin
    CREATE TABLE Admin(
        idx BIGINT AUTO_INCREMENT PRIMARY KEY,
        id VARCHAR(50) NOT NULL UNIQUE,
        nickname VARCHAR(30) NOT NULL DEFAULT '관리자',
        password VARCHAR(255) NOT NULL UNIQUE,
        rollName VARCHAR(5) NOT NULL DEFAULT 'ADMIN'
    );

### Booking
    CREATE TABLE Booking(
        idx BIGINT AUTO_INCREMENT PRIMARY KEY,
        memberIdx BIGINT NOT NULL,
        businessIdx BIGINT NOT NULL,
        roomIdx BIGINT NOT NULL,
        paymentId VARCHAR(200) NOT NULL,
        pgProvider VARCHAR(50) NOT NULL,
        payMethod VARCHAR(50),
        checkInDate VARCHAR(10) NOT NULL,
        checkOutDate VARCHAR(10) NOT NULL,
        price INT NOT NULL,
        personnel INT NOT NULL,
        buyerName VARCHAR(20) NOT NULL,
        buyerTel VARCHAR(11) NOT NULL,
        isPaid INT NOT NULL
    );

### Business
    CREATE TABLE Business(
        idx BIGINT AUTO_INCREMENT PRIMARY KEY,
        id VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL UNIQUE,
        name VARCHAR(30) NOT NULL,
        placeCall VARCHAR(11) NOT NULL UNIQUE,
        placeName VARCHAR(50) NOT NULL,
        placeAddress VARCHAR(255) NOT NULL,
        category VARCHAR(20) NOT NULL,
        authentication INT NOT NULL,
        parkingOption INT NOT NULL,
        smokingOption INT NOT NULL,
        wifiOption INT NOT NULL,
        ottOption INT NOT NULL,
        rollName VARCHAR(8) NOT NULL DEFAULT 'BUSINESS'
    );

### BusinessImage
    CREATE TABLE BusinessImage(
        idx BIGINT AUTO_INCREMENT PRIMARY KEY,
        businessIdx BIGINT NOT NULL,
        image VARCHAR(255) NOT NULL
    );

### Member
    CREATE TABLE Member(
        idx BIGINT AUTO_INCREMENT PRIMARY KEY,
        id VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        name VARCHAR(20) NOT NULL,
        nickname VARCHAR(20) NOT NULL UNIQUE,
        phone VARCHAR(11) NOT NULL,
        profileImage VARCHAR(255),
        rollName VARCAHR(6) NOT NULL DEFAULT 'MEMBER'
    );

### Room
    CREATE TABLE Room(
        idx BIGINT AUTO_INCREMENT PRIMARY KEY,
        businessIdx BIGINT NOT NULL,
        roomName VARCHAR(20) NOT NULL,
        price INT NOT NULL,
        minPersonnel INT NOT NULL,
        maxPersonnel INT NOT NULL,
        checkInTime VARCHAR(10) NOT NULL,
        checkOutTime VARCHAR(10) NOT NULL
    );

### RoomImage
    CREATE TABLE RoomImage(
        idx BIGINT AUTO_INCREMENT PRIMARY KEY,
        roomIdx BIGINT NOT NULL,
        businessIdx BIGINT NOT NULL,
        image VARCHAR(255) NOT NULL
    );

### 외래키 제약 조건
    #businessImage 테이블 외래키 추가
    ALTER TABLE BusinessImage
	ADD CONSTRAINT
	FOREIGN KEY (businessIdx)
	REFERENCES Business (idx)
	ON UPDATE CASCADE;

    #room 테이블 외래키 추가
    ALTER TABLE Room
    ADD CONSTRAINT
    FOREIGN KEY (businessIdx)
    REFERENCES Business (idx)
    ON UPDATE CASCADE;
    
    #roomImage 테이블 외래키 추가
    ALTER TABLE RoomImage
    ADD CONSTRAINT
    FOREIGN KEY (businessIdx)
    REFERENCES Business (idx)
    ON UPDATE CASCADE;
    
    ALTER TABLE RoomImage
    ADD CONSTRAINT
    FOREIGN KEY (roomIdx)
    REFERENCES Room (idx)
    ON UPDATE CASCADE;
    
    #booking 테이블 외래키 추가
    ALTER TABLE Booking
    ADD CONSTRAINT
    FOREIGN KEY (memberIdx)
    REFERENCES Member (idx)
    ON UPDATE CASCADE;
    
    ALTER TABLE Booking
    ADD CONSTRAINT
    FOREIGN KEY (businessIdx)
    REFERENCES Business (idx)
    ON UPDATE CASCADE;
    
    ALTER TABLE Booking
    ADD CONSTRAINT
    FOREIGN KEY (roomIdx)
    REFERENCES Room (idx)
    ON UPDATE CASCADE;

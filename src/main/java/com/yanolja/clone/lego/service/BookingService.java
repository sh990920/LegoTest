package com.yanolja.clone.lego.service;

import com.yanolja.clone.lego.entity.Booking;
import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.Member;
import com.yanolja.clone.lego.entity.Room;
import com.yanolja.clone.lego.repository.BookingRepository;
import com.yanolja.clone.lego.repository.BusinessRepository;
import com.yanolja.clone.lego.repository.MemberRepository;
import com.yanolja.clone.lego.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class BookingService {
    // MemberRepository 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    MemberRepository memberRepository;

    // BusienssRepository 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessRepository businessRepository;

    // RoomRepository 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    RoomRepository roomRepository;

    // BookingRepository 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BookingRepository bookingRepository;

    // Member 객체 검색
    public Member getMember(Long idx){
        // 파라미터로 넘어온 idx 값으로 member 객체 검색
        Member member = memberRepository.findByIdx(idx);
        // 검색한 member 객체 반환
        return member;
    }

    // Business 객체 검색
    public Business getBusiness(Long idx){
        // 파라미터로 넘어온 idx 값으로 business 객체 검색
        Business business = businessRepository.findByIdx(idx);
        // 검색한 business 객체 반환
        return business;
    }

    // Room 객체 검색
    public Room getRoom(Long idx){
        // 파라미터로 넘어온 idx 값으로 room 객체 검색
        Room room = roomRepository.findByIdx(idx);
        // 검색한 room 객체 반환
        return room;
    }

    // 결제 정보를 저장
    public String Pay(Booking booking){
        // 결제가 완료 되었으므로 결제 완료 정보를 저장
        booking.setIsPaid(0);
        // 이후 예약 내용 정보 저장
        Booking afterBooking = bookingRepository.save(booking);
        // 만약 저장된 결과값이 있으면 'yes' 를 반환하고 결과값이 없다면 'no' 를 반환
        if(afterBooking != null){
            return "yes";
        }else{
            return "no";
        }
    }

    // 예약 리스트 가져오기
    public List<Booking> bookingList(Long businessIdx, Long roomIdx){
        // businessIdx 와 roomIdx 로 검색한 리스트를 저장한 이후 반환
        List<Booking> bookingList = bookingRepository.findByBusinessIdxAndRoomIdx(businessIdx, roomIdx);
        return bookingList;
    }

    // 예약 리스트 가져오기
    public List<Booking> bookingList(Long businessIdx){
        // businessIdx 로 검색한 리스트를 저장한 이후 반환
        List<Booking> bookingList = bookingRepository.findByBusinessIdx(businessIdx);
        return bookingList;
    }

    // 예약 리스트 가져오기
    public List<Booking> bookingList2(Long roomIdx){
        // roomIdx 로 검색한 리스트를 저장한 이후 반환
        List<Booking> bookingList = bookingRepository.findByRoomIdx(roomIdx);
        return bookingList;
    }

    // 입실, 퇴실, 환불 업데이트
    public void bookingUpdate(Booking booking){
        bookingRepository.save(booking);
    }

    // 입실
    public void checkIn(Booking booking){
        Booking updateBooking = bookingRepository.findByIdx(booking.getIdx());
        updateBooking.setIsPaid(1);
        bookingUpdate(updateBooking);
    }

    // 퇴실
    public void checkOut(Booking booking){
        Booking updateBooking = bookingRepository.findByIdx(booking.getIdx());
        updateBooking.setIsPaid(2);
        bookingUpdate(updateBooking);
    }

    // 환불
    public void refund(Booking booking){
        // 상세하게 입력은 하지 않고 환불 처리만 일단 진행
        Booking updateBooking = bookingRepository.findByIdx(booking.getIdx());
        updateBooking.setIsPaid(3);
        bookingUpdate(updateBooking);
    }

}

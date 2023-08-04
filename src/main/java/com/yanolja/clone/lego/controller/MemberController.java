package com.yanolja.clone.lego.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.yanolja.clone.lego.entity.*;
import com.yanolja.clone.lego.httpClient.IamPortPass;
import com.yanolja.clone.lego.service.BookingService;
import com.yanolja.clone.lego.service.BusinessService;
import com.yanolja.clone.lego.service.RoomService;
import com.yanolja.clone.lego.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {
    // SignUpService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    SignUpService signUpService;

    // BusinessService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessService businessService;

    // RoomService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    RoomService roomService;

    @Autowired
    BookingService bookingService;

    // PasswordEncoder 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    PasswordEncoder passwordEncoder;

    //로그인 페이지 이동
    @GetMapping("/loginPage/")
    public String loginPage(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "errorMessage", required = false) String errorMessage, Model model){
        // member 폴더 안의 loginPage.html 로 이동
        model.addAttribute("error", error);
        model.addAttribute("errorMessage", errorMessage);
        return "member/loginPage";
    }

    // 로그인 기능
    @PostMapping("/loginPage/login/")
    public void login(String id){
        // signUpService 의 loadUserByUsername 메소드로 실제 'USER' 권한을 가진 유저인지 확인하기 위해서 사용
        signUpService.loadUserByUsername(id);
        // 로그인 성공 시 이후 작동은 MemberSecurityConfig 에서 설정을 해두었기 때문에 따로 작성을 하지 않음
    }

    // 로그인 완료
    @GetMapping("/loginPage/login/loginSuccess/")
    public String loginSuccess(){
        // 로그인을 성공했다면 메인 페이지로 이동
        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout/")
    public void logout(){
        // 로그아웃 기능은 SecurityConfig 에서 설정을 해두었기 때문에 따로 작성하지 않음
    }

    // 회원가입 페이지 이동
    @GetMapping("/signUpPage/")
    public String signUpPage(){
        // 'USER' 권한을 가진 사용자를 생성하기 위해 member 폴더 안의 signUpPage.html 로 이동
        return "member/signUpPage";
    }

    // 회원가입
    @PostMapping("/signUpPage/signUp")
    @ResponseBody
    public String signUp(Member member){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // signUpService 에서 처리하고 나온 결과값을 리턴값에 저장
        res = signUpService.signUp(member, passwordEncoder);
        // 리턴값을 리턴
        return res;
    }

    // 아이디 중복 검사
    @GetMapping("/signUpPage/signUp/idCheck")
    @ResponseBody
    public String idCheck(String id){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // signUpService 에서 처리하고 나온 결과값을 리턴값에 저장
        res = signUpService.idCheck(id);
        // 리턴값을 리턴
        return res;
    }

    // 업체 상세보기
    @GetMapping("/placePost/")
    public String placePostPage(Business business, Model model){
        // 어떤 업체로 접근했는지 업체 객체 생성
        Business business1 = businessService.findBusiness(business.getIdx());
        // 업체에 어떤 방들이 있는지 방 리스트 생성
        List<Room> roomList = roomService.roomList(business.getIdx());
        // 업체에 관련된 사진 리스트를 생성
        List<BusinessImage> businessImageList = businessService.findBusinessImageList(business.getIdx());
        // 업체의 방에 관련된 사진 리스트를 생성
        List<List> roomImageList = roomService.findDetailRoomImageList(business.getIdx());

        List<Booking> bookingList = bookingService.bookingList(business1.getIdx());
        model.addAttribute("bookingList", bookingList);
        // 방 이미지 리스트를 상세 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("roomImageList", roomImageList);
        // 업체 이미지 리스트를 상세 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("businessImageList", businessImageList);
        // 업체 객체를 상세 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("business", business1);
        // 방 리스트를 상세 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("roomList", roomList);
        // 상세 페이지로 이동
        return "member/businessDetailPage";
    }

    // 방 상세보기
    @GetMapping("/placePost/roomPost/")
    public String roomPostPage(String checkInDate, String checkOutDate, Room room, Principal principal, Model model){
        // 접속한 사용자가 누구인지 알기 위해 id 얻어오기
        String id = principal.getName();
        // 얻어온 id 로 사용자 검색
        Member member = signUpService.findUser(id);
        // 들어온 방의 객체를 검색
        Room room1 = roomService.findRoom(room.getIdx());
        // 방에 관련된 사진 리스트를 검색
        List<RoomImage> roomImageList = roomService.findRoomImageList(room.getIdx());


        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);

        List<Booking> bookingList = bookingService.bookingList2(room1.getIdx());
        model.addAttribute("bookingList", bookingList);
        System.out.println(bookingList.toString());
        // 검색한 방 객체를 방 상세보기 페이지에서 사용하기 위해 model 에 바인딩
        model.addAttribute("room", room1);
        // 검색한 방 사진 리스트를 방 상세보기 페이지에서 사용하기 위해 model 에 바인딩
        model.addAttribute("roomImageList", roomImageList);
        // 검색한 사용자 객체를 방 상세보기 페이지에서 사용하기 위해 model 에 바인딩
        model.addAttribute("member", member);
        // 방 상세보기 페이지로 이동
        return "member/roomDetailPage";
    }

    // 결제 페이지로 이동하기
    @PostMapping("/pay/")
    public String pay(Booking booking, Model model){
        // 결제하는 사용자 정보를 얻기 위해 사용자 객체 검색
        Member member = bookingService.getMember(booking.getMemberIdx());
        // 결제하려는 업체의 정보를 얻기 위해 업체 객체 검색
        Business business = bookingService.getBusiness(booking.getBusinessIdx());
        // 결제하려는 방의 정보를 얻기 위해 방 객체 검색
        Room room = bookingService.getRoom(booking.getRoomIdx());
        // 이전 페이지에서 받아온 정보들을 결제 페이지에서 사용하기 위해 model 에 바인딩
        model.addAttribute("booking", booking);
        // 사용자 객체를 결제 페이지에서 사용하기 위해 model 에 바인딩
        model.addAttribute("member", member);
        // 업체 객체를 결제 페이지에서 사용하기 위해 model 에 바인딩
        model.addAttribute("business", business);
        // 방 객체를 결제 페이지에서 사용하기 위해 model 에 바인딩
        model.addAttribute("room", room);
        // 결제 페이지로 이동
        return "member/bookingPage";
    }

    // 결제 완료
    @PostMapping("/pay/success/")
    @ResponseBody
    public String success(Booking booking){
        // 포트원 서버로부터 토큰 값을 받아오기 위해 사용
        JsonNode jsonToken = IamPortPass.getToken();
        // 가져온 json 형태의 정보를 String 타입으로 변경
        String accessToken = jsonToken.get("access_token").asText();
        // paymentId 와 accessToken 으로 결제 내역을 가져오기 위해 사용
        JsonNode payment = IamPortPass.getResult(booking.getPaymentId(), accessToken);
        // 가져온 json 형태의 정보가 복잡해 payment 의 값을 json 형태로 생성
        JsonNode paymentNode = payment.get("payment");
        // json 형태의 정보가 복잡해 트랜잭션 의 값을 json 형태로 생성
        JsonNode transactionsNode = paymentNode.get("transactions");
        // 첫 번째 트랜잭션 정보 가져오기
        JsonNode firstTransaction = transactionsNode.get(0);
        // paid 값 가져오기
        String paid = firstTransaction.get("amount").get("paid").asText();
        // status 값 가져오기
        String status = firstTransaction.get("status").asText();
        // payment_id 값 가져오기
        String payment_id = paymentNode.get("id").asText();
        // 카카오페이 결제 시 카카오페이에서 카드를 사용했다면 카드 사용으로 변경
        if(booking.getPgProvider().equals("CACAO_PAY")){
            JsonNode payment_method_detail = firstTransaction.get("payment_method_detail").get("easy_pay");
            JsonNode card = payment_method_detail.get("card");
            if(card != null){
                booking.setPayMethod(booking.getPayMethod() + "_CARD");
            }
        }
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // 포트원 서버에서 가져온 paymentId 와 결제 할 때 등록한 paymentId 가 같은지 비교
        if(payment_id.equals(booking.getPaymentId())){
            // 포트원 서버에서 가져온 status 의 결과가 "PAID" 와 같은지 비교
            if(status.equalsIgnoreCase("PAID")){
                // 포트원 서버에서 가져온 paid 가 비어있는지 확인
                if(paid != null){
                    // 모든 조건에 일치하면 결제 정보 저장
                    res = bookingService.Pay(booking);
                }
            }
        }
        // 결과값 리턴
        return res;
    }
}

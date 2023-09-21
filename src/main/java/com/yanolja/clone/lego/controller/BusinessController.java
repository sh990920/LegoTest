package com.yanolja.clone.lego.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.yanolja.clone.lego.entity.*;
import com.yanolja.clone.lego.httpClient.IamPortPass;
import com.yanolja.clone.lego.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/business")
public class BusinessController {
    // BusinessSignUpService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessSignUpService businessSignUpService;

    // BusinessService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessService businessService;

    // roomService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    RoomService roomService;

    // SignUpService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    SignUpService signUpService;

    // AdminSignUpService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    AdminSignUpService adminSignUpService;

    // PasswordEncoder 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    PasswordEncoder passwordEncoder;

    // 사업자 로그인 기능
    @PostMapping("/login/")
    public void login(String id){
        // businessSignUpService 의 loadUsrByUsername 메소드로 실제 'BUSINESS' 권한을 가진 유저인지 확인하기 위해 사용
        businessSignUpService.loadUserByUsername(id);
        // 로그인 성공 시 이후 작동은 BusinessSecurityConfig 에서 설정을 해두었기 때문에 따로 작성을 하지 않음

    }

    // 로그인 완료
    @GetMapping("/login/loginSuccess/")
    public String loginSuccess(){
        // 로그인을 성공했다면 business 메인 페이지로 이동
        return "redirect:/business/";
    }

    // 사업자 회원가입 페이지 이동
    @GetMapping("/signUpPage/")
    public String businessSignUpPage(){
        // 'BUSINESS' 권한을 가진 사용자를 생성하기 위해 business 폴더 안의 signUpPage.html 로 이동
        return "business/signUpPage";
    }

    // 업체 이름 중복 체크
    @GetMapping("/signUpPage/placeNameCheck")
    @ResponseBody
    public String placeNameCheck(String placeName){
        String res = "no";
        res = businessSignUpService.placeNameCheck(placeName);
        return res;
    }

    // 이름, 핸드폰 번호 인증
    @PostMapping("/signUpPage/certifications/")
    @ResponseBody
    public HashMap<String, String> certifications(String impUid){
        // PortOne 안에 있는 핸드폰 번호 인증을 위해서 토큰이 저장된 JsonNode 받아오기
        JsonNode jsonToken = IamPortPass.getTokenV1();
        // 받아온 JsonNode 에서 토큰값 가져오기
        String accessToken = jsonToken.get("response").get("access_token").asText();
        // 사용자의 정보를 받아오기 위해 위에서 가져온 토큰으로 사용자 정보 받아오기
        JsonNode userInfo = IamPortPass.getUserInfo(impUid, accessToken);
        // 받아온 JsonNode 에서 사용자 이름 가져오기
        String name = userInfo.get("response").get("name").asText();
        // 받아온 JsonNode 에서 사용자 전화번호 가져오기
        String placeCall = userInfo.get("response").get("phone").asText();
        // 이름과 전화번호를 Map 으로 저장 이후 저장한 Map 을 다시 리턴
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("placeCall", placeCall);
        return map;
    }

    // 회원가입 시 전화번호 중복 비교하기
    @PostMapping("/signUpPage/placeCallCheck")
    @ResponseBody
    public String placeCallCheck(String placeCall){
        String res = "no";
        res = businessSignUpService.placeCallCheck(placeCall);
        return res;
    }

    // 회원가입
    @PostMapping("/signUpPage/signUp")
    @ResponseBody
    public String signUp(Business business){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // businessSignUpService 에서 처리하고 나온 결과값을 리턴값에 저장
        res = businessSignUpService.signUp(business, passwordEncoder);
        // 리턴값을 리턴
        return res;
    }

    // 사업자 메인 페이지
    @GetMapping("/")
    public String businessMain(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "errorMessage", required = false) String errorMessage, Principal principal, Model model){
        // 만약 로그인한 객체가 없다면 로그인을 하지 않은 메인 페이지로 이동
        if(principal == null){
            return "redirect:/n/";
        }
        Member member = signUpService.findUser(principal.getName());
        if(member != null){
            return "redirect:/";
        }
        Admin admin = adminSignUpService.findUser(principal.getName());
        if(admin != null){
            return "redirect:/admin/";
        }
        // 숙소에 포함된 방들을 메인 페이지에서 보여주기 위해 리스트 생성
        List<Room> roomList = roomService.roomList(principal.getName());
        // 로그인한 사업자 정보를 메인 페이지에서 보여주기 위한 객체 생성
        Business business = businessService.findBusiness(principal.getName());
        // 사업자의 숙소에 포함된 이미지를 메인 페이지에서 보여주기 위해 리스트 생성
        List<BusinessImage> businessImageList = businessService.findBusinessImageList(business.getIdx());
        // 업체의 방에 관련된 사진 리스트를 생성
        List<List> roomImageList = roomService.findDetailRoomImageList(business.getIdx());
        // 방 이미지 리스트를 상세 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("roomImageList", roomImageList);
        // 방 리스트를 html 에서 사용할 수 있도록 model 에 바인딩
        model.addAttribute("roomList", roomList);
        // 로그인한 사용자의 id 를 메인 페이지에서 사용할 수 있도록 model 에 바인딩
        model.addAttribute("id", principal.getName());
        // 로그인한 사용자의 정보를 메인 페이지에서 사용할 수 있도록 model 에 바인딩
        model.addAttribute("business", business);
        // 사업자의 숙소 사진들을 메인 페이지에서 사용할 수 있도록 model 에 바인딩
        model.addAttribute("businessImageList", businessImageList);
        // 메인페이지에서 사용할 에러 코드
        model.addAttribute("error", error);
        // 메인페이지에서 에러가 있을 때 보여줄 에러 내용
        model.addAttribute("errorMessage", errorMessage);
        // business 폴더 안의 mainPage 로 이동
        return "business/mainPage";
    }

    // 객실 등록 페이지
    @GetMapping("/roomAddPage/")
    public String roomAddPage(Principal principal, Model model){
        // 로그인한 유저의 business 객체를 얻기 위해 id 를 통해 검색
        Business business = businessService.findBusiness(principal.getName());
        // 검색된 객체의 idx 를 model 로 바인딩
        model.addAttribute("businessIdx", business.getIdx());
        // business 폴더 안의 roomAddPage 로 이동
        return "business/roomAddPage";
    }

    // 객실 등록
    @GetMapping("/roomAddPage/roomAdd/")
    @ResponseBody
    public String roomAdd(Room room){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // roomService 의 결과값을 리턴값에 저장
        res = roomService.roomAdd(room);
        // 리턴값 리턴
        return res;
    }

    // 객실 수정 페이지
    @GetMapping("/roomUpdatePage/")
    public String roomUpdatePage(Room room, Model model){
        // 가져온 room 객체의 idx 로 업데이트를 진행할 객체 검색
        Room updateRoom = roomService.findRoom(room.getIdx());
        // 검색한 객체를 모델에 바인딩
        model.addAttribute("room", updateRoom);
        // business 폴더 안의 roomUpdatePage.html 로 이동
        return "business/roomUpdatePage";
    }

    // 객실 수정
    @GetMapping("/roomUpdatePage/roomUpdate/")
    @ResponseBody
    public String roomUpdate(Room room){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // roomService 의 결과값을 리턴값에 저장
        res = roomService.roomUpdate(room);
        // 리턴값 리턴
        return res;
    }

    // 객실 삭제
    @GetMapping("/roomDelete/")
    @ResponseBody
    public String roomDelete(Room room){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // roomService 의 결과값을 리턴값에 저장
        res = roomService.roomDelete(room);
        // 리턴값 리턴
        return res;
    }

    // 사업자 아이디 중복 검사
    @GetMapping("/signUpPage/idCheck")
    @ResponseBody
    public String idCheck(String id){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // adminSignUpService 에서 처리하고 나온 결과값을 리턴값에 저장
        res = businessSignUpService.idCheck(id);
        // 리턴값을 리턴
        return res;
    }

    // 장소 사진 추가
    @PostMapping("/imageAdd/")
    public String imageAdd(BusinessImage businessImage) throws UnsupportedEncodingException {
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // businessService 에서 처리하고 나온 결과값을 리턴값에 저장
        res = businessService.imageAdd(businessImage);
        // 만약 결과값이 'noImage' 라면 이미지 파일이 아니므로 에러 메시지를 생성 후 메인 페이지로 리턴(리턴할 때 에러 결과와 에러 메세지 등록)
        if(res.equals("noImage")){
            String errorMessage = "이미지 파일이 아닙니다.";
            return "redirect:/business/?error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        // 만약 결과값이 'no' 라면 이미지가 정상적으로 저장되지 않았으므로 에러 메시지를 생성 후 메인 페이지로 리턴(리턴할 때 에러 결과와 에러 메시지 등록)
        }else if(res.equals("no")){
            String errorMessage = "이미지가 추가되지 않았습니다.";
            return "redirect:/business/?error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        // 그렇지 않다면 정상적으로 등록 된 것이므로 에러 코드 없이 메인 페이지로 리턴
        }else{
            return "redirect:/business/";
        }
    }

    // 업체 사진 삭제
    @PostMapping("/imageDelete/")
    public String imageDelete(BusinessImage businessImage) throws UnsupportedEncodingException {
        // 리턴값의 기본값을 no' 로 설정
        String res = "no";
        // businessSerivce 에서 처리하고 나온 결과값을 리턴값에 저장
        res = businessService.imageDelete(businessImage);
        // 만약 결과값이 'noImage' 라면 이미지 파일이 존재하지 않는 것이므로 에러 메세지를 생성한 이후 메인 페이지로 리턴(리턴할 때 에러 결과와 에러 메시지를 등록)
        if(res.equals("noImage")){
            String errorMessage = "이미지 파일이 존재하지 않습니다.";
            return "redirect:/business/?error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        // 만약 결과값이 'no' 라면 이미지 파일이 정상적으로 제거 되지 않은 것이므로 에러 메세지를 생성한 이후 메인 페이지로 리턴(리턴할 때 에러 결과와 에러 메세지를 등록)
        }else if(res.equals("no")){
            String errorMessage = "이미지가 삭제되지 않았습니다.";
            return "redirect:/business/?error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        // 그렇지 않다면 정상적으로 삭제된 것이므로 에러 코드 없이 메인 페이지로 리턴
        }else{
            return "redirect:/business/";
        }
    }

    // 방 사진 추가
    @PostMapping("/roomPostPage/roomImageAdd/")
    public String roomImageAdd(RoomImage roomImage) throws UnsupportedEncodingException {
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // roomSerivce 에서 처리하고 나온 결과값을 리턴값에 저장
        res = roomService.imageAdd(roomImage);
        // 만약 결과값이 'noImage' 라면 이미지 파일이 아니므로 에러 메시지를 생성 후 방 상세페이지로 리턴(리턴할 때 에러 결과와 에러 메세지 등록)
        if(res.equals("noImage")){
            String errorMessage = "이미지 파일이 아닙니다.";
            return "redirect:/business/roomPostPage/?idx=" + roomImage.getRoomIdx() + "&error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        // 만약 결과값이 'no' 라면 이미지가 정상적으로 저장되지 않았으므로 에러 메시지를 생성 후 방 상세페이지로 리턴(리턴할 때 에러 결과와 에러 메시지 등록)
        }else if(res.equals("no")){
            String errorMessage = "이미지가 추가되지 않았습니다.";
            return "redirect:/business/roomPostPage/?idx=" + roomImage.getRoomIdx() + "&error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        // 그렇지 않다면 정상적으로 등록 된 것이므로 에러 코드 없이 방 상세페이지로 리턴
        }else{
            return "redirect:/business/roomPostPage/?idx=" + roomImage.getRoomIdx();
        }
    }

    // 방 사진 삭제
    @PostMapping("/roomPostPage/roomImageDelete/")
    public String roomImageDelete(RoomImage roomImage) throws UnsupportedEncodingException {
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // roomSerivce 에서 처리하고 나온 결과값을 리턴값에 저장
        res = roomService.imageDelete(roomImage);
        // 만약 결과값이 'noImage' 라면 이미지 파일이 존재하지 않는 것이므로 에러 메세지를 생성한 이후 방 상세페이지로 리턴(리턴할 때 에러 결과와 에러 메시지를 등록)
        if(res.equals("noImage")){
            String errorMessage = "이미지 파일이 존재하지 않습니다.";
            return "redirect:/business/roomPostPage/?idx=" + roomImage.getRoomIdx() + "&error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        // 만약 결과값이 'no' 라면 이미지 파일이 정상적으로 제거 되지 않은 것이므로 에러 메세지를 생성한 이후 방 상세페이지로 리턴(리턴할 때 에러 결과와 에러 메세지를 등록)
        }else if(res.equals("no")){
            String errorMessage = "이미지가 삭제되지 않았습니다.";
            return "redirect:/business/roomPostPage/?idx=" + roomImage.getRoomIdx() + "&error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        // 그렇지 않다면 정상적으로 삭제된 것이므로 에러 코드 없이 방 상세페이지로 리턴
        }else{
            return "redirect:/business/roomPostPage/?idx=" + roomImage.getRoomIdx();
        }
    }

    // 방 상세 페이지
    @GetMapping("/roomPostPage/")
    public String roomPostPage(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "errorMessage", required = false) String errorMessage, Room room, Model model){
        // 받아온 파라미터로 방 객체 검색
        Room room1 = roomService.findRoom(room.getIdx());
        // 방에 등록된 이미지 리스트를 검색
        List<RoomImage> roomImageList = roomService.findRoomImageList(room.getIdx());
        // 방 객체를 방 상세보기 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("room", room1);
        // 에러 상태를 방 상세보기 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("error", error);
        // 에러 메세지를 방 상세보기 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("errorMessage", errorMessage);
        // 이미지 리스트를 방 상세보기 페이지에서 사용하기 위해 model 바인딩
        model.addAttribute("roomImageList", roomImageList);
        // 방 상세보기 페이지로 이동
        return "business/roomPost";
    }
}

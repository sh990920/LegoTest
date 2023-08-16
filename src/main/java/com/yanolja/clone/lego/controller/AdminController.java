package com.yanolja.clone.lego.controller;

import com.yanolja.clone.lego.entity.Admin;
import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.Member;
import com.yanolja.clone.lego.service.AdminSignUpService;
import com.yanolja.clone.lego.service.BusinessSignUpService;
import com.yanolja.clone.lego.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    // AdminSignUpService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    AdminSignUpService adminSignUpService;

    // BusinessSignUpService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessSignUpService businessSignUpService;

    // SignUpService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    SignUpService signUpService;

    // PasswordEncoder 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    PasswordEncoder passwordEncoder;

    // 관리자 로그인
    @PostMapping("/login/")
    public void login(String id){
        // signUpService 의 loadUserByUsername 메소드로 실제 'ADMIN' 권한을 가진 유저인지 확인하기 위해서 사용
        adminSignUpService.loadUserByUsername(id);
        // 로그인 성공 시 이후 작동은 AdminSecurityConfig 에서 설정을 해두었기 때문에 따로 작성을 하지 않음
    }

    // 로그인 완료
    @GetMapping("/login/loginSuccess/")
    public String loginSuccess(){
        // 로그인을 성공했다면 admin 메인 페이지로 이동
        return "redirect:/admin/";
    }

    // 관리자 회원가입 페이지
    @GetMapping("/signUpPage/")
    public String adminSignUpPage(){
        // 'ADMIN' 권한을 가진 사용자를 생성하기 위해 admin 폴더 안의 signUpPage.html 로 이동
        return "admin/signUpPage";
    }

    // 회원가입
    @PostMapping("/signUpPage/signUp")
    @ResponseBody
    public String signUp(Admin admin){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // adminSignUpService 에서 처리하고 나온 결과값을 리턴값에 저장
        res = adminSignUpService.signUp(admin, passwordEncoder);
        // 리턴값을 리턴
        return res;
    }

    // 관리자 메인 페이지
    @GetMapping("/")
    public String businessMain(Principal principal, Model model){
        // 만약 로그인한 객체가 없다면 로그인을 하지않은 메인페이지로 이동
        if(principal == null){
            return "redirect:/n/";
        }
        Member member = signUpService.findUser(principal.getName());
        if(member != null){
            return "redirect:/";
        }
        Business business = businessSignUpService.findUser(principal.getName());
        if(business != null){
            return "redirect:/business/";
        }
        List<Business> businessList = businessSignUpService.authenticationCheck();
        model.addAttribute("businessList", businessList);
        // 로그인한 유저의 id 값을 model 에 담아 바인딩
        model.addAttribute("id", principal.getName());
        // admin 폴더 안의 mainPage.html 로 이동
        return "admin/mainPage";
    }

    // 사업자가 회원 가입을 했을 때 실제 사용 가능한 사업자로 등록
    @PostMapping("/businessAuthentication/")
    public String businessAuthentication(Business business){
        // 로그인이 가능하게 내용 변경 후 업데이트
        businessSignUpService.authenticationCheck(business);
        // 업데이트 이후 메인 페이지로 다시 redirect
        return "redirect:/admin/";
    }

    // 관리자 아이디 중복 검사
    @GetMapping("/signUpPage/idCheck")
    @ResponseBody
    public String idCheck(String id){
        // 리턴값의 기본값을 'no' 로 설정
        String res = "no";
        // adminSignUpService 에서 처리하고 나온 결과값을 리턴값에 저장
        res = adminSignUpService.idCheck(id);
        // 리턴값을 리턴
        return res;
    }
}

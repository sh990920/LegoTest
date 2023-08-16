package com.yanolja.clone.lego.controller;

import com.yanolja.clone.lego.entity.Booking;
import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.Member;
import com.yanolja.clone.lego.service.BookingService;
import com.yanolja.clone.lego.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/myPage")
public class MyPageController {
    @Autowired
    MyPageService myPageService;

    // 마이페이지 메인
    @GetMapping("/")
    public String myPage(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "errorMessage", required = false) String errorMessage, Principal principal, Model model){
        Member member = myPageService.findMember(principal.getName());
        model.addAttribute("member", member);
        model.addAttribute("error", error);
        model.addAttribute("errorMessage", errorMessage);
        return "myPage/myPage";
    }

    // 유저 프로필사진 변경
    @PostMapping("/profileImageUpdate/")
    public String profileImageUpdate(Member member, Principal principal) throws UnsupportedEncodingException {
        // 유저 프로필 사진 변경 후 myPage 로 리다이렉트
        String res = "no";
        res = myPageService.profileImageUpdate(member, principal.getName());
        if(res.equals("noImage")){
            String errorMessage = "이미지 파일이 아닙니다.";
            return "redirect:/myPage/?error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        }else if(res.equals("no")){
            String errorMessage = "이미지가 변경되지 않았습니다.";
            return "redirect:/myPage/?error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        }else{
            return "redirect:/myPage/";
        }
    }

    @GetMapping("/bookingCheck/")
    public String bookingCheckPage(Principal principal, Model model){
        Member member = myPageService.findMember(principal.getName());
        List<Booking> bookingList = myPageService.bookingCheckList(member.getIdx());
        List<Business> businessList = myPageService.businessCheckList(member.getIdx());
        model.addAttribute("businessList", businessList);
        model.addAttribute("member", member);
        model.addAttribute("bookingList", bookingList);
        return "myPage/BookingCheckPage";
    }


}

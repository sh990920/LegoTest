package com.yanolja.clone.lego.controller;

import com.yanolja.clone.lego.entity.Admin;
import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.BusinessImage;
import com.yanolja.clone.lego.entity.Member;
import com.yanolja.clone.lego.service.AdminSignUpService;
import com.yanolja.clone.lego.service.BusinessService;
import com.yanolja.clone.lego.service.BusinessSignUpService;
import com.yanolja.clone.lego.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    // SignUpService 를 사용하기 위해 AutoWierd 로 연결
    @Autowired
    SignUpService signUpService;

    // BusinessSignUpService 를 사용하기 위해 AutoWierd 로 연결
    @Autowired
    BusinessSignUpService businessSignUpService;

    // businessService 를 사용하기 위해 AutoWierd 로 연결
    @Autowired
    BusinessService businessService;

    // AdminSignUpService 를 사용하기 위해 AutoWierd 로 연결
    @Autowired
    AdminSignUpService adminSignUpService;

    // 메인 페이지 이동
    @GetMapping("/")
    public String mainPage(Principal principal, Model model){
        // 만약 로그인을 하지 않은 유저라면 '/n/' url 로 이동
        if(principal == null){
            return "redirect:/n/";
        }
        // 만약 사업자가 메인 페이지로 들어오려 한다면 business 메인 페이지로 이동
        Business business = businessSignUpService.findUser(principal.getName());
        if(business != null){
            return "redirect:/business/";
        }
        // 만약 관리자가 메인 페이지로 들어오려 한다면 관리자 메인 페이지로 이동
        Admin admin = adminSignUpService.findUser(principal.getName());
        if(admin != null){
            return "redirect:/admin/";
        }
        // 숙소 리스트를 메인 페이지에서 보여주기 위해 리스트 생성(총 4개만 보여줄 예정)
        List<Business> businessList = businessService.businessList();

        List<BusinessImage> businessImageList = businessService.findBusinessImageList();

        Member member = signUpService.findUser(principal.getName());

        model.addAttribute("businessImageList", businessImageList);
        // 숙소 리스트를 html 에서 사용할 수 있도록 model 에 바인딩
        model.addAttribute("businessList", businessList);
        // 로그인한 사용자의 id 를 html 에서 사용할 수 있도록 model 에 바인딩
        model.addAttribute("member", member);
        // mainPage.html 로 이동
        return "mainPage";
    }

    // 로그인을 하지 않은 유저가 이동
    @GetMapping("/n/")
    public String noLoginMainPage(Principal principal, Model model){
        // 만약 유저가 로그인을 했다면
        if(principal != null){
            // principal 에서 사용자의 id 를 받아온다.
            String id = principal.getName();
            // 받아온 id 로 member 사용자인지 검색
            Member findMember = signUpService.findUser(id);
            // 만약 검색 결과가 없다면 member 사용자가 아니기 때문에 다른 사용자인지 검색
            if(findMember == null){
                // 받아온 id 로 business 사용자인지 검색
                Business findBusiness = businessSignUpService.findUser(id);
                // 만약 검색 결과가 없다면 business 사용자도 아니기 때문에 다른 사용자인지 검색
                if(findBusiness == null){
                    // 받아온 id 로 admin 사용자인지 검색
                    Admin findAdmin = adminSignUpService.findUser(id);
                    // 만약 검색 결과가 없다면 모든 사용자가 아니기 때문에 다시 main 페이지로 이동해서 다시 검사를 진행함
                    if(findAdmin == null){
                        return "mainPage";
                    }
                    // admin 사용자라면 admin 메인페이지로 이동
                    return "redirect:/admin/";
                }
                // business 사용자라면 business 메인페이지로 이동
                return "redirect:/business/";
            }
            // member 사용자라면 메인페이지로 이동
            return "redirect:/";
        }
        // 숙소 리스트를 메인 페이지에서 보여주기 위해 리스트 생성(총 4개만 보여줄 예정)
        List<Business> businessList = businessService.businessList();
        List<BusinessImage> businessImageList = businessService.findBusinessImageList();

        model.addAttribute("businessImageList", businessImageList);
        // 숙소 리스트를 html 에서 사용할 수 있도록 model 에 바인딩
        model.addAttribute("businessList", businessList);
        // 로그인을 하지 않았다면 메인페이지로 이동
        return "mainPage";
    }
}

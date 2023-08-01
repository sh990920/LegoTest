package com.yanolja.clone.lego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
    // 검색 페이지 이동
    @GetMapping("/beforeSearchPage/")
    public String beforeSearchPage(){
        return "search/beforeSearchPage";
    }

    // 검색
    @GetMapping("/search/")
    public String search(){
        return "search/afterSearchPage";
    }
}

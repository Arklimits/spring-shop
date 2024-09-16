package com.arklimits.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicController {
    @GetMapping("/")
    String home(){
        return "index.html";
    }

    @GetMapping("/about")
    @ResponseBody
    String about(){
        return "Site for practice Spring Boot";
    }

    @GetMapping("/mypage")
    @ResponseBody
    String mypage(){
        return "My Page";
    }
}

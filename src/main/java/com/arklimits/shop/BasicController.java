package com.arklimits.shop;

import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicController {

    @GetMapping("/")
    String home() {
        return "index";
    }

    @GetMapping("/about")
    @ResponseBody
    String about() {
        return "Site for practice Spring Boot";
    }

    @GetMapping("/date")
    @ResponseBody
    LocalDateTime date() {
        return LocalDateTime.now();
    }
}

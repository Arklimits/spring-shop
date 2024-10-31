package com.arklimits.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode == 404) {
            model.addAttribute("errorCode", "404");
            model.addAttribute("message", "Page Not Found");
            return "common/error";
        } else if (statusCode == 500) {
            model.addAttribute("errorCode", "500");
            model.addAttribute("message", "Internal Server Error");
            return "common/error";
        } else {
            model.addAttribute("errorCode", statusCode);
            model.addAttribute("message", "Unexpected Error");
            return "common/error";
        }
    }
}

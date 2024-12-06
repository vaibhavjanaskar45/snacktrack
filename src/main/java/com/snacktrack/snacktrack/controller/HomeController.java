package com.snacktrack.snacktrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {


    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";
}
    @GetMapping("/contactUs")
    public String contactUs() {
        return "contactUs";
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}

package com.syscawfit.syscawfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String welcomePage() {
        return "index.html";
    }
    @GetMapping(value = "/login")
    public String loginForm(){
        return "login.html";
    }
}


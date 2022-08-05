package com.syscawfit.syscawfit;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@SpringBootApplication
public class SyscawfitApplication {

    public static void main(String[] args) {
    	
    	
        SpringApplication.run(SyscawfitApplication.class, args);
    }
    @RequestMapping("/login")
    public String homePage() {

        return "/login.html";
    }

}

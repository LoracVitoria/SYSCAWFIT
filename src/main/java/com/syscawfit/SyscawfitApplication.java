package com.syscawfit;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class SyscawfitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyscawfitApplication.class, args);

    }
// configurar
    @RequestMapping("/login")
    public String homePage() {

        return "/login.html";
    }
}

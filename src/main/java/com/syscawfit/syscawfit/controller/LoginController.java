package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.exceptions.UsuarioNotFoundException;
import com.syscawfit.syscawfit.security.UsuarioPrincipal;
import com.syscawfit.syscawfit.services.UsuarioPrincipalDetailsService;
import com.syscawfit.syscawfit.services.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {
//    @Autowired
//    private UsuarioPrincipalDetailsService daoUser;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String welcomePage() {

        return "index.html";
    }


    //Spring Security see this :
    @RequestMapping(value = "/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model,
            HttpServletRequest request) {
      if (error != null ) {
          model.addAttribute("error", "CPF e senha inválidos!");
          return "login.html";
      } else  {
            return "redirect:/";
        }
//            model.addAttribute("error", "CPF ou senha inválidos!");
////            return "login.html";
//        if (logout != null) {
//            model.addAttribute("msg", "You've been logged out successfully.");
//        }
    }

//    @PostMapping("/login")
//    public String processarFormLogin(HttpServletRequest request, Model model) {
////        daoUsuario.findByCpf(request.getParameter("cpf"))
////        if (daoUsuario.findByCpf(request.getParameter("txtUsername")) == null ) {
//            model.addAttribute("error", "CPF ou senha inválidos!");
////            return "login.html";
////        } else {
//            return "redirect:/";
//
//    }

//    @PostMapping(value = "/logout")
//    public String logout(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = true) String logout, Model model){
//        if(logout!=null){
//
//        }
//    }


}


package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.model.Usuario;
import com.syscawfit.syscawfit.exceptions.UsuarioNotFoundException;
import com.syscawfit.syscawfit.services.UsuarioPrincipalDetailsService;
import com.syscawfit.syscawfit.services.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class RedefinirSenhaController {

    @Autowired
    private UsuarioPrincipalDetailsService usuarioPrincipalDetailsService;
    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("/redefinirSenha")
    public String mostrarFormRedefinirSenhaEmail(Model model) {
        model.addAttribute("pageTitle", "Redefinir a senha");
        return "redefinirSenhaForm.html";
    }

    @PostMapping("/redefinirSenha")
    public String processarFormRedefinirSenha(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        try {
            usuarioPrincipalDetailsService.atualizarTokenSenhaRedefinida(token, email);
            String redefinirSenhaLink = Utility.getSiteURL(request) + "/alterarSenha?token=" + token;
            sendEmail(email, redefinirSenhaLink);
            model.addAttribute("message", "O link para redefinição de senha foi enviado, por favor verifique a caixa de entrada do seu e-mail!");

        } catch (UsuarioNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", "Erro ao enviar e-mail.");
        }
        model.addAttribute("pageTitle", "Redefinir a senha");
        return "redefinirSenhaForm.html";
    }

    private void sendEmail(String email, String redefinirSenhaLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("syscawfit@gmail.com", "SYSCAWFIT");
        helper.setTo(email);

        String subject = "Aqui está o link para redefinir a sua senha!";
        String content = "<p>Olá,</p>"
                + "<p>Você requisitou a redefinição da sua senha.</p>"
                + "<p>Clique no link para alterá-la:</p>"
                + "<p><b><a href=\"" + redefinirSenhaLink + "\">Alterar minha senha</a><b></p>"
                + "<p>Ignore este e-mail se você não fez essa solicitação.";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/alterarSenha")
    public String mostrarFormAlterarSenha(@Param(value = "token") String token, Model model) {
        Usuario usuario = usuarioPrincipalDetailsService.getUsuarioRedefinido(token);
        if (usuario == null) {
            model.addAttribute("title", "Alterar a senha");
            model.addAttribute("error", "Token inválido!");
            return "/redefinirSenhaForm";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Alterar a senha");
        return "alterarSenhaForm.html";
    }

    @PostMapping("/alterarSenha")
    public String processarAlterarSenha(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String senha = request.getParameter("txtPassword");
        Usuario usuario = usuarioPrincipalDetailsService.getUsuarioRedefinido(token);
        if (usuario == null) {
            model.addAttribute("title", "Alterar a senha");
            model.addAttribute("error", "Token inválido!");
            return "/redefinirSenhaForm";
        }else {
            usuarioPrincipalDetailsService.atualizarSenha(usuario, senha);
            model.addAttribute("message", "Senha redefinida com sucesso!");

            return "/login";
        }
    }
}

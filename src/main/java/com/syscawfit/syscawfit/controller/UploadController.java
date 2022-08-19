package com.syscawfit.syscawfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/upload")
public class UploadController {


    public static String UPLOUD_DIRECTORY = System.getProperty("user.dir") + "/uploads";


    // Recebe a solicitação e retorna a página para exibir ao usuário, para permitir que ele importe uma imagem
    @GetMapping("/uploadimage")
    public String displayUploudForm(){
        return "imageupload/index";
    }

    //Aceita uma solicitação e salva a imagem no sistema de arquivos local
    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image")MultipartFile file) throws IOException {

        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOUD_DIRECTORY, file.getOriginalFilename());

        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());

        return "imageupload/index";
    }







}

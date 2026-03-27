package mx.edu.uteq.idgs15.ejemplo01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import mx.edu.uteq.idgs15.ejemplo01.dto.EmailDTO;
import mx.edu.uteq.idgs15.ejemplo01.service.EmailService;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/email/form")
    public String form (Model model, EmailDTO emailDTO) {
        model.addAttribute("emailDTO", emailDTO);
        return "emailForm";
    }

    @Async
    @PostMapping("/email/enviar")
    public String page (@ModelAttribute EmailDTO emailDTO) {
        try {
            emailService.enviarHtmlEmail(emailDTO);
        } catch (Exception e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
        return "redirect:/";
    }

}

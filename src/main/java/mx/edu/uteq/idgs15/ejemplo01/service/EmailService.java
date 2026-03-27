package mx.edu.uteq.idgs15.ejemplo01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import mx.edu.uteq.idgs15.ejemplo01.dto.EmailDTO;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    public void enviarHtmlEmail(EmailDTO dto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                true, "UTF-8");

        // 1. Preparar datos para la plantilla
        Context context = new Context();
        context.setVariable("nombre", dto.getAsunto());
        context.setVariable("mensaje", dto.getMensaje());
        context.setVariable("codigo", (int) (Math.random() * 99999)); // Dato de ejemplo

        // 2. Crear el cuerpo del correo procesado el HTML
        String htmlContent = templateEngine.process("email-template", context);

        // 3. Configurar el envío
        helper.setTo(dto.getDestinatario());
        helper.setSubject("Notificación Especial");
        helper.setText(htmlContent, true); // TRUE indica que es HTML

        mailSender.send(message);
    }
}

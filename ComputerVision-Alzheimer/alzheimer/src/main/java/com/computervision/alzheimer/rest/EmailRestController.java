package com.computervision.alzheimer.rest;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmailRestController {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    Environment environment;

    @PostMapping("/mail")
    public String sendMail(@RequestParam String to, @RequestParam String text, @RequestParam String subject) throws Exception {
        try {
//            SimpleMailMessage message = new SimpleMailMessage();
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(environment.getProperty("spring.mail.username"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
            emailSender.send(message);
        }catch (Exception e){
            throw new Exception("Error sending mail");
        }
        return text;
    }

}

package com.brovko.notificationservice.controller;

import com.brovko.notificationservice.model.EmailDetails;
import com.brovko.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
     private final EmailService emailService;

    @PostMapping("/sendMail")
    public EmailDetails sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);

        return details;
    }


    @PostMapping("/sendMailToFollowers")
    public EmailDetails sendMailToFollowers(@RequestBody EmailDetails details) {
        String status = emailService.sendEmailToFollowers(details);
        return details;
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        String status = emailService.sendMailWithAttachment(details);

        return status;
    }
}

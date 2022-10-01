package com.brovko.notificationservice.service;

import com.brovko.notificationservice.model.EmailDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            log.info("{}",details);

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipients().get(0));
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }



    @Override
    public String sendMailWithAttachment(EmailDetails details) {

        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipients().get(0));
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }

    @Override
    public String sendEmailToFollowers(EmailDetails details) {
        try {
            for (int i = 0; i < details.getRecipients().size(); i++) {
                SimpleMailMessage mailMessage
                        = new SimpleMailMessage();

                mailMessage.setFrom(sender);
                mailMessage.setTo(details.getRecipients().get(i));
                mailMessage.setText(details.getMsgBody());
                mailMessage.setSubject(details.getSubject());

                javaMailSender.send(mailMessage);
            }
            return "Mails sent successfully";
        } catch (Exception e) {
            return "Eror while sending messages";
        }
    }
}

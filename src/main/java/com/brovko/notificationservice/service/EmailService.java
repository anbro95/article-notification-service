package com.brovko.notificationservice.service;

import com.brovko.notificationservice.model.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}

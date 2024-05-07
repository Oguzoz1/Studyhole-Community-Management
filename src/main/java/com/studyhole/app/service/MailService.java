package com.studyhole.app.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.studyhole.app.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@org.springframework.context.annotation.Lazy
public class MailService {
    private final JavaMailSender sender;

    @Async
    public void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("studyholeproject@start.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try{
            sender.send(messagePreparator);
            log.info("Activation Email is sent!");
        } catch (MailException e){
            log.error("Problem occurred during email transmission", e);
            // throw new StudyholeException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }
}

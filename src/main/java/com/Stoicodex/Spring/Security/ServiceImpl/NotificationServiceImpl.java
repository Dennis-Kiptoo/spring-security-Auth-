package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Services.NotificationService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender mailSender;

   // @Autowired
    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
   @Value("${spring.sms.twilio.account-sid}")
    private String twilioAccountSid;

    @Value("${spring.sms.twilio.auth-token}")
    private String twilioAuthToken;

    @Value ("${spring.sms.twilio.phone-number}")
    private String twilioPhoneNumber;

    @Override
    public void sendEmailNotification(String to, String body, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);

        // send email
    }

    @Override
    public void sendSMSNotification(String to, String body) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                body
        ).create();
        // send sms
    }



}

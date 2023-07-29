package com.example.job_portal_master.ServiceImpl;

import com.example.job_portal_master.Entity.Email;
import com.example.job_portal_master.Entity.candidatedetails;
import com.example.job_portal_master.Service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private candidatedetails candidatedetails;



        @Autowired
        public EmailServiceImpl(JavaMailSender javaMailSender) {
            this.javaMailSender = javaMailSender;
        }

        @Override
        public void sendEmail(Email email) throws MessagingException {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            try {
                helper.setFrom("muhammedshadeel571@gmail.com");
                helper.setTo(email.getTo());
                helper.setSubject(email.getSubject());
                helper.setText(email.getBody(), true);

                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                // Handle messaging exception
                throw e;
            } catch (Exception e) {
                // Handle other exceptions
                // For example, you may log the error or display a custom error message
                e.printStackTrace();
            }
        }
    }





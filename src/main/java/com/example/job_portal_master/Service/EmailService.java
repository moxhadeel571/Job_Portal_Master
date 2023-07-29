package com.example.job_portal_master.Service;

import com.example.job_portal_master.Entity.CompanyDetails;
import com.example.job_portal_master.Entity.Email;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {



    void sendEmail(Email email) throws MessagingException;
}

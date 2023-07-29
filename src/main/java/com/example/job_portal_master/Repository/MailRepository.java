package com.example.job_portal_master.Repository;

import com.example.job_portal_master.Config.MailConfig;
import com.example.job_portal_master.Entity.Email;
import com.example.job_portal_master.Entity.candidatedetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends MongoRepository<Email,Long> {
}

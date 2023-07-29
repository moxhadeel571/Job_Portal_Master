package com.example.job_portal_master.Repository;

import com.example.job_portal_master.Entity.candidatedetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<candidatedetails, String> {

}

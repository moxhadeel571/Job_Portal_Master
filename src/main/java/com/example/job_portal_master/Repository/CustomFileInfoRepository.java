package com.example.job_portal_master.Repository;


import com.example.job_portal_master.Entity.FileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFileInfoRepository extends MongoRepository<FileInfo, String> {
}

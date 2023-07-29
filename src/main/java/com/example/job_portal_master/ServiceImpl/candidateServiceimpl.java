package com.example.job_portal_master.ServiceImpl;


import com.example.job_portal_master.Entity.candidatedetails;
import com.example.job_portal_master.Repository.JobRepository;
import com.example.job_portal_master.Service.candidateService;
import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class candidateServiceimpl implements candidateService {
    private final MongoTemplate mongoTemplate;
    private final GridFsTemplate gridFsTemplate;
    private String candidateId;

    @Autowired
    public candidateServiceimpl(JobRepository jobRepository, MongoTemplate mongoTemplate, GridFsTemplate gridFsTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.gridFsTemplate = gridFsTemplate;
    }
    @Override
    public List<candidatedetails> getAllCandidates(String fileName) {
        Query query = new Query();
        List<candidatedetails> candidates = mongoTemplate.find(query, candidatedetails.class);
        return candidates;
    }

//

    @Override
    public ObjectId saveJobDetails(MultipartFile file, candidatedetails candidatedetails) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null.");
        }

        // Extract file information
        byte[] fileData = file.getBytes();
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        // Create a new document to store the job details
        Document jobDocument = new Document();
        jobDocument.append("firstName", candidatedetails.getFirstName())
                .append("lastName", candidatedetails.getLastName())
                .append("fullName", candidatedetails.getFullName())
                .append("email", candidatedetails.getEmail())
                .append("gender", candidatedetails.getGender())
                .append("education", candidatedetails.getEducation())
                .append("qualification", candidatedetails.getQualification())
                .append("graduationPercentage", candidatedetails.getGraduationPercentage())
                .append("experience", candidatedetails.getExperience())
                .append("location", candidatedetails.getLocation())
                .append("country", candidatedetails.getCountry())
                .append("address", candidatedetails.getAddress())
                .append("skills", candidatedetails.getSkills())
                .append("message", candidatedetails.getMessage())
                .append("status", candidatedetails.getStatus())
                .append("fileData", new Binary(BsonBinarySubType.BINARY, fileData))
                .append("fileName", fileName)
                .append("contentType", contentType)
                .append("addedDate", new Date())  // Current date
                .append("addedTime", new Date()); // Current time

        // Save the job details to MongoDB
        mongoTemplate.getCollection("jobCollection").insertOne(jobDocument);

        // Return the generated _id of the job document
        return jobDocument.getObjectId("_id");
    }


    @Override
    public void deleteJobDetailsById(Long id) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));

    }
    @Override
    public byte[] getCandidateFileData(String candidateId) {
        Query query = Query.query(Criteria.where("_id").is(candidateId));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "jobCollection");

        if (candidateDocument != null) {
            Binary fileData = candidateDocument.get("fileData", Binary.class);
            if (fileData != null) {
                return fileData.getData();
            }
        }

        return null;
    }

    @Override
    public String getCandidateFileName(String candidateId) {
        Query query = Query.query(Criteria.where("_id").is(candidateId));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "jobCollection");

        if (candidateDocument != null) {
            return candidateDocument.getString("fileName");
        }

        return null;
    }



    @Override
    public String getCandidateContentType(String candidateId) {
        Query query = Query.query(Criteria.where("_id").is(candidateId));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "jobCollection");

        if (candidateDocument != null) {
            return candidateDocument.getString("contentType");
        }

        return null;
    }


}

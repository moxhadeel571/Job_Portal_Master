package com.example.job_portal_master.ServiceImpl;

import com.example.job_portal_master.Entity.FileInfo;
import com.example.job_portal_master.Entity.candidatedetails;
import com.example.job_portal_master.Service.FileService;
import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class FileServiceImpl implements FileService {

    private  FileInfo fileInfo;
    private final MongoTemplate mongoTemplate;
@Autowired
    public FileServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public ObjectId saverandom(MultipartFile file, FileInfo fileInfo) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        byte[] fileData = file.getBytes();
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        Document randowmDocument=new Document();
        randowmDocument.append("name",fileInfo.getName())
                .append("contentType",fileInfo.getFileContentType())
                .append("fileData",new Binary(BsonBinarySubType.BINARY,fileData))
                .append("fileName",fileName)
                .append("fileSize",fileData.length)
                .append("contentType", contentType)
                .append("email",fileInfo.getEmail())
                .append("experience",fileInfo.getExperience())
                .append("message",fileInfo.getMessage())
                .append("addedDate", new Date())  // Current date
                .append("addedTime", new Date()); // Current time
        mongoTemplate.getCollection("random_resume_file").insertOne(randowmDocument);
        return randowmDocument.getObjectId("_id");
    }

    @Override
    public String getContentType(String staticCandidateId) {
        Query query = Query.query(Criteria.where("_id").is(staticCandidateId));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "random_resume_file");

        if (candidateDocument != null) {
            return candidateDocument.getString("contentType");
        }

        return null;
    }

    @Override
    public String getFileName(String staticCandidateId) {
        Query query = Query.query(Criteria.where("_id").is(staticCandidateId));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "random_resume_file");

        if (candidateDocument != null) {
            return candidateDocument.getString("fileName");
        }

        return null;
    }

    @Override
    public byte[] getFileData(String staticCandidateId) {
        Query query = Query.query(Criteria.where("_id").is(staticCandidateId));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "random_resume_file");

        if (candidateDocument != null) {
            Binary fileData = candidateDocument.get("fileData", Binary.class);
            if (fileData != null) {
                return fileData.getData();
            }
        }

        return null;
}

    @Override
    public List<FileInfo> getAllfiles(String fileName) {
        Query query = new Query();
        List<FileInfo> random = mongoTemplate.find(query, FileInfo.class);
        return random;
    }


}






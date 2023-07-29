package com.example.job_portal_master.Entity;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Document(collection = "random_resume_file")
public class FileInfo {
    @Id
    private String id;
    private String name;
    private String email;
    private String experience;
    private String message;
    private MultipartFile pdf;
    @Field("file_reference")
    private String fileReference;

    @Field("file_name")
    private String fileName;

    @Field("file_content_type")
    private String fileContentType;

    @Field("file_data")
    private byte[] fileData;

    @CreatedDate
    @Field("added_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date addedDate;

    @Field("added_time")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date addedTime;


    // Add getters and setters
}


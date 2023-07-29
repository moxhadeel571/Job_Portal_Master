package com.example.job_portal_master.Entity;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "jobCollection")

public class candidatedetails {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String gender;
    private String education;
    private String qualification;
    private Integer graduationPercentage;
    private String experience;
    private String location;
    private String country;
    private String address;
    private String skills;
    private String message;
    private Boolean status;
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

    public candidatedetails(String fullName, String email, String gender, Binary fileData, String message, Boolean status, Date date, Date dateTime, String education, String qualification, String experience, String location, String country, String address, String skills) {
    }
}
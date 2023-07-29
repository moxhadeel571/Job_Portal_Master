package com.example.job_portal_master.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "companyDetails")
public class CompanyDetails {
    @Id
    private String id;
    private String title;
    private String company;
    private String EmailAddress;
    private String location;
    private String Address;
    private String experience;
    private String remote;
    private String jobMode;
    private String description;
    private String Key_Responsibilities;
    private String Requirements;
    private Double salaryMin;
    private Double salaryMax;
    private Integer experienceYears;

}

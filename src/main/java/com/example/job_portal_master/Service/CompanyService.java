package com.example.job_portal_master.Service;
import com.example.job_portal_master.Entity.CompanyDetails;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan(basePackages = "com.example.job_portal_master.Service")
public interface CompanyService {
    List<CompanyDetails> getAllCompanyDetails();
    CompanyDetails saveCompanyDetails(CompanyDetails companyDetails);
    void deleteCompanyDetailsById(Long id);


}

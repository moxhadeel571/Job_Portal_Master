package com.example.job_portal_master.ServiceImpl;

import com.example.job_portal_master.Entity.CompanyDetails;
import com.example.job_portal_master.Repository.CompanyRepository;
import com.example.job_portal_master.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyImpl implements CompanyService {
    private CompanyDetails companyDetails;
    @Autowired
    public CompanyImpl(MongoTemplate template, CompanyRepository companyRepository, com.example.job_portal_master.Repository.CompanyRepository companyRepository1) {
        this.template = template;
        CompanyRepository = companyRepository1;
    }

    private MongoTemplate template;

    private final CompanyRepository CompanyRepository;

    public CompanyImpl(CompanyRepository companyDetailsRepository) {
        this.CompanyRepository = companyDetailsRepository;
    }


    @Override
    public List<CompanyDetails> getAllCompanyDetails() {
        return CompanyRepository.findAll();
    }



    @Override
    public CompanyDetails saveCompanyDetails(CompanyDetails companyDetails) {
        return CompanyRepository.save(companyDetails);
    }

    @Override
    public void deleteCompanyDetailsById(Long id)   {
        CompanyRepository.deleteById(String.valueOf(id));

    }


}



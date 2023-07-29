package com.example.job_portal_master.Controller;

import com.example.job_portal_master.Entity.CompanyDetails;
import com.example.job_portal_master.Entity.FileInfo;
import com.example.job_portal_master.Entity.candidatedetails;
import com.example.job_portal_master.Repository.CompanyRepository;
import com.example.job_portal_master.Service.CompanyService;
import com.example.job_portal_master.Service.EmailService;
import com.example.job_portal_master.Service.FileService;
import com.example.job_portal_master.Service.candidateService;
import lombok.Value;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class CandidateController {

    private CompanyService companyService;
    private MongoTemplate mongoTemplate;
    private com.example.job_portal_master.Service.candidateService candidateService;
    private FileService fileService;
    private CompanyRepository companyRepository;
    private HttpSession httpSession;
    private EmailService emailService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    public void setCandidateService(candidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }



    @GetMapping("/Displaycompany")  //candidate user
    public String showAppliedCompanies(Model model,String candidate) {
        List<CompanyDetails> companyDetails1 = companyService.getAllCompanyDetails();
        model.addAttribute("company_display", companyDetails1);
        List<candidatedetails> candidatedetailsList = candidateService.getAllCandidates(candidate);
        model.addAttribute("jobdisplay", candidatedetailsList);
        return "candidatedashboard";
    }
    @PostMapping("/saveresume")//candidate
    public String saveResume(@RequestParam("fileupload") MultipartFile file, FileInfo fileInfo) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("No file selected");
            }
            ObjectId jobId = fileService.saverandom(file, fileInfo);
            return "redirect:/joblisting";
        } catch (IOException e) {
            throw new RuntimeException("Failed to process the file", e);
        }
    }
    @GetMapping("/joblisting")//candidate
    public String joblisting(@ModelAttribute("metadata")FileInfo fileInfo,
                             HttpSession session,
                             Model model,candidatedetails candidatedetails, CompanyDetails companyDetails) {
        List<CompanyDetails> companyDetails1 = companyService.getAllCompanyDetails();
        model.addAttribute("jobForm", candidatedetails);
        String jobROle=companyDetails.getTitle();
        model.addAttribute("showjobs", companyDetails1);
        model.addAttribute("alertMessage", "You have successfully applied for the job.");

        return "jobview";
    }
    @GetMapping("/viewjob/{id}")//candidate
    public String viewJob(@ModelAttribute("metadata")FileInfo fileInfo,@PathVariable("id") String id, Model model,candidatedetails candidatedetails) {
        CompanyDetails jobPost = companyRepository.findById(id).orElse(null);
        model.addAttribute("viewing", jobPost);
        List<CompanyDetails> companyDetails1 = companyService.getAllCompanyDetails();
        model.addAttribute("jobForm", candidatedetails);
        return "viewjob";
    }
    @GetMapping("/upload") //candidate user random upload
    public String jobList(@ModelAttribute("jobForm") candidatedetails candidatedetails,
                          CompanyDetails companyDetails,
                          Model model) throws Exception {
        Document demoDocument = new Document();

        mongoTemplate.insert(demoDocument);

        return "redirect:/joblisting";
    }
    @GetMapping("/companylisting") //candidate user
    public String companyList(@ModelAttribute("companyForm") CompanyDetails companyDetails,
                              Model model,
                              String candidate) {
        List<candidatedetails> candidatedetailsList = candidateService.getAllCandidates(candidate); {
            model.addAttribute("companyForm", companyDetails);
            List<CompanyDetails> companyDetails1 = companyService.getAllCompanyDetails();
            model.addAttribute("company", companyDetails1);
            model.addAttribute("candidates", candidatedetailsList);
            return "test";
        }
}
}

package com.example.job_portal_master.Controller;

import com.example.job_portal_master.Entity.CompanyDetails;
import com.example.job_portal_master.Entity.Email;
import com.example.job_portal_master.Entity.FileInfo;
import com.example.job_portal_master.Entity.candidatedetails;
import com.example.job_portal_master.Repository.CompanyRepository;
import com.example.job_portal_master.Service.CompanyService;
import com.example.job_portal_master.Service.EmailService;
import com.example.job_portal_master.Service.FileService;
import com.example.job_portal_master.Service.candidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RecruiterController {

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
    @GetMapping("/recruitor")//recruitor
    public String about() {
        return "about";
    }
    @GetMapping("/profile")//recruitor profile
    public String profile(@ModelAttribute("companyDetails") CompanyDetails companyDetails) {
        return "recruitordashboard";
    }

    @GetMapping("/Displaycandidates")   //recruiter user
    public String showAppliedJobs(
            @ModelAttribute("Data_info") Email email,
            Model model, CompanyDetails companyDetails, candidatedetails candidatedetails, String fileName) {
        List<candidatedetails> RegCandidate = candidateService.getAllCandidates(fileName);
        model.addAttribute("RegCandidate", RegCandidate);
        List<CompanyDetails> companyDetail=companyService.getAllCompanyDetails();
        model.addAttribute("companyview", companyDetail);
        return "Recruiter_page";
    }
    @PostMapping("/send_email")//recruitor
    public String sendEmail(@ModelAttribute("Data_info") Email email) throws MessagingException {
        // Create the email object

        // Send the automated email, passing the CompanyDetails object
        emailService.sendEmail(email);
        System.setProperty("mail.debug", "true");

        // Redirect to the URL mapping for displaying candidates, without the leading slash
        return "redirect:/Displaycandidates";
    }
    @DeleteMapping("/company_delete/{id}")//recruitor
    public String deleteCandidate(@PathVariable String id) {
        companyService.deleteCompanyDetailsById(Long.parseLong(id));
        return "redirect:/Displaycandidates";
    }
    @GetMapping("/resumedashboard")//recruitor
    public String resumedashboar(@ModelAttribute("metadata") FileInfo fileInfo,
                                 @ModelAttribute("Data_info_random") Email email,Model model,
                                 String candidate,candidatedetails candidatedetails) {
        List<CompanyDetails> companyDetails1 = companyService.getAllCompanyDetails();
        List<FileInfo> candidatedetailsinfo = fileService.getAllfiles(candidate);

        model.addAttribute("candidate", candidatedetailsinfo);
        model.addAttribute("showjobs", companyDetails1);

        return "dashboard";
    }
    @PostMapping("/send_email_resume")//recruitor
    public String sendEmailtoRandom(@ModelAttribute("Data_info_random") Email email) throws MessagingException {
        // Create the email object

        // Send the automated email, passing the CompanyDetails object
        emailService.sendEmail(email);
        System.setProperty("mail.debug", "true");

        // Redirect to the URL mapping for displaying candidates, without the leading slash
        return "redirect:/resumedashboard";
    }
    @PostMapping("/savecompany") //recruiter user
    public String saveCompanyDetails(@ModelAttribute("companyDetails") CompanyDetails companyDetails,
                                     Model model,
                                     String candidate) {
        List<candidatedetails> candidatedetailsList = candidateService.getAllCandidates(candidate);
        model.addAttribute("jobdisplay", candidatedetailsList);
        companyService.saveCompanyDetails(companyDetails);
        model.addAttribute("companyDetails", companyDetails);
        List<CompanyDetails> companyDetails1 = companyService.getAllCompanyDetails();
        model.addAttribute("companydisplay", companyDetails1);

        return "Recruiter_page";
    }
    @GetMapping("/download_static/{candidateId}") ////this is for the candidate posting
    public ResponseEntity<Resource> downloadCandidateFile(@PathVariable String candidateId) {
        byte[] fileData = candidateService.getCandidateFileData(candidateId);
        String fileName = candidateService.getCandidateFileName(candidateId);
        String contentType = candidateService.getCandidateContentType(candidateId);

        if (fileData != null) {
            ByteArrayResource resource = new ByteArrayResource(fileData);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(MediaType.parseMediaType(contentType))
                    .contentLength(fileData.length)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/download/{staticCandidateId}") //recruitor
    public ResponseEntity<Resource> downlaodresume(@PathVariable String staticCandidateId) {
        byte[] fileData = fileService.getFileData(staticCandidateId);
        String fileName = fileService.getFileName(staticCandidateId);
        String contentType = fileService.getContentType(staticCandidateId);

        if (fileData != null) {
            ByteArrayResource resource = new ByteArrayResource(fileData);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(MediaType.parseMediaType(contentType))
                    .contentLength(fileData.length)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }


}
}

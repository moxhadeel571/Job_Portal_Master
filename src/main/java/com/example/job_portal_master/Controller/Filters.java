package com.example.job_portal_master.Controller;

import com.example.job_portal_master.Entity.CompanyDetails;
import com.example.job_portal_master.Repository.CompanyRepository;
import com.example.job_portal_master.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/filters")
public class Filters {
    private final CompanyRepository CompanyRepository;

    @Autowired
    public Filters(CompanyRepository CompanyRepository) {
        this.CompanyRepository = CompanyRepository;
    }
//
//    @GetMapping("/search")
//    public String search(@RequestParam("keyword") String keyword, Model model) {
//        List<CompanyDetails> searchResults = companyService.searchData(keyword);
//        model.addAttribute("results", searchResults);
//        return "redirect:/joblisting"; // Create a Thymeleaf template with this name to display the search results
//    }
//@GetMapping("/search")
//public String searchPersons(@RequestParam String query, Model model) {
//   List<CompanyDetails> results= companyService.searchData(query);
//   model.addAttribute("results", results);
//   model.addAttribute("input",query);
//   return "redirect:/joblisting";
//}
@GetMapping("/search{query}")
public ResponseEntity<?> searchCompanyDetails(@RequestParam("query") String query,Model model)
        {
    List<CompanyDetails> response= CompanyRepository.searchByTitle(query);
    return ResponseEntity.ok(response);
}
@GetMapping("/searchlocation{location}")
public ResponseEntity<?> searchLocation(@RequestParam("location") String location,Model model)
        {
    List<CompanyDetails> response= CompanyRepository.searchByLocation(location);
    return ResponseEntity.ok(response);
}
    @GetMapping("/jobs")
    public List<CompanyDetails> getJobs(@RequestParam(value = "remote", required = false) Boolean remote) {
        if (remote != null && remote) {
            // Fetch remote jobs from MongoDB
            return CompanyRepository.findByJobModeIgnoreCaseContaining("remote");
        } else {
            // Fetch all jobs from MongoDB
            return CompanyRepository.findAll();
        }
    }

}

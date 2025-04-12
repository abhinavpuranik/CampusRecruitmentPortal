package com.example.demo.controller;
import com.example.demo.model.Feedback;
import com.example.demo.repository.FeedbackRepository;



import com.example.demo.model.Company;
import com.example.demo.model.JobDescription;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.JobDescriptionRepository;
import com.example.demo.model.JobDescription.JobType;


import java.math.BigDecimal;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository companyRepo;
    private final JobDescriptionRepository jobDescriptionRepo;
    private final FeedbackRepository feedbackRepo;

    public CompanyController(CompanyRepository companyRepo, JobDescriptionRepository jobDescriptionRepo, FeedbackRepository feedbackRepo) {
        this.companyRepo = companyRepo;
        this.jobDescriptionRepo = jobDescriptionRepo;
        this.feedbackRepo = feedbackRepo;
    }

    // 🔹 Show all job descriptions
    @GetMapping("/jobs")
    public String listJobs(@RequestParam(value = "gpa", required = false) String gpa, @RequestParam(value = "type", required = false) String type, Model model) {

        List<JobDescription> jobs = jobDescriptionRepo.findAll();
        switch (gpa != null ? gpa : "") {
            case "below7" -> jobs = jobDescriptionRepo.findByGpaCutoffLessThan(new BigDecimal("7.0"));
            case "below8" -> jobs = jobDescriptionRepo.findByGpaCutoffLessThan(new BigDecimal("8.0"));
            case "below8_5" -> jobs = jobDescriptionRepo.findByGpaCutoffLessThan(new BigDecimal("8.5"));
            case "above9" -> jobs = jobDescriptionRepo.findByGpaCutoffGreaterThanEqual(new BigDecimal("9.0"));
            default -> jobs = jobDescriptionRepo.findAll();
        }

        if (type != null && !type.isEmpty()) {
            try {
                JobType selectedType = JobType.valueOf(type.toUpperCase());
                jobs = jobs.stream()
                        .filter(job -> job.getType() == selectedType)
                        .toList();
            } catch (IllegalArgumentException e) {
                // Ignore invalid type
            }
        }
        model.addAttribute("jobs", jobs);
        return "templates/job_list";
    }

    @GetMapping("/jobs/feedbacks")
    public String showFeedbacksForJob(@RequestParam("jobId") Integer jobId, Model model) {
        JobDescription job = jobDescriptionRepo.findById(Long.valueOf(jobId)).orElseThrow();
        List<Feedback> feedbacks = feedbackRepo.findByJobDescriptionId(jobId);
        model.addAttribute("job", job);
        model.addAttribute("feedbacks", feedbacks);
        return "templates/job_feedbacks";
    }



    // 🔹 Show all companies
    @GetMapping
    public String list(Model model) {
        model.addAttribute("companies", companyRepo.findAll());
        return "templates/list";
    }

    // 🔹 Home page
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 🔹 Show create company form
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/form";
    }

    // 🔹 Save or update company
    @PostMapping("/save")
    public String save(@ModelAttribute Company company) {
        companyRepo.save(company);
        return "redirect:/companies";
    }

    // 🔹 Show edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyRepo.findById(id).orElseThrow());
        return "company/form";
    }

    // 🔹 Delete company
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        companyRepo.deleteById(id);
        return "redirect:/companies";
    }
}
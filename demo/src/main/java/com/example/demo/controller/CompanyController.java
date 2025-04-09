package com.example.demo.controller;

import com.example.demo.model.Company;
import com.example.demo.model.JobDescription;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.JobDescriptionRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository companyRepo;
    private final JobDescriptionRepository jobDescriptionRepo;

    public CompanyController(CompanyRepository companyRepo, JobDescriptionRepository jobDescriptionRepo) {
        this.companyRepo = companyRepo;
        this.jobDescriptionRepo = jobDescriptionRepo;
    }

    // 🔹 Show all job descriptions
    @GetMapping("/jobs")
    public String listJobs(Model model) {
        List<JobDescription> jobs = jobDescriptionRepo.findAll();
        model.addAttribute("jobs", jobs);
        return "templates/job_list";
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

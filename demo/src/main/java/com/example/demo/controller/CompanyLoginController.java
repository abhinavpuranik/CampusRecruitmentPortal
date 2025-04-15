package com.example.demo.controller;

import com.example.demo.model.Company;
import com.example.demo.model.Interview;
import com.example.demo.model.JobDescription;
import com.example.demo.model.PlacementResult;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.InterviewRepository;
import com.example.demo.repository.JobDescriptionRepository;
import com.example.demo.repository.PlacementResultRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/company-login")
public class CompanyLoginController {

    private final CompanyRepository companyRepo;
    private final InterviewRepository interviewRepo;
    private final PlacementResultRepository placementResultRepo;
    private final JobDescriptionRepository jobDescriptionRepo;


    public CompanyLoginController(CompanyRepository companyRepo, InterviewRepository interviewRepo, PlacementResultRepository placementResultRepo, JobDescriptionRepository jobDescriptionRepo) {
        this.jobDescriptionRepo = jobDescriptionRepo;
        this.companyRepo = companyRepo;
        this.interviewRepo = interviewRepo;
        this.placementResultRepo = placementResultRepo;
    }

    @GetMapping
    public String showLoginForm() {
        return "templates/company_login"; // company login page
    }

    @PostMapping
    public String processLogin(@RequestParam Long id, @RequestParam String name, Model model) {
        Company company = companyRepo.findByIdAndName(id, name);
        if (company == null) {
            model.addAttribute("notFound", true);
            return "templates/company_login";
        }

        model.addAttribute("company", company);
        return "templates/company_home"; // dashboard with options
    }

    @GetMapping("/interviews")
    public String viewInterviews(@RequestParam Long id, Model model) {
        Company company = companyRepo.findById(id).orElse(null);
        if (company == null) {
            return "redirect:/company-login";
        }

        List<Interview> interviews = interviewRepo.findByCompany(company);
        model.addAttribute("interviews", interviews);
        model.addAttribute("company", company);
        return "templates/interview_list"; // interview list page
    }
    @PostMapping("/interviews/delete/{id}")
    public String deleteInterview(@PathVariable Long id) {
        Interview interview = interviewRepo.findById(id).orElse(null);

        interviewRepo.deleteById(id);
        return "redirect:/company-login/interviews?id=" + interview.getCompany().getId();
    }

    @PostMapping("/interviews/round/up/{id}")
    public String increaseRound(@PathVariable Long id) {
        Interview interview = interviewRepo.findById(id).orElse(null);
        if (interview == null) return "redirect:/company-login/interviews";

        String roundStr = interview.getInterviewRound().replace("Round ", "");
        int currentRound = Integer.parseInt(roundStr);

        if (currentRound < 3) {
            interview.setInterviewRound("Round " + (currentRound + 1));
            interviewRepo.save(interview);
        }

        return "redirect:/company-login/interviews?id=" + interview.getCompany().getId();
    }


    @PostMapping("/interviews/round/down/{id}")
    public String decreaseRound(@PathVariable Long id) {
        Interview interview = interviewRepo.findById(id).orElse(null);
        if (interview == null) return "redirect:/company-login/interviews";

        String roundStr = interview.getInterviewRound().replace("Round ", "");
        int currentRound = Integer.parseInt(roundStr);

        if (currentRound > 1) {
            interview.setInterviewRound("Round " + (currentRound - 1));
            interviewRepo.save(interview);
        }

        return "redirect:/company-login/interviews?id=" + interview.getCompany().getId();
    }


    @PostMapping("/interviews/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id) {
        Interview interview = interviewRepo.findById(id).orElseThrow();
        Interview.InterviewStatus current = interview.getStatus();

        Interview.InterviewStatus next = switch (current) {
            case Scheduled -> Interview.InterviewStatus.Completed;
            case Completed -> Interview.InterviewStatus.Cancelled;
            case Cancelled -> Interview.InterviewStatus.Scheduled;
        };

        interview.setStatus(next);
        interviewRepo.save(interview);
        return "redirect:/company-login/interviews?id=" + interview.getCompany().getId();
    }

    @PostMapping("/interviews/create-result/{id}")
    public String createPlacementResult(@PathVariable("id") Long interviewId) {
        Interview interview = interviewRepo.findById(interviewId).orElseThrow();

        PlacementResult result = new PlacementResult();
        result.setStudent(interview.getStudent());
        result.setCompany(interview.getCompany());
        result.setJob(interview.getJob());
        result.setOfferType(PlacementResult.OfferType.FULL_TIME);
        result.setStatus(PlacementResult.OfferStatus.ACCEPTED);
        result.setSalaryPackage(interview.getJob().getPackageOffered());

        placementResultRepo.save(result);

        return "redirect:/company-login/interviews?id=" + interview.getCompany().getId();

    }
    @GetMapping("/jobs/add")
    public String showAddJobForm(@RequestParam Long id, Model model) {
        Company company = companyRepo.findById(id).orElseThrow();
        JobDescription jobDescription = new JobDescription();
        jobDescription.setCompany(company);

        model.addAttribute("company", company);
        model.addAttribute("jobDescription", jobDescription);
        return "templates/add_job"; // The form view where you input the job details
    }

    // Handle the form submission to add the new job description
    @PostMapping("/jobs/add")
    public String processAddJob(@ModelAttribute JobDescription jobDescription) {
        // Save the job description to the database
        jobDescriptionRepo.save(jobDescription);

        // Redirect back to the company's interview page or some other page
        return "redirect:/company-login/interviews?id=" + jobDescription.getCompany().getId();
    }




}
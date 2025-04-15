package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.model.JobDescription.JobType;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student-login")
public class LoginController {

    private final StudentRepository studentRepo;
    private final JobDescriptionRepository jobRepo;
    private final InterviewRepository interviewRepo;
    private final FeedbackRepository feedbackRepo;

    @Autowired
    public LoginController(StudentRepository studentRepo,
                           JobDescriptionRepository jobRepo,
                           InterviewRepository interviewRepo,
                           FeedbackRepository feedbackRepo) {
        this.studentRepo = studentRepo;
        this.jobRepo = jobRepo;
        this.interviewRepo = interviewRepo;
        this.feedbackRepo = feedbackRepo;
    }

    @GetMapping
    public String showLoginForm() {
        return "templates/login";
    }

    @PostMapping
    public String processLogin(@RequestParam String srn, @RequestParam String name, Model model, HttpSession session) {
        Student student = studentRepo.findBySrnAndName(srn, name);
        if (student == null) {
            model.addAttribute("notFound", true);
            return "templates/login";
        }

        session.setAttribute("student", student);
        model.addAttribute("student", student);
        return "templates/student_home";
    }

    @GetMapping("/profile")
    public String showStudentProfile(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/student-login";
        }
        model.addAttribute("student", student);
        return "templates/student_dashboard";
    }

    @GetMapping("/jobs")
    public String listJobs(@RequestParam(value = "gpa", required = false) String gpa,
                           @RequestParam(value = "type", required = false) String type,
                           HttpSession session,
                           Model model,
                           @RequestParam(value = "success", required = false) String success) {

        Student student = (Student) session.getAttribute("student");
        if (student == null) return "redirect:/student-login";

        List<JobDescription> jobs;
        switch (gpa != null ? gpa : "") {
            case "below7" -> jobs = jobRepo.findByGpaCutoffLessThan(new BigDecimal("7.0"));
            case "below8" -> jobs = jobRepo.findByGpaCutoffLessThan(new BigDecimal("8.0"));
            case "below8_5" -> jobs = jobRepo.findByGpaCutoffLessThan(new BigDecimal("8.5"));
            case "above9" -> jobs = jobRepo.findByGpaCutoffGreaterThanEqual(new BigDecimal("9.0"));
            default -> jobs = jobRepo.findAll();
        }

        if (type != null && !type.isEmpty()) {
            try {
                JobType selectedType = JobType.valueOf(type.toUpperCase());
                jobs = jobs.stream()
                        .filter(job -> job.getType() == selectedType)
                        .toList();
            } catch (IllegalArgumentException e) {
                // Ignore invalid type filter
            }
        }

        // 🔽 Collect jobIds the student has already applied to
        List<Long> appliedJobIds = jobs.stream()
                .filter(job -> interviewRepo.existsByStudentAndJob(student, job))
                .map(JobDescription::getId)
                .toList();

        model.addAttribute("jobs", jobs);
        model.addAttribute("appliedJobIds", appliedJobIds);  // ✅ Add to model
        model.addAttribute("student", student);
        if (success != null) {
            model.addAttribute("message", success);
        }

        return "templates/job_list";
    }


    @GetMapping("/jobs/feedbacks")
    public String showFeedbacksForJob(@RequestParam("jobId") Integer jobId, Model model) {
        JobDescription job = jobRepo.findById(Long.valueOf(jobId)).orElseThrow();
        List<Feedback> feedbacks = feedbackRepo.findByJobDescriptionId(jobId);
        model.addAttribute("job", job);
        model.addAttribute("feedbacks", feedbacks);
        return "templates/job_feedbacks";
    }

    @GetMapping("/apply")
    public String applyForJob(@RequestParam("jobId") Long jobId, HttpSession session, RedirectAttributes redirectAttributes) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) return "redirect:/student-login";

        Optional<JobDescription> jobOpt = jobRepo.findById(jobId);
        if (jobOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Job not found.");
            return "redirect:/student-login/jobs";
        }

        JobDescription job = jobOpt.get();

        // 🔽 GPA Check
        if (student.getGpa().compareTo(job.getGpaCutoff()) < 0) {
            redirectAttributes.addFlashAttribute("error", "You are not eligible for this job (CGPA below cutoff).");
            return "redirect:/student-login/jobs";
        }

        // 🔽 Already applied check
        if (interviewRepo.existsByStudentAndJob(student, job)) {
            redirectAttributes.addFlashAttribute("error", "You have already applied to this job.");
            return "redirect:/student-login/jobs";
        }

        // 🔽 Eligibility state check
        if (!student.getCurrentState().canApplyToJob(student, job)) {
            redirectAttributes.addFlashAttribute("error", "You are not eligible to apply for this job based on your current offer status.");
            return "redirect:/student-login/jobs";
        }

        // 🔽 If eligible, create interview
        Interview interview = new Interview();
        interview.setStudent(student);
        interview.setJob(job);
        interview.setInterviewDate(LocalDateTime.now());
        interviewRepo.save(interview);

        redirectAttributes.addFlashAttribute("success", "Successfully applied to job: " + job.getJobTitle());
        return "redirect:/student-login/jobs";
    }


}

package com.example.demo.controller;

import com.example.demo.model.JobDescription;
import com.example.demo.model.Student;
import com.example.demo.repository.JobDescriptionRepository;
import com.example.demo.repository.StudentRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student-login")
public class LoginController {

    private final StudentRepository studentRepo;
    private final JobDescriptionRepository jobRepo;

    public LoginController(StudentRepository studentRepo, JobDescriptionRepository jobRepo) {
        this.studentRepo = studentRepo;
        this.jobRepo = jobRepo;
    }

    // 🔹 Show login form
    @GetMapping
    public String showLoginForm() {
        return "templates/login"; // refers to templates/login.html
    }

    // 🔹 Handle login form submission
    @PostMapping
    public String processLogin(@RequestParam String srn, @RequestParam String name, Model model) {
        Student student = studentRepo.findBySrnAndName(srn, name);

        if (student == null) {
            model.addAttribute("notFound", true);
            return "templates/login"; // go back to login form with error
        }

        model.addAttribute("student", student);
        return "templates/student_home"; // ✅ NEW: takes user to a page with 2 buttons
    }

    // 🔹 View Profile
    @GetMapping("/profile")
    public String showStudentProfile(@RequestParam String srn, @RequestParam String name, Model model) {
        Student student = studentRepo.findBySrnAndName(srn, name);
        if (student == null) {
            return "redirect:/student-login";
        }
        model.addAttribute("student", student);
        return "templates/student_dashboard"; // shows full profile
    }

    // 🔹 View Jobs after login
    @GetMapping("/jobs")
    public String viewJobs(Model model) {
        List<JobDescription> jobs = jobRepo.findAll();
        model.addAttribute("jobs", jobs);
        return "templates/job_list"; // job list page
    }
}

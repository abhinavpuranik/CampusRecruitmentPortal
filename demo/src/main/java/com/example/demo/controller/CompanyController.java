package com.example.demo.controller;

import com.example.demo.model.Company;
import com.example.demo.repository.CompanyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository companyRepo;

    public CompanyController(CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    // 1️⃣ List all companies
    @GetMapping
    public String list(Model model) {
        model.addAttribute("companies", companyRepo.findAll());
        return "company/list";
    }
    @GetMapping("/")
    public String home() {
        return "home"; // matches home.html in templates/
    }
    // 2️⃣ Show create form
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/form";
    }

    // 3️⃣ Save or update
    @PostMapping("/save")
    public String save(@ModelAttribute Company company) {
        companyRepo.save(company);
        return "redirect:/companies";
    }

    // 4️⃣ Show edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyRepo.findById(id).orElseThrow());
        return "company/form";
    }

    // 5️⃣ Delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        companyRepo.deleteById(id);
        return "redirect:/companies";
    }
}

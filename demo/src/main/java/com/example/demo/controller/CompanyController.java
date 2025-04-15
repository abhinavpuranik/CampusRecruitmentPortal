// Removed /jobs and /feedbacks related code

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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("companies", companyRepo.findAll());
        return "templates/list";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Company company) {
        companyRepo.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyRepo.findById(id).orElseThrow());
        return "company/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        companyRepo.deleteById(id);
        return "redirect:/companies";
    }
}

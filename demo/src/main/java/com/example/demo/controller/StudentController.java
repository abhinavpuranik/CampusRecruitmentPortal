//package com.example.demo.controller;
//
//import com.example.demo.model.Student;
//import com.example.demo.repository.StudentRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/students")
//public class StudentController {
//
//    private final StudentRepository studentRepo;
//
//    public StudentController(StudentRepository studentRepo) {
//        this.studentRepo = studentRepo;
//    }
//
//    @GetMapping("/")
//    public String listStudents(Model model) {
//        List<Student> students = studentRepo.findAll();
//        model.addAttribute("students", students);
//        return "templates/student_list"; // this should match the HTML template
//    }
//}

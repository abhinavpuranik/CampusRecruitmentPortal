package com.example.demo.repository;

import com.example.demo.model.Interview;
import com.example.demo.model.Student;
import com.example.demo.model.Company;

import com.example.demo.model.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // Check if the student has already applied for the job
    boolean existsByStudentAndJob(Student student, JobDescription job);
    List<Interview> findByCompany(Company company);

    // Optionally, find an interview by student and job if needed
    Optional<Interview> findByStudentAndJob(Student student, JobDescription job);
}

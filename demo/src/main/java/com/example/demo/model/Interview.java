// src/main/java/com/example/demo/model/Interview.java
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "interview")
public class Interview {
    public enum InterviewStatus { SCHEDULED, COMPLETED, CANCELED }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_srn")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    
    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobDescription job;
    
    @Column(name = "interview_date", nullable = false)
    private LocalDateTime interviewDate;
    
    @Column(name = "interview_round", length = 50)
    private String interviewRound;
    
    @Enumerated(EnumType.STRING)
    private InterviewStatus status = InterviewStatus.SCHEDULED;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;


    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public JobDescription getJob() { return job; }
    public void setJob(JobDescription job) { this.job = job; }

    public LocalDateTime getInterviewDate() { return interviewDate; }
    public void setInterviewDate(LocalDateTime interviewDate) { 
        this.interviewDate = interviewDate; 
    }

    public String getInterviewRound() { return interviewRound; }
    public void setInterviewRound(String interviewRound) { 
        this.interviewRound = interviewRound; 
    }

    public InterviewStatus getStatus() { return status; }
    public void setStatus(InterviewStatus status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    // Getters, Setters, Constructors
}
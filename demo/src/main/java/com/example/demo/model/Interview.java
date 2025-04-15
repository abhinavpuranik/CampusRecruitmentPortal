package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "interview")
public class Interview {

    public enum InterviewStatus {
        Scheduled,
        Completed,
        Cancelled
    }

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
    @Column(name = "status", length = 20, nullable = false)
    private InterviewStatus status = InterviewStatus.Scheduled;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public JobDescription getJob() {
        return job;
    }

    public void setJob(JobDescription job) {
        this.job = job;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getInterviewRound() {
        return interviewRound;
    }

    public void setInterviewRound(String interviewRound) {
        this.interviewRound = interviewRound;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
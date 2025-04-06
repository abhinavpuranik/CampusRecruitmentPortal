package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;  // Alumni giving feedback

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;  // Company the feedback is about

    private String feedbackText;
    private int rating;        // e.g., 1-5
    private LocalDate dateGiven;

    public Feedback() {
    }

    public Feedback(Student student, Company company, String feedbackText, int rating, LocalDate dateGiven) {
        this.student = student;
        this.company = company;
        this.feedbackText = feedbackText;
        this.rating = rating;
        this.dateGiven = dateGiven;
    }

    // Getters and Setters
    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
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

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getDateGiven() {
        return dateGiven;
    }

    public void setDateGiven(LocalDate dateGiven) {
        this.dateGiven = dateGiven;
    }
}

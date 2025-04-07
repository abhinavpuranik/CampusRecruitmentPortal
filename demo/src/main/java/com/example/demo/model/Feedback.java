// src/main/java/com/example/demo/model/Feedback.java
package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_srn")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "job_description_id")
    private JobDescription jobDescription;
    
    private Integer rating;
    
    @Column(name = "feedback_text")
    private String feedbackText;
    
    @CreationTimestamp
    @Column(name = "date")
    private Timestamp date;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public JobDescription getJobDescription() { return jobDescription; }
    public void setJobDescription(JobDescription jobDescription) { 
        this.jobDescription = jobDescription; 
    }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getFeedbackText() { return feedbackText; }
    public void setFeedbackText(String feedbackText) { 
        this.feedbackText = feedbackText; 
    }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }
    
    // Getters, Setters, Constructors
}
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class JobDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobDescriptionId;

    private String jobTitle;
    private String jobLocation;
    private String jobType;       // e.g., Full-time, Internship, etc.
    private String requirements;  // e.g., skill requirements

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public JobDescription() {
    }

    public JobDescription(String jobTitle, String jobLocation, String jobType, String requirements, Company company) {
        this.jobTitle = jobTitle;
        this.jobLocation = jobLocation;
        this.jobType = jobType;
        this.requirements = requirements;
        this.company = company;
    }

    // Getters and Setters
    public Long getJobDescriptionId() {
        return jobDescriptionId;
    }

    public void setJobDescriptionId(Long jobDescriptionId) {
        this.jobDescriptionId = jobDescriptionId;
    }

    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobLocation() {
        return jobLocation;
    }
    
    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobType() {
        return jobType;
    }
    
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRequirements() {
        return requirements;
    }
    
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Company getCompany() {
        return company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
}

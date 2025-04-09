// src/main/java/com/example/demo/model/JobDescription.java
package com.example.demo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "job_description")
public class JobDescription {
    public enum JobType {SWE, DATA_ANALYTICS}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType type;

    @Column(name = "package_offered", precision = 10, scale = 2, nullable = false)
    private BigDecimal packageOffered;

    @Column(name = "gpa_cutoff", precision = 3, scale = 2)
    private BigDecimal gpaCutoff;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public BigDecimal getPackageOffered() {
        return packageOffered;
    }

    public void setPackageOffered(BigDecimal packageOffered) {
        this.packageOffered = packageOffered;
    }

    public BigDecimal getGpaCutoff() {
        return gpaCutoff;
    }

    public void setGpaCutoff(BigDecimal gpaCutoff) {
        this.gpaCutoff = gpaCutoff;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
    // Getters, Setters, Constructors
}
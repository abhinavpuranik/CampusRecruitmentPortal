// src/main/java/com/example/demo/model/Student.java
package com.example.demo.model;

import com.example.demo.model.PlacementResult.OfferStatus;
import com.example.demo.state.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "srn", length = 15, nullable = false)
    private String srn;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "phone_no", length = 15)
    private String phoneNo;
    
    @Column(name = "graduation_year", nullable = false)
    private Integer graduationYear;
    
    @Column(name = "resume_url")
    private String resumeUrl;
    
    @Column(precision = 3, scale = 2)
    private BigDecimal gpa;
    
    private String branch;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlacementResult> placementResults = new ArrayList<>();

    
    @Transient
    private EligibilityState currentState;

    @PostLoad
    public void initializeState() {
        if (placementResults == null) {
            currentState = new NoOfferState();
            return;
        }
        if (hasTier1Offer()) {
            currentState = new Tier1OfferState();
        } else if (hasTier2Offer()) {
            currentState = new Tier2OfferState();
        } else {
            currentState = new NoOfferState();
        }
    }

    
    public boolean canApplyToJob(JobDescription job) {
        return currentState.canApplyToJob(this, job);
    }
    
    private boolean hasTier1Offer() {
        return placementResults.stream()
            .anyMatch(r -> r.getStatus() == OfferStatus.ACCEPTED &&
                r.getSalaryPackage().compareTo(new BigDecimal("12")) >= 0);
    }
    
    private boolean hasTier2Offer() {
        return placementResults.stream()
            .anyMatch(r -> r.getStatus() == OfferStatus.ACCEPTED &&
                r.getSalaryPackage().compareTo(new BigDecimal("6")) >= 0 &&
                r.getSalaryPackage().compareTo(new BigDecimal("12")) < 0);
    }
    
       public String getSrn() { return srn; }
    public void setSrn(String srn) { this.srn = srn; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public Integer getGraduationYear() { return graduationYear; }
    public void setGraduationYear(Integer graduationYear) { this.graduationYear = graduationYear; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public BigDecimal getGpa() { return gpa; }
    public void setGpa(BigDecimal gpa) { this.gpa = gpa; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public List<PlacementResult> getPlacementResults() { return placementResults; }
    public void setPlacementResults(List<PlacementResult> placementResults) {
        this.placementResults = placementResults;
        initializeState(); // trigger state update
    }


    public EligibilityState getCurrentState() { return currentState; }
    public void setCurrentState(EligibilityState currentState) { 
        this.currentState = currentState; 
    }
    // Getters, Setters, Constructors
}
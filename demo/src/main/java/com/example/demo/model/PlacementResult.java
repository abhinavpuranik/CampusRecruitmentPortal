// src/main/java/com/example/demo/model/PlacementResult.java
package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "placement_results")
public class PlacementResult {
    public enum OfferStatus { ACCEPTED, DECLINED, PENDING }
    public enum OfferType { FULL_TIME, INTERNSHIP }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_srn")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobDescription job;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "offer_type", nullable = false)
    private OfferType offerType;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal salaryPackage;
    
    @Enumerated(EnumType.STRING)
    private OfferStatus status = OfferStatus.PENDING;
    
    @CreationTimestamp
    @Column(name = "date_of_offer")
    private Timestamp dateOfOffer;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public JobDescription getJob() { return job; }
    public void setJob(JobDescription job) { this.job = job; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public OfferType getOfferType() { return offerType; }
    public void setOfferType(OfferType offerType) { this.offerType = offerType; }

    @Column(name = "package")
    public BigDecimal getSalaryPackage() { return salaryPackage; }
    public void setSalaryPackage(BigDecimal salaryPackage) { 
        this.salaryPackage = salaryPackage; 
    }

    public OfferStatus getStatus() { return status; }
    public void setStatus(OfferStatus status) { this.status = status; }

    public Timestamp getDateOfOffer() { return dateOfOffer; }
    public void setDateOfOffer(Timestamp dateOfOffer) { 
        this.dateOfOffer = dateOfOffer; 
    }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    // Getters, Setters, Constructors
}
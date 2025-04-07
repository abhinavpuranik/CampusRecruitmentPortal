package com.example.demo.state;

import com.example.demo.model.JobDescription;
import com.example.demo.model.Student;
import java.math.BigDecimal;

public class Tier2OfferState implements EligibilityState {
    @Override
    public boolean canApplyToJob(Student student, JobDescription job) {
        return job.getPackageOffered().compareTo(new BigDecimal("12")) >= 0;
    }
}
package com.example.demo.state;

import com.example.demo.model.JobDescription;
import com.example.demo.model.Student;

public class Tier1OfferState implements EligibilityState {
    @Override
    public boolean canApplyToJob(Student student, JobDescription job) {
        return false; // Cannot apply after accepting Tier1
    }
}
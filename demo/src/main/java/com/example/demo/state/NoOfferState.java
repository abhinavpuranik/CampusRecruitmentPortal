package com.example.demo.state;

import com.example.demo.model.JobDescription;
import com.example.demo.model.Student;

public class NoOfferState implements EligibilityState {
    @Override
    public boolean canApplyToJob(Student student, JobDescription job) {
        return true;
    }
}
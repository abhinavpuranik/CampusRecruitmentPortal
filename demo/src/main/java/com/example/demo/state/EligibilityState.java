// src/main/java/com/example/demo/state/EligibilityState.java
package com.example.demo.state;

import com.example.demo.model.JobDescription;
import com.example.demo.model.Student;

public interface EligibilityState {
    boolean canApplyToJob(Student student, JobDescription job);
}
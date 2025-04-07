// src/main/java/com/example/demo/factory/JobDescriptionFactory.java
package com.example.demo.factory;

import com.example.demo.model.JobDescription;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

public interface JobDescriptionFactory {
    JobDescription createJob(String title, String location, BigDecimal pkg, BigDecimal gpaCutoff);
}

@Component
class SWEJobFactory implements JobDescriptionFactory {
    @Override
    public JobDescription createJob(String title, String location, 
                                   BigDecimal pkg, BigDecimal gpaCutoff) {
        JobDescription job = new JobDescription();
        job.setType(JobDescription.JobType.SWE);
        job.setJobTitle(title);
        job.setLocation(location);
        job.setPackageOffered(pkg);
        job.setGpaCutoff(gpaCutoff);
        return job;
    }
}

@Component
class DataAnalyticsJobFactory implements JobDescriptionFactory {
    @Override
    public JobDescription createJob(String title, String location,
                                   BigDecimal pkg, BigDecimal gpaCutoff) {
        JobDescription job = new JobDescription();
        job.setType(JobDescription.JobType.DATA_ANALYTICS);
        job.setJobTitle(title);
        job.setLocation(location);
        job.setPackageOffered(pkg);
        job.setGpaCutoff(gpaCutoff);
        return job;
    }
}
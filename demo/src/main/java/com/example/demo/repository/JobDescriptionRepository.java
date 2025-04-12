package com.example.demo.repository;
import java.util.List;
import java.math.BigDecimal;
import com.example.demo.model.JobDescription;






import com.example.demo.model.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDescriptionRepository extends JpaRepository<JobDescription, Long> {
    List<JobDescription> findByGpaCutoffLessThan(BigDecimal gpa);

    List<JobDescription> findByGpaCutoffGreaterThanEqual(BigDecimal gpa);
    List<JobDescription> findByType(JobDescription.JobType type);



}
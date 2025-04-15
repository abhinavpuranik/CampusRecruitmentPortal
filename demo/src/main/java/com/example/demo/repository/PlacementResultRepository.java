package com.example.demo.repository;

import com.example.demo.model.PlacementResult;
import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlacementResultRepository extends JpaRepository<PlacementResult, Long> {
    List<PlacementResult> findByStudent(Student student);
}
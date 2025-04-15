package com.example.demo.repository;

import com.example.demo.model.PlacementResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementResultRepository extends JpaRepository<PlacementResult, Long> {
}
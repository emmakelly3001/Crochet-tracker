package com.example.crochet_tracker.repository;

import com.example.crochet_tracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Define any custom methods if required
}

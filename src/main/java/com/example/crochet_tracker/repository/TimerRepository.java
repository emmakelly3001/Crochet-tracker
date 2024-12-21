package com.example.crochet_tracker.repository;

import com.example.crochet_tracker.model.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
    //custom query to find the timer by searching for user and project
    List<Timer> findByUser_IdAndProject_Id(Long userId, Long projectId);
}

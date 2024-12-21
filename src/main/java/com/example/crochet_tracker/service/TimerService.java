package com.example.crochet_tracker.service;

import com.example.crochet_tracker.model.Timer;
import com.example.crochet_tracker.repository.TimerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimerService {

    private final TimerRepository timerRepository;

    @Autowired
    public TimerService(TimerRepository timerRepository) {
        this.timerRepository = timerRepository;
    }

    public Timer startTimer(Timer timer) {
        return timerRepository.save(timer);
    }

    public Timer stopTimer(Long timerId, Long elapsedTime) {
        Timer timer = timerRepository.findById(timerId).orElseThrow(() -> new RuntimeException("Timer not found"));
        timer.setElapsedTime(timer.getElapsedTime() + elapsedTime);
        return timerRepository.save(timer);
    }

    public List<Timer> getTimersForUserAndProject(Long userId, Long projectId) {
        return timerRepository.findByUser_IdAndProject_Id(userId, projectId);
    }

    // Other timer-related methods
}

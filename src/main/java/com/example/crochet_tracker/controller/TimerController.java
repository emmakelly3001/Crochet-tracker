package com.example.crochet_tracker.controller;

import com.example.crochet_tracker.model.Timer;
import com.example.crochet_tracker.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timers")
public class TimerController {

    private final TimerService timerService;

    @Autowired
    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }

    @PostMapping("/start")
    public Timer startTimer(@RequestBody Timer timer) {
        return timerService.startTimer(timer);
    }

    @PostMapping("/stop/{timerId}")
    public Timer stopTimer(@PathVariable Long timerId, @RequestParam Long elapsedTime) {
        return timerService.stopTimer(timerId, elapsedTime);
    }

    @GetMapping("/user/{userId}/project/{projectId}")
    public List<Timer> getTimersForUserAndProject(@PathVariable Long userId, @PathVariable Long projectId) {
        return timerService.getTimersForUserAndProject(userId, projectId);
    }

    // Other methods for managing timers
}

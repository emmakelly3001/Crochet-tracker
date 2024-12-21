package com.example.crochet_tracker.service;

import com.example.crochet_tracker.model.Project;
import com.example.crochet_tracker.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Method to create a project
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Method to retrieve all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}

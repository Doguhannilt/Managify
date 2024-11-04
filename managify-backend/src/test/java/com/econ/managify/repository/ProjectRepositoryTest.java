package com.econ.managify.repository;


import com.econ.managify.model.Project;
import com.econ.managify.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(ProjectRepositoryTest.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findOwnerAProject() throws Exception {
        // Arrange
        String email = "doguhan@gmail.com";

        // Act
        User selectedUser = userRepository.findByEmail(email);
        assertNotNull(selectedUser, "User should not be null");

        List<Project> projectOwner = projectRepository.findByOwner(selectedUser);

        // Assert
        assertNotNull(projectOwner, "Project list cannot be null");
        assertFalse(projectOwner.isEmpty(), "Project list should not be empty");

        for (Project project : projectOwner) {
            assertEquals(selectedUser.getId(), project.getOwner().getId(), "Project owner ID should match");
        }

        // Log
        logger.info("Size of Project {}",
                projectOwner.size());

        // Log 2
        projectOwner.forEach(project -> logger.info("Project: {}", project.getId()));

    }

    @Test
    public void findProjectById() throws Exception {
        // Arrange
        Long projectId = 1L;

        // Act
        Optional<Project> project = projectRepository.findById(projectId);

        // Assert
        assertTrue(project.isPresent(), "Project should be found");
        assertEquals(projectId, project.get().getId(), "Found project ID should match");
    }

    @Test
    public void findProjectByInvalidId() throws Exception {
        // Arrange
        Long invalidProjectId = 999L;

        // Act
        Optional<Project> project = projectRepository.findById(invalidProjectId);

        // Assert
        assertFalse(project.isPresent(), "Project should not be found");
    }

}

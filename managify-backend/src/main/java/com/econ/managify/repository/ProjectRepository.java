package com.econ.managify.repository;

import com.econ.managify.model.Project;
import com.econ.managify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User user);
    List<Project> findByNameContainingAndTeamContains(String partialName, User user);
    List<Project> findByTeamContains(User user);
}

package com.econ.managify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issues {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();

    @ManyToOne
    private User assigned;

    @JsonIgnore
    @ManyToOne
    private Project project;


    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments = new ArrayList<>();

    public Issues() {
    }

    public Issues(String title, String description, String status, Long projectID, String priority, LocalDate dueDate, List<String> tags, User assigned, Project project, List<Comments> comments) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.projectID = projectID;
        this.priority = priority;
        this.dueDate = dueDate;
        this.tags = tags;
        this.assigned = assigned;
        this.project = project;
        this.comments = comments;
    }

    public Issues(Long id, String title, String description, String status, Long projectID, String priority, LocalDate dueDate, List<String> tags, User assigned, Project project, List<Comments> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.projectID = projectID;
        this.priority = priority;
        this.dueDate = dueDate;
        this.tags = tags;
        this.assigned = assigned;
        this.project = project;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public User getAssigned() {
        return assigned;
    }

    public void setAssigned(User assigned) {
        this.assigned = assigned;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}

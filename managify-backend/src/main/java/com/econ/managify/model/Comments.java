package com.econ.managify.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private LocalDate createdDateTime;

    @ManyToOne
    private User user;

    @ManyToOne
    private Issues issues;

    public Comments(Long id, String content, LocalDate createdDateTime, User user, Issues issues) {
        this.id = id;
        this.content = content;
        this.createdDateTime = createdDateTime;
        this.user = user;
        this.issues = issues;
    }

    public Comments() {
    }

    public Issues getIssues() {
        return issues;
    }

    public void setIssues(Issues issues) {
        this.issues = issues;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDate createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

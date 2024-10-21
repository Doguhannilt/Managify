package com.econ.managify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "assigned", cascade = CascadeType.ALL)
    private List<Issues> assignedIssues = new ArrayList<>();

    private int projectSize;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", assignedIssues=" + assignedIssues +
                ", projectSize=" + projectSize +
                '}';
    }

    public User(String fullName, String email, String password, List<Issues> assignedIssues, int projectSize) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.assignedIssues = assignedIssues;
        this.projectSize = projectSize;
    }

    public User(Long id, String fullName, String email, String password, List<Issues> assignedIssues, int projectSize) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.assignedIssues = assignedIssues;
        this.projectSize = projectSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Issues> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(List<Issues> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }

    public int getProjectSize() {
        return projectSize;
    }

    public void setProjectSize(int projectSize) {
        this.projectSize = projectSize;
    }
}

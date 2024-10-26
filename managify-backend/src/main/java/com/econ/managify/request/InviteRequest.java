package com.econ.managify.request;

public class InviteRequest {
    private Long projectId;
    private String email;

    public InviteRequest() {
    }

    public InviteRequest(Long projectId, String email) {
        this.projectId = projectId;
        this.email = email;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

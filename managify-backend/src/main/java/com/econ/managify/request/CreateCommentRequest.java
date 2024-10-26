package com.econ.managify.request;

import java.time.LocalDate;

public class CreateCommentRequest {
  private Long issueId;
  private String content;


    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

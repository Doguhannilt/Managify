package com.econ.managify.interfaces;

import com.econ.managify.model.Issues;
import com.econ.managify.model.User;
import com.econ.managify.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issues getIssueById(Long issueId) throws  Exception;
    List<Issues> getIssueByProjectId(Long projectId) throws Exception;
    Issues createIssue (IssueRequest issue, User user) throws Exception;
    void deleteIssue(Long issueId, Long userId) throws Exception;
    Issues addUserToIssue(Long issueId, Long userId) throws Exception;
    Issues updateStatus(Long issueId, String status) throws Exception;
}


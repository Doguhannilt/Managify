package com.econ.managify.interfaces;


import com.econ.managify.model.Comments;

import java.util.List;

public interface CommentService {
    Comments createComment(Long issueId, Long userId, String comment) throws Exception;
    void deleteComment(Long commentId, Long userId) throws Exception;
    List<Comments> findCommentByIssue(Long issueId);
}

package com.econ.managify.service;

import com.econ.managify.interfaces.CommentService;
import com.econ.managify.model.Comments;
import com.econ.managify.model.Issues;
import com.econ.managify.model.User;
import com.econ.managify.repository.CommentRepository;
import com.econ.managify.repository.IssueRepository;
import com.econ.managify.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public CommentServiceImp(CommentRepository commentRepository, IssueRepository issueRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comments createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issues> issuesOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(issuesOptional.isEmpty()) {throw new Exception("Issue Not Found");}
        if(userOptional.isEmpty()) {throw new Exception("User Not Found");}
        Issues issues = issuesOptional.get();
        User user = userOptional.get();
        Comments comments = new Comments();
        comments.setIssues(issues);
        comments.setUser(user);
        comments.setCreatedDateTime(LocalDate.from(LocalDateTime.now()));
        comments.setContent(content);
        Comments savedComment = commentRepository.save(comments);
        issues.getComments().add(savedComment);
        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comments> commentsOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(commentsOptional.isEmpty()) {throw new Exception("Comment Not Found");}
        if(userOptional.isEmpty()) {throw new Exception("User Not Found");}

        Comments comments = commentsOptional.get();
        User user = userOptional.get();

        if(comments.getUser().equals(user)) {commentRepository.delete(comments);}
        else {throw new Exception("User does not have permission to delete this comment");}
    }

    @Override
    public List<Comments> findCommentByIssue(Long issueId) {
        return commentRepository.findByIssuesId(issueId);
    }
}

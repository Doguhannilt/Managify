package com.econ.managify.controller;

import com.econ.managify.interfaces.CommentService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.Comments;
import com.econ.managify.model.User;
import com.econ.managify.request.CreateCommentRequest;
import com.econ.managify.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Comments> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Comments createdComment = commentService.createComment(req.getIssueId(), user.getId(), req.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        ApiResponse apiResponse = new ApiResponse("Comment Deleted Successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comments>> getCommentsByIssueId(@PathVariable Long issueId) {
        List<Comments> comments = commentService.findCommentByIssue(issueId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }
}

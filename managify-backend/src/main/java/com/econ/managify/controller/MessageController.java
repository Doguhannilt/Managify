package com.econ.managify.controller;

import com.econ.managify.interfaces.MessageService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.Chat;
import com.econ.managify.model.Message;
import com.econ.managify.model.User;
import com.econ.managify.request.createMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final UserService userService;
    private final ProjectService projectService;
    private final MessageService messageService;
    public MessageController(UserService userService, ProjectService projectService, MessageService messageService) {
        this.userService = userService;
        this.projectService = projectService;
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody createMessageRequest request) throws Exception {
        User user = userService.findUserById(request.getSenderId());
        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if(chats==null) throw new Exception("Chats Not Found");
        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(),request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) throws Exception{
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }

}

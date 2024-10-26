package com.econ.managify.service;

import com.econ.managify.interfaces.MessageService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.Chat;
import com.econ.managify.model.Message;
import com.econ.managify.model.User;
import com.econ.managify.repository.MessageRepository;
import com.econ.managify.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MessageServicesImp implements MessageService {

    private final MessageService messageService;
    private final UserService userService;
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    public MessageServicesImp(MessageService messageService, UserService userService, ProjectService projectService, UserRepository userRepository, MessageRepository messageRepository) {
        this.messageService = messageService;
        this.userService = userService;
        this.projectService = projectService;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() ->  new Exception("User Not Found"));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setChat(chat);
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {

        Chat chat = projectService.getChatByProjectId(projectId);

        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}

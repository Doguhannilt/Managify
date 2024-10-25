package com.econ.managify.service;

import com.econ.managify.interfaces.ChatService;
import com.econ.managify.model.Chat;
import com.econ.managify.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImp implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImp(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}

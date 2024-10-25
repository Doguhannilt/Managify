package com.econ.managify.interfaces;

import com.econ.managify.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatService  {

    Chat createChat(Chat chat);
}

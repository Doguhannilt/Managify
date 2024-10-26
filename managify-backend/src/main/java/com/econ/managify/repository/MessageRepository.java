package com.econ.managify.repository;

import com.econ.managify.model.Chat;
import com.econ.managify.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    public List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}

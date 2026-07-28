package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.ChatHistory;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.repository.ChatHistoryRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.service.ChatHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatHistoryServiceImpl implements ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;
    private final UserRepository userRepository;

    public ChatHistoryServiceImpl(ChatHistoryRepository chatHistoryRepository,
                                   UserRepository userRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ChatHistory> getUserChatHistory(Integer userId) {
        return chatHistoryRepository.findByUserIdOrderByCreatedAtAsc(userId);
    }

    @Override
    public ChatHistory saveMessage(Integer userId, String sender, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " was not found."));
        ChatHistory chat = new ChatHistory();
        chat.setUser(user);
        chat.setSender(sender);
        chat.setMessage(message);
        return chatHistoryRepository.save(chat);
    }

    @Override
    @Transactional
    public void clearUserChat(Integer userId) {
        chatHistoryRepository.deleteByUserId(userId);
    }
}

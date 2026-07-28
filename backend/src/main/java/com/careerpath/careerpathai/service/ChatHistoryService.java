package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.ChatHistory;

import java.util.List;

public interface ChatHistoryService {
    List<ChatHistory> getUserChatHistory(Integer userId);
    ChatHistory saveMessage(Integer userId, String sender, String message);
    void clearUserChat(Integer userId);
}

package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.ChatMessageRequestDTO;
import com.careerpath.careerpathai.dto.ChatMessageResponseDTO;
import com.careerpath.careerpathai.entity.ChatHistory;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.service.ChatHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    public ChatHistoryController(ChatHistoryService chatHistoryService) {
        this.chatHistoryService = chatHistoryService;
    }

    private ChatMessageResponseDTO toDTO(ChatHistory c) {
        return new ChatMessageResponseDTO(c.getId(), c.getUser().getId(),
                c.getSender(), c.getMessage(), c.getCreatedAt());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatMessageResponseDTO>>> getChatHistory(
            @AuthenticationPrincipal User principal) {
        List<ChatMessageResponseDTO> list = chatHistoryService.getUserChatHistory(principal.getId())
                .stream().map(this::toDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Chat history retrieved successfully", list));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ChatMessageResponseDTO>> sendMessage(
            @Valid @RequestBody ChatMessageRequestDTO dto,
            @AuthenticationPrincipal User principal) {

        // Save the user's message
        ChatHistory userMsg = chatHistoryService.saveMessage(
                principal.getId(), "USER", dto.getMessage());

        // Placeholder AI response — replace with actual AI integration
        ChatHistory aiMsg = chatHistoryService.saveMessage(
                principal.getId(), "AI",
                "Thank you for your message. AI response integration coming soon.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Message sent successfully", toDTO(aiMsg)));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Object>> clearChat(
            @AuthenticationPrincipal User principal) {
        chatHistoryService.clearUserChat(principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Chat history cleared", null));
    }
}

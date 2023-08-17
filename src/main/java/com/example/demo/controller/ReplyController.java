package com.example.demo.controller;

import com.example.demo.dto.ReplyDto;
import com.example.demo.service.DupReplyService;
import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping(value = "/new")
    public String createReply(@RequestParam(value = "reply") String content,
                              @RequestParam Long boardId, Authentication authentication) {
        replyService.saveReply(boardId, content, authentication.getName());
        return "redirect:/board/detail/" + boardId;
    }

    @DeleteMapping(value = "/delete/{replyId}")
    public String deleteReply(@PathVariable Long replyId, Model model, Authentication authentication) {
        Long boardId = replyService.deleteReply(replyId);
        model.addAttribute("replies",replyService.getReplyList(boardId));
        model.addAttribute("userEmail",authentication.getName());
        return "/pages/boards/replyCard";
    }

    @PatchMapping(value = "/update/{replyId}")
    public String updateReply(@PathVariable Long replyId, @RequestBody String content,
                              Authentication authentication, Model model){
        Long boardId = replyService.updateReply(replyId, content);
        model.addAttribute("replies",replyService.getReplyList(boardId));
        model.addAttribute("userEmail", authentication.getName());
        return "pages/boards/replyCard";
    }
}

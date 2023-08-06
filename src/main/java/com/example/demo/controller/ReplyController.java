package com.example.demo.controller;

import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping(value = "/new")
    public String createReply(@RequestParam(value = "reply") String content,
                              @RequestParam Long boardId,
                              Authentication authentication) {
        replyService.saveReply(content, boardId, authentication.getName());
        return "redirect:/board/detail/" + boardId;
    }
}

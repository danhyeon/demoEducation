package com.example.demo.controller;

import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @DeleteMapping(value = "/delete/{replyId}")
    public ResponseEntity<Long> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return new ResponseEntity<Long>(replyId, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "/info/{replyId}")
    public String infoReply(@PathVariable Long replyId) {
        return replyService.getContent(replyId);
    }

    @PatchMapping(value = "/update/{replyId}")
    public String updateReply(@PathVariable Long replyId,
                              @RequestBody String content,
                              Model model) {
        Long boardId = replyService.updateReply(replyId, content);
        model.addAttribute("replies", replyService.getReplyList(boardId));
        return "pages/boards/replyCard";
    }
}

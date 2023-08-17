package com.example.demo.controller;

import com.example.demo.service.DupReplyService;
import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/dupReply")
public class DupReplyController {

    private final DupReplyService dupReplyService;
    private final ReplyService replyService;

    @PostMapping(value = "/new")
    public String createDupReply(@RequestParam Long replyId,
                                 @RequestParam String dupReply,
                                 Authentication authentication) {
        Long boardId =
                dupReplyService.saveDupReply(replyId, dupReply, authentication.getName());
        return "redirect:/board/detail/" + boardId;
    }

    @DeleteMapping(value = "/delete/{dupReplyId}")
    public String deleteDupReply(@PathVariable Long dupReplyId, Model model, Authentication authentication) {
        Long boardId = dupReplyService.deleteDupReply(dupReplyId);
        model.addAttribute("replies",replyService.getReplyList(boardId));
        model.addAttribute("userEmail",authentication.getName());
        return "/pages/boards/replyCard";
    }

    @PostMapping(value = "/update")
    public String updateDupReply(@RequestParam Long dupReplyId,
                                 @RequestParam String dupReply) {
        Long boardId = dupReplyService.updateDupReply(dupReplyId, dupReply);
        return "redirect:/board/detail/" + boardId;
    }
}

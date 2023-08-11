package com.example.demo.controller;

import com.example.demo.repository.ReplyRepository;
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

    private final ReplyService replyService;
    private final DupReplyService dupReplyService;

    @PostMapping(value = "/new")
    public String createDupReply(@RequestParam Long replyId,
                                 @RequestParam String dupReply,
                                 Authentication authentication) {
        dupReplyService.saveDupReply(replyId, dupReply, authentication.getName());
        Long boardId = replyService.getReplyDto(replyId).getBoardId();
        return "redirect:/board/detail/" + boardId;
    }

    @DeleteMapping(value = "/delete/{dupReplyId}")
    public ResponseEntity<Long> deleteDupReply(@PathVariable Long dupReplyId) {
        dupReplyService.deleteDupReply(dupReplyId);
        return new ResponseEntity<Long>(dupReplyId, HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public String updateDupReply(@RequestParam(value = "dupReplyId") Long dupReplyId,
                              @RequestParam(value = "dupReply") String content) {
        System.out.println(dupReplyId + " " + content);
       Long boardId = dupReplyService.updateDupReply(dupReplyId, content);
        return "redirect:/board/detail/" + boardId;
    }
}

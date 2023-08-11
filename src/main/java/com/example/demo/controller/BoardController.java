package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.ReplyDto;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping(value = "/info")
    public String boardInfo(Model model) {
        model.addAttribute("boards", boardService.getBoardList());
        return "/pages/boards/boardInfo";
    }

    @GetMapping(value = "/form")
    public String boardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "/pages/boards/boardForm";
    }

    @PostMapping(value = "/form")
    public String boardSave(@Valid BoardDto boardDto,
                            BindingResult bindingResult,
                            Authentication authentication,
                            Model model) {
        String email = authentication.getName();
        if(bindingResult.hasErrors()) {
            return "/pages/boards/boardForm";
        }
        boardService.saveBoard(boardDto, email);
        return "redirect:/board/info";
    }

    @GetMapping(value = "/detail/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        BoardDto boardDto = boardService.showDetail(boardId);
        if(boardDto.getMemberEmail().equals(userEmail)) {
            model.addAttribute("mine", true);
        }else {
            model.addAttribute("mine", false);
        }
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("replies", replyService.getReplyList(boardId));
        return "/pages/boards/boardDetail";
    }

    @PatchMapping(value = "/update")
    public ResponseEntity boardUpdate(@RequestBody BoardDto boardDto) {
        boardService.updateBoard(boardDto);
        return new ResponseEntity<Long>(boardDto.getId(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{boardId}")
    public ResponseEntity boardDelete(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }
}

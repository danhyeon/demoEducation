package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public String boardInfo(@RequestParam(value = "page", required = false, defaultValue = "0") String page,
                            Model model) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 5, Sort.by("regTime").descending());
        model.addAttribute("page", boardService.getBoardList(pageable));
        return "pages/boards/boardInfo";
    }

    @GetMapping(value = "/info/{page}")
    public String boardPages(@PathVariable Integer page, Model model) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("regTime").descending());
        model.addAttribute("page", boardService.getBoardList(pageable));
        return "pages/cards/board/pageCard";
    }

    @GetMapping(value = "/form")
    public String boardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "pages/boards/boardForm";
    }

    @PostMapping(value = "/form")
    public String boardSave(@Valid BoardDto boardDto, BindingResult bindingResult,
                            Authentication authentication) {
        if(bindingResult.hasErrors()) {return "/pages/boards/boardForm";}
        boardService.saveBoard(boardDto, authentication.getName());
        return "redirect:/board/info";
    }

    @GetMapping(value = "/detail")
    public String boardDetail(@RequestParam Long boardId,
                              @RequestParam(required = false, defaultValue = "0") Long page,
                              Model model, Authentication authentication) {
        model.addAttribute("pageNum", page);
        model.addAttribute("userEmail", authentication.getName());
        model.addAttribute("boardDto", boardService.showDetail(boardId));
        model.addAttribute("replies", replyService.getReplyList(boardId));
        return "pages/boards/boardDetail";
    }

    @PatchMapping(value = "/update")
    public String boardUpdate(@RequestBody BoardDto boardDto, Model model) {
        boardService.updateBoard(boardDto);
        model.addAttribute("boardDto", boardService.showDetail(boardDto.getId()));
        return "pages/cards/board/detailCard";
    }

    @DeleteMapping(value = "/delete/{boardId}")
    public ResponseEntity boardDelete(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }
}

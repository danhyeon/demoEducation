package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> Stashed changes

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

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
    public String boardSave(@Valid BoardDto boardDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "/pages/boards/boardForm";
        }
        boardService.saveBoard(boardDto);
        return "redirect:/board/info";
    }
<<<<<<< Updated upstream
=======

    @GetMapping(value = "/detail/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model) {
        BoardDto boardDto = boardService.showDetail(boardId);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("bid", boardDto.getId());
        return "/pages/boards/boardDetail";
    }

    @DeleteMapping(value = "/delete/{boardId}")
    public ResponseEntity boardDelete(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }
>>>>>>> Stashed changes
}

package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.service.BoardService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String boardSave(@Valid BoardDto boardDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/pages/boards/boardForm";
        }
        boardService.saveBoard(boardDto);
        return "redirect:/board/info";
    }

    @GetMapping(value = "/detail/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model) {
        model.addAttribute("boardDto", boardService.showDetail(boardId));
        return "/pages/boards/boardDetail";
    }
}

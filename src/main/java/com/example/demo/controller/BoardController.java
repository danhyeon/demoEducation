package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.MemberDto;
import com.example.demo.dto.ReplyDto;
import com.example.demo.service.BoardService;
import com.example.demo.service.DupReplyService;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final ReplyService replyService;
    private final DupReplyService dupReplyService;

    @GetMapping(value = "/info")
    public String boardInfo(@RequestParam(value = "page", required = false, defaultValue = "0") String page, Model model) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page),5);
        model.addAttribute("page", boardService.getBoardList(pageable));
        return "/pages/boards/boardInfo";
    }

    @PostMapping(value = "/info/{page}")
    public String boardPages(@PathVariable Integer page, Model model) {
        Pageable pageable = PageRequest.of(page,5);
        model.addAttribute("page", boardService.getBoardList(pageable));
        return "/pages/boards/boardPageCard";
    }

    @GetMapping(value = "/form")
    public String boardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "/pages/boards/boardForm";
    }

    @PostMapping(value = "/form")
    public String boardSave(@Valid BoardDto boardDto, BindingResult bindingResult, Model model, Authentication authentication) {
        if(bindingResult.hasErrors()) {
            return "/pages/boards/boardForm";
        }
        boardService.saveBoard(boardDto, authentication.getName());
        return "redirect:/board/info";
    }

    @GetMapping(value = "/detail/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model, Authentication authentication) {
        BoardDto boardDto = boardService.showDetail(boardId);
        String writerEmail = boardDto.getMemberEmail();
        List<ReplyDto> replyDtoList = replyService.getReplyList(boardId);
        for(ReplyDto r : replyDtoList) {
            r.setDupReplyDtoList(dupReplyService.getDupReplys(r.getId()));
        }
        model.addAttribute("userEmail", authentication.getName());
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("bid", boardDto.getId());
        model.addAttribute("updateTime", boardDto.getUpdateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        model.addAttribute("replies", replyDtoList);
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

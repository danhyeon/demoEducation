package com.example.demo.controller;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
    @GetMapping(value = "/info")
    public String boardInfo() {
        return "/boards/boardInfo";
    }
}

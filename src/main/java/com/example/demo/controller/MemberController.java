package com.example.demo.controller;

import com.example.demo.dto.MemberFormDto;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/login")
    public String login() {return "pages/member/memberLoginForm";}

    @GetMapping(value = "/new")
    public String create(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "pages/member/memberCreateForm";
    }

    @PostMapping(value = "/new")
    public String saveMember(MemberFormDto memberFormDto, Model model) {
        try{
            memberService.saveMember(memberFormDto);
        }catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "pages/member/memberCreateForm";
        }
        return "redirect:/member/login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model, @RequestParam String error, @RequestParam String exception) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "pages/member/memberLoginForm";
    }
}

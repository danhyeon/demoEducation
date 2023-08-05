package com.example.demo.controller;

import com.example.demo.dto.MemberFormDto;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping(value = "/login")
    public String login() {return "pages/member/memberLoginForm";}

    @GetMapping(value = "/new")
    public String newMember(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "pages/member/memberCreateForm";
    }

    @PostMapping(value = "/new")
    public String createMember(MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "pages/member/MemberCreateForm";
        }
        try {
            memberService.saveMember(memberFormDto);
        } catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "pages/member/memberCreateForm";
        }
        return "redirect:/member/login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(@RequestParam String error, @RequestParam String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "pages/member/memberLoginForm";
    }
}

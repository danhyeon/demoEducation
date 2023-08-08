package com.example.demo.entity;

import com.example.demo.constant.Role;
import com.example.demo.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@ToString
@NoArgsConstructor
public class Member {

    private String name;

    @Id
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .password(passwordEncoder.encode(memberFormDto.getPassword()))
                .role(Role.USER)
                .build();
    }

    @Builder
    public Member(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getRoleKey() {return this.role.getKey();}
}

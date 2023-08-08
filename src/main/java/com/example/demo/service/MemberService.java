package com.example.demo.service;

import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(MemberFormDto memberFormDto) throws IllegalStateException {
        validateMember(memberFormDto);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberRepository.save(member);
    }

    private void validateMember(MemberFormDto memberFormDto) throws IllegalStateException {
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());
        if(member != null) {
            throw new IllegalStateException("이미 사용 중인 이메일 주소 입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException(email);
        }

        return new CustomUserDetails(member);
    }
}

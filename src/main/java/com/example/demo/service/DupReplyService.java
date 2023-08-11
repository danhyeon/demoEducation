package com.example.demo.service;

import com.example.demo.dto.DupReplyDto;
import com.example.demo.entity.DupReply;
import com.example.demo.entity.Member;
import com.example.demo.entity.Reply;
import com.example.demo.repository.DupReplyRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DupReplyService {

    private final DupReplyRepository dupReplyRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    public List<DupReplyDto> getDupReplys(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(EntityExistsException::new);
        List<DupReplyDto> result = new ArrayList<>();
        for(DupReply dupReply : dupReplyRepository.findByReply(reply)){
            result.add(DupReplyDto.of(dupReply));
        }
        return result;
    }

    public void saveDupReply(Long replyId, String content, String email) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(EntityExistsException::new);
        Member member = memberRepository.findByEmail(email);
        DupReply dupReply = DupReply.builder()
                .reply(reply)
                .content(content)
                .writer(member.getName())
                .member(member)
                .build();
        dupReplyRepository.save(dupReply);
    }

    public void deleteDupReply(Long dupReplyId) {
        dupReplyRepository.deleteById(dupReplyId);
    }

    public Long updateDupReply(Long dupReplyId, String content) {
        DupReply dupReply = dupReplyRepository.findById(dupReplyId)
                .orElseThrow(EntityExistsException::new);
        dupReply.updateDupReply(content);
        return dupReply.getReply().getBoard().getId();
    }
}

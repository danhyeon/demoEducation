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

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final DupReplyRepository dupReplyRepository;

    public Long saveDupReply(Long replyId, String content, String email) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(EntityExistsException::new);
        Member member = memberRepository.findByEmail(email);
        DupReply dupReply = DupReply.builder()
                .reply(reply)
                .member(member)
                .writer(member.getName())
                .content(content)
                .build();
        dupReplyRepository.save(dupReply);
        return reply.getBoard().getId();
    }

    public List<DupReplyDto> getDupReplys(Reply reply) {
        List<DupReplyDto> result = new ArrayList<>();
        for(DupReply dupReply : dupReplyRepository.findByReply(reply)) {
            result.add(DupReplyDto.of(dupReply));
        }
        return result;
    }

    public Long deleteDupReply(Long dupReplyId) {
        DupReply dupReply = dupReplyRepository.findById(dupReplyId)
                .orElseThrow(EntityExistsException::new);
        dupReplyRepository.delete(dupReply);
        return dupReply.getReply().getBoard().getId();
    }

    public Long updateDupReply(Long dupReplyId, String content) {
        DupReply dupReply = dupReplyRepository.findById(dupReplyId)
                .orElseThrow(EntityExistsException::new);
        dupReply.updateDupReply(content);
        return dupReply.getReply().getBoard().getId();
    }
}

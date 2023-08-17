package com.example.demo.service;

import com.example.demo.dto.ReplyDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.DupReply;
import com.example.demo.entity.Member;
import com.example.demo.entity.Reply;
import com.example.demo.repository.BoardRepository;
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
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final DupReplyService dupReplyService;

    public List<ReplyDto> getReplyList(Long boardId) {
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(EntityExistsException::new);
        List<ReplyDto> replyDtos = new ArrayList<>();
        for(Reply reply : replyRepository.findByBoard(board)) {
            ReplyDto replyDto = ReplyDto.of(reply);
            replyDto.setDupReplyDtoList(dupReplyService.getDupReplys(reply));
            replyDtos.add(replyDto);
        }
        return replyDtos;
    }

    public void saveReply(Long boardId, String content, String email) {
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(EntityExistsException::new);
        Member member = memberRepository.findByEmail(email);
        Reply reply = Reply.builder()
                .content(content)
                .member(member)
                .board(board)
                .writer(member.getName())
                .build();
        replyRepository.save(reply);
    }

    public Long deleteReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(EntityExistsException::new);
        replyRepository.delete(reply);
        return reply.getBoard().getId();
    }

    public String getContent(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(EntityExistsException::new)
                .getContent();
    }

    public Long updateReply(Long replyId, String content) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(EntityExistsException::new);
        reply.updateReply(content);
        return reply.getBoard().getId();
    }

}

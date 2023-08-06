package com.example.demo.service;

import com.example.demo.dto.ReplyDto;
import com.example.demo.entity.Board;
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

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    public void saveReply(String content, Long boardId, String memberEmail) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityExistsException::new);
        Member member = memberRepository.findByEmail(memberEmail);
        Reply reply = Reply.builder()
                .content(content)
                .member(member)
                .board(board)
                .writer(member.getName())
                .build();
        replyRepository.save(reply);
    }

    public List<ReplyDto> getReplyList(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityExistsException::new);
        List<Reply> replies = replyRepository.findByBoard(board);
        List<ReplyDto> replyDtos = new ArrayList<>();
        for(Reply reply : replies) {
            replyDtos.add(ReplyDto.of(reply));
        }
        return replyDtos;
    }
}

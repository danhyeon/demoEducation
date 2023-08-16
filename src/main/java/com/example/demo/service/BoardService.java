package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Board saveBoard(BoardDto boardDto, String email) {
        Member member = memberRepository.findByEmail(email);
        boardDto.setWriter(member.getName());
        return boardRepository.save(Board.createBoard(boardDto, member));
    }

    public Page<Board> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public BoardDto showDetail(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityExistsException::new);
        return BoardDto.of(board);
    }

    public void updateBoard(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(EntityExistsException::new);
        board.updateBoard(boardDto);
    }

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(EntityExistsException::new);
        boardRepository.delete(board);
    }

}

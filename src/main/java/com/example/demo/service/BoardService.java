package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board saveBoard(BoardDto boardDto) {
        return boardRepository.save(Board.createBoard(boardDto));
    }

    public List<BoardDto> getBoardList() {
        List<BoardDto> boardDtos = new ArrayList<>();

        for(Board board : boardRepository.findAll()) {
            boardDtos.add(BoardDto.of(board));
        }

//        List<Board> boards = boardRepository.findAll();
//        for(Board board : boards) {
//            boardDtos.add(BoardDto.of(board));
//        }

//        List<Board> boards = boardRepository.findAll();
//        for(int i=0; i<boards.size(); i++) {
//            boardDtos.add(BoardDto.of(boards.get(i)));
//        }

        return boardDtos;
    }

}

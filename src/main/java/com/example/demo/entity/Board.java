package com.example.demo.entity;

import com.example.demo.dto.BoardDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board")
@Data
@ToString
@NoArgsConstructor
public class Board {
    @Id
    @Column(name = "Board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Builder
    Board(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }

    public static Board createBoard(BoardDto boardDto) {
        return Board.builder()
                .title(boardDto.getTitle())
                .writer(boardDto.getWriter())
                .build();
    }
}

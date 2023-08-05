package com.example.demo.entity;

import com.example.demo.auditing.BaseEntity;
import com.example.demo.auditing.BaseTimeEntity;
import com.example.demo.dto.BoardDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board extends BaseTimeEntity {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email", unique = true)
    private Member member;

    @Builder
    Board(String title, String content, String writer, Member member) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.member = member;
    }

    public static Board createBoard(BoardDto boardDto, Member member) {
        return Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .writer(boardDto.getWriter())
                .member(member)
                .build();
    }

    public void updateBoard(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }
}

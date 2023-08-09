package com.example.demo.entity;

import com.example.demo.auditing.BaseEntity;
import com.example.demo.dto.ReplyDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Reply extends BaseEntity {
    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_email")
    private Member member;

    @Builder
    Reply(String content, String writer, Board board, Member member) {
        this.content = content;
        this.writer = writer;
        this.board = board;
        this.member = member;
    }

    public static Reply createReply(ReplyDto replyDto, Member member, Board board) {
        return Reply.builder()
                .content(replyDto.getContent())
                .writer(replyDto.getWriter())
                .member(member)
                .board(board)
                .build();
    }
}

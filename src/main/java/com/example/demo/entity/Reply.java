package com.example.demo.entity;

import com.example.demo.auditing.BaseTimeEntity;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.ReplyDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Reply extends BaseTimeEntity {
    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private String writer;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email")
    private Member member;

    @Builder
    Reply(String content, String writer, Member member, Board board) {
        this.content = content;
        this.writer = writer;
        this.member = member;
        this.board = board;
    }

    public static Reply createReply(ReplyDto replyDto, Member member, Board board) {
        return Reply.builder()
                .content(replyDto.getContent())
                .writer(replyDto.getWriter())
                .member(member)
                .board(board)
                .build();
    }

    public void updateReply(String content) {
        this.content = content;
    }
}

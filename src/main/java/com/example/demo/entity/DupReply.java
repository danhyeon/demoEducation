package com.example.demo.entity;

import com.example.demo.auditing.BaseTimeEntity;
import com.example.demo.dto.DupReplyDto;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "dup_reply")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DupReply extends BaseTimeEntity {
    @Id
    @Column(name = "dup_reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reply reply;

    @Column(nullable = false)
    private String content;

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email")
    private Member member;

    @Builder
    DupReply(Reply reply, String content, String writer, Member member) {
        this.reply = reply;
        this.content = content;
        this.writer = writer;
        this.member = member;
    }

    public static DupReply createDupReply(DupReplyDto dupReplyDto, Reply reply, Member member) {
        return DupReply.builder()
                .reply(reply)
                .content(dupReplyDto.getContent())
                .writer(dupReplyDto.getWriter())
                .member(member)
                .build();
    }

    public void updateDupReply(String content) {
        this.content = content;
    }

}

package com.example.demo.entity;

import com.example.demo.auditing.BaseEntity;
import com.example.demo.dto.DupReplyDto;
import com.example.demo.dto.ReplyDto;
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
public class DupReply extends BaseEntity {
    @Id
    @Column(name = "dup_reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "member_email")
    private Member member;

    @Builder
    DupReply(String content, String writer, Reply reply, Member member) {
        this.content = content;
        this.writer = writer;
        this.reply = reply;
        this.member = member;
    }

    public static DupReply createDupReply(DupReplyDto dupreplyDto, Member member, Reply reply) {
        return DupReply.builder()
                .content(dupreplyDto.getContent())
                .writer(dupreplyDto.getWriter())
                .member(member)
                .reply(reply)
                .build();
    }

    public void updateDupReply(String content) {
        this.content = content;
    }
}

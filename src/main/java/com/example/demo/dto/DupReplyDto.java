package com.example.demo.dto;

import com.example.demo.entity.Board;
import com.example.demo.entity.DupReply;
import com.example.demo.entity.Reply;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
//@NoArgsConstructor
@RequiredArgsConstructor
public class DupReplyDto {
    private Long id;
    private Long replyId;
    private String content;
    private String writer;
    private String memberEmail;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private static ModelMapper modelmapper = new ModelMapper();

    public static DupReplyDto of(DupReply dupReply) {
        return modelmapper.map(dupReply, DupReplyDto.class);
    }
}

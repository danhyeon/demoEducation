package com.example.demo.dto;

import com.example.demo.entity.Board;
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
public class ReplyDto {
    private Long id;
    private String content;
    private String writer;
    private String memberEmail;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private static ModelMapper modelmapper = new ModelMapper();

    public static ReplyDto of(Reply reply) {
        return modelmapper.map(reply, ReplyDto.class);
    }
}

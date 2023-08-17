package com.example.demo.dto;

import com.example.demo.entity.DupReply;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;

@Getter
@Setter
public class DupReplyDto {
    private Long id;
    private Long replyId;
    private String content;
    private String writer;
    private String memberEmail;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public static DupReplyDto of(DupReply dupReply) {
        return modelMapper.map(dupReply, DupReplyDto.class);
    }
}

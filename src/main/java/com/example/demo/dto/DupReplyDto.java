package com.example.demo.dto;

import com.example.demo.entity.DupReply;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class DupReplyDto {
    private Long id;
    private Long replyId;
    private String content;
    private String writer;
    private String memberEmail;
    private String regTime;
    private String updateTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public static DupReplyDto of(DupReply dupReply) {
        DupReplyDto dupReplyDto = modelMapper.map(dupReply, DupReplyDto.class);
        dupReplyDto.setRegTime(dupReply.getRegTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
        dupReplyDto.setUpdateTime(dupReply.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
        return dupReplyDto;
    }
}

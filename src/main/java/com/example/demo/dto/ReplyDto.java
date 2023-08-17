package com.example.demo.dto;

import com.example.demo.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyDto {
    private Long id;
    private String content;
    private String writer;
    private String memberEmail;
    private String regTime;
    private String updateTime;
    private List<DupReplyDto> dupReplyDtoList;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ReplyDto of(Reply reply) {
        ReplyDto replyDto = modelMapper.map(reply, ReplyDto.class);
        replyDto.setRegTime(reply.getRegTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
        replyDto.setUpdateTime(reply.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
        return replyDto;
    }
}

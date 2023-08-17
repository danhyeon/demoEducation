package com.example.demo.dto;

import com.example.demo.entity.Board;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
//@NoArgsConstructor
@RequiredArgsConstructor
public class BoardDto {
    private Long id;
    @NotBlank(message = "제목은 필수 입력입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;
    private String writer;
    private String memberEmail;
    private String regTime;
    private String updateTime;

    private static ModelMapper modelmapper = new ModelMapper();

    public static BoardDto of(Board board) {
        BoardDto boardDto = modelmapper.map(board, BoardDto.class);
        boardDto.setRegTime(board.getRegTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        boardDto.setUpdateTime(board.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
        return boardDto;
    }

}

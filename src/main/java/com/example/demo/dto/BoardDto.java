package com.example.demo.dto;

import com.example.demo.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    @NotBlank(message = "제목은 필수 입력입니다")
    private String title;
    @NotBlank(message = "내용은 필수 입력입니다")
    private String content;
    private String writer;

    private static ModelMapper modelmapper = new ModelMapper();

    public static BoardDto of(Board board) {
        return modelmapper.map(board, BoardDto.class);
    }

}

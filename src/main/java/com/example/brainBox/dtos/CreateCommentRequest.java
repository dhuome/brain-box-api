package com.example.brainBox.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.example.brainBox.common.constants.constants.Defaults.MAX_COMMENT_LENGTH;
import static com.example.brainBox.common.constants.constants.ErrorMessages.BLANK_FILED;
import static com.example.brainBox.common.constants.constants.ErrorMessages.MIN_MAX_COMMENT_LENGTH;

@Data
public class CreateCommentRequest {
    @NotBlank(message = BLANK_FILED)
    @Size(max = MAX_COMMENT_LENGTH, message = MIN_MAX_COMMENT_LENGTH)
    private String comment;
}

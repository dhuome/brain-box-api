package com.example.brainBox.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.example.brainBox.common.constants.constants.Defaults.*;
import static com.example.brainBox.common.constants.constants.ErrorMessages.*;

@Data
public class CreateArticleRequest {
    @NotBlank(message = BLANK_FILED)
    @Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH, message = MIN_MAX_TITLE_LENGTH)
    private String title;

    @NotBlank(message = BLANK_FILED)
    @Size(min = MIN_BODY_LENGTH, max = MAX_BODY_LENGTH, message = MIN_MAX_BODY_LENGTH)
    private String body;

    private String imgUrl;
}

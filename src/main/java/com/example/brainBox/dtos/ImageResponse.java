package com.example.brainBox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ImageResponse {
    private String base64;
    private String contentType;
    private Long size;
    private ZonedDateTime lastModified;
}

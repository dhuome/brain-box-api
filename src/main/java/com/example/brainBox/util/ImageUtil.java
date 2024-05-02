package com.example.brainBox.util;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ImageUtil {
    public static String convertImageToBase64(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);
    }
}


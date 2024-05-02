package com.example.brainBox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> results;
    private int currentPage;
    private int perPage;
    private long totalCount;
    private int pagesCount;

    public static <T> PaginatedResponse<T> mapToPaginatedResponse(List<T> data, Page<?> page) {
        return new PaginatedResponse<>(data, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}

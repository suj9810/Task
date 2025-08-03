package com.example.task.common.response;

import com.example.task.common.code.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponses<T> {
    private String status;
    private String message;
    private Result<T> result;

    @Getter
    @Builder
    @JsonInclude(Include.NON_NULL)
    public static class Result<T> {
        private Long totalElements;
        private Integer totalPages;
        private Boolean hasNextPage;
        private Boolean hasPreviousPage;
        @Builder.Default
        private List<T> content = new ArrayList<>();
    }

    public static <T> CommonResponses<T> of(ResponseCode responseCode, Page<T> page) {
        return CommonResponses.<T>builder()
                .status(responseCode.getCode())
                .message(responseCode.getMessage())
                .result(Result.<T>builder()
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .hasNextPage(page.hasNext())
                        .hasPreviousPage(page.hasPrevious())
                        .content(page.getContent())
                        .build())
                .build();
    }

    public static <T> CommonResponses<T> of(String status, String message, List<T> list) {
        return CommonResponses.<T>builder()
                .status(status)
                .message(message)
                .result(Result.<T>builder()
                        .content(list)
                        .build())
                .build();
    }
}

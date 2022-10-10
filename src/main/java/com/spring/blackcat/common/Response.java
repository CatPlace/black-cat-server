package com.spring.blackcat.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    private String status;

    private Object data;

    @Nullable
    private String error;

    private int code;
}

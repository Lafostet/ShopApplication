package com.myronenko.shop.application.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ErrorResponseDto {

    private String message;
    private String errorCode;
    private LocalDateTime timestamp;

}
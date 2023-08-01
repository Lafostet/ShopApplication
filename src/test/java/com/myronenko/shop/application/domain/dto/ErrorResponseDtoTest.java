package com.myronenko.shop.application.domain.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ErrorResponseDtoTest {

    @Test
    void constructor_WhenCalledWithValues_ThenCreateInstanceWithFieldsProvided() {
        LocalDateTime timestamp = LocalDateTime.of(2023, 7, 29, 10, 10);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("Error message", "404", timestamp);

        assertThat(errorResponseDto.getMessage()).isEqualTo("Error message");
        assertThat(errorResponseDto.getErrorCode()).isEqualTo("404");
        assertThat(errorResponseDto.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void constructor_WhenCalledWithReflection_ThenInstanceCreated() {
        assertThatCode(() -> ErrorResponseDto.class.getDeclaredConstructor().newInstance())
                .doesNotThrowAnyException();
    }

}

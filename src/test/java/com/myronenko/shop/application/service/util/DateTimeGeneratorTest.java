package com.myronenko.shop.application.service.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeGeneratorTest {

    @Test
    void generateNow_WhenCalled_ThenCorrectDateIsReturned() {
        DateTimeGenerator dateTimeGenerator = new DateTimeGenerator();
        LocalDateTime expected = LocalDateTime.now();
        LocalDateTime actual = dateTimeGenerator.generateNow();

        assertThat(actual).isEqualToIgnoringNanos(expected);
    }

}

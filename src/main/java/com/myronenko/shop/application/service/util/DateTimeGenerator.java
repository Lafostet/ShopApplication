package com.myronenko.shop.application.service.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeGenerator {

    public LocalDateTime generateNow(){
        return LocalDateTime.now();
    }

}

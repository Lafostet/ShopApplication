package com.myronenko.shop.application.exception;

import java.text.MessageFormat;

public class DataAlreadyExistsException extends RuntimeException {

    public DataAlreadyExistsException(String pattern, Object... args){
        super(MessageFormat.format(pattern,args));
    }

}

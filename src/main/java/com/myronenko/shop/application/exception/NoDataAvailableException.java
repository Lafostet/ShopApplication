package com.myronenko.shop.application.exception;

import java.text.MessageFormat;

public class NoDataAvailableException extends RuntimeException{

    public NoDataAvailableException(String pattern, Object... args){
        super(MessageFormat.format(pattern,args));
    }

}

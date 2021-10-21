package com.cloudflare.hellourld.exception;

public class NoOriginalURLException extends Exception{
    public NoOriginalURLException(String message){
        super(message);
    }
}

package com.example.redditclonespringboot.exception;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exmsg) {
        super(exmsg);
    }
}

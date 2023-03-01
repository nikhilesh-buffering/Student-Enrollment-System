package com.example.redditclonespringboot.exception;

public class SubRedditNotFoundException extends RuntimeException {
    public SubRedditNotFoundException(String s)  {
        super(s);
    }
}

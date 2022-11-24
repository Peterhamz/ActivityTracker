package com.example.ActivityTracker.Exception;

public class CustomAppException extends RuntimeException{
    public CustomAppException(String message){
        super (message);
    }
}

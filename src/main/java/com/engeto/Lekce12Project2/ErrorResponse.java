package com.engeto.Lekce12Project2;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;
    private LocalDateTime timeStamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
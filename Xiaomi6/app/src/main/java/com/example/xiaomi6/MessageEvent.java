package com.example.xiaomi6;

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.example.xiaomi6;

public class MessageEvent {
    private int position;
    private boolean like;

    public MessageEvent(int position, boolean like) {
        this.position = position;
        this.like = like;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public MessageEvent() {

    }
}

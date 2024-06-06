package com.example.xiaomi6;

public class HomeItem {
    private String title;
    private int imageResource;

    private boolean like;

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public HomeItem(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
        this.like = false;
    }

    public HomeItem(String title, int imageResource, boolean like) {
        this.title = title;
        this.imageResource = imageResource;
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}

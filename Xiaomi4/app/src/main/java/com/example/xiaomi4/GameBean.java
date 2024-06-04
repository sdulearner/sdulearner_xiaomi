package com.example.xiaomi4;


public class GameBean {
    private int gameIcon;
    private String gameName;
    private String gameStatus;

    public GameBean(String addItem, int icon1, String added) {
        this.gameName = addItem;
        this.gameIcon = icon1;
        this.gameStatus = added;
    }

    public GameBean() {

    }

    public int getGameIcon() {
        return gameIcon;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameIcon(int gameIcon) {
        this.gameIcon = gameIcon;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}

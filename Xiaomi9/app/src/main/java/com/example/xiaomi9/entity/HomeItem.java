package com.example.xiaomi9.entity;

public class HomeItem {
    // 列表页显示游戏图标、名称、评分、简介、安装按钮
    private String icon;
    private String gameName;
    private float score;
    private String brief;
    private String apkUrl;

    public HomeItem(String icon, String gameName, float score, String brief, String apkUrl) {
        this.icon = icon;
        this.gameName = gameName;
        this.score = score;
        this.brief = brief;
        this.apkUrl = apkUrl;
    }



    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

}

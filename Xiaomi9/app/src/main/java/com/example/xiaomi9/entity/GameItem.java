package com.example.xiaomi9.entity;

public class GameItem {
    @Override
    public String toString() {
        return "GameItem{" +
                "gameId=" + id +
                ", gameName='" + gameName + '\'' +
                ", icon='" + icon + '\'' +
                ", introduction='" + introduction + '\'' +
                ", brief='" + brief + '\'' +
                ", versionName='" + versionName + '\'' +
                ", apkUrl='" + apkUrl + '\'' +
                ", tags='" + tags + '\'' +
                ", score=" + score +
                ", playNumFormat='" + playNumFormat + '\'' +
                ", createTime='" + createTime + '\'' +
                "}\n";
    }

    private int id;
    private String gameName;
    private String icon;
    private String introduction;
    private String brief;
    private String versionName;
    private String apkUrl;
    private String tags;
    private float score;
    private String playNumFormat;
    private String createTime;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getPlayNumFormat() {
        return playNumFormat;
    }

    public void setPlayNumFormat(String playNumFormat) {
        this.playNumFormat = playNumFormat;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
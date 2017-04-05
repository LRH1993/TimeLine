package com.lvr.timeline;

/**
 * Created by zq on 2016/6/16.
 */
public class TimeInfo {
    //年月日信息
    private String ymD;
    //标题
    private String title;
    //内容
    private String content;
    //小时信息
    private String hour;
    //生成时间信息
    private String addTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYmD() {
        return ymD;
    }

    public void setYmD(String ymD) {
        this.ymD = ymD;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}

package com.lvr.timeline.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class TimeInfo implements Parcelable {
    //年月日信息
    private String ymD;
    //标题
    private String title;
    //内容
    private String content;
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

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "ymD='" + ymD + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ymD);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.addTime);
    }

    public TimeInfo() {
    }

    protected TimeInfo(Parcel in) {
        this.ymD = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.addTime = in.readString();
    }

    public static final Parcelable.Creator<TimeInfo> CREATOR = new Parcelable.Creator<TimeInfo>() {
        @Override
        public TimeInfo createFromParcel(Parcel source) {
            return new TimeInfo(source);
        }

        @Override
        public TimeInfo[] newArray(int size) {
            return new TimeInfo[size];
        }
    };
}

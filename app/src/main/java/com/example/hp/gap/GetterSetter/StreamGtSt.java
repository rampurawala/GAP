package com.example.hp.gap.GetterSetter;

public class StreamGtSt {
    int image;
    String name,date,msg;

    public StreamGtSt(int image, String name, String date, String msg) {
        this.image = image;
        this.name = name;
        this.date = date;
        this.msg = msg;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

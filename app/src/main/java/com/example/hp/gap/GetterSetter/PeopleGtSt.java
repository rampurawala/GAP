package com.example.hp.gap.GetterSetter;

public class PeopleGtSt {
String name,event;
Integer img;

    public PeopleGtSt(String name, String event, Integer img) {
        this.name = name;
        this.event = event;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}


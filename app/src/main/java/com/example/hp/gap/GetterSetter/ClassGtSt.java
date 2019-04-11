package com.example.hp.gap.GetterSetter;

public class ClassGtSt {
    int image;
    String name,date;

    public ClassGtSt(int image, String name, String date) {
        this.image = image;
        this.name = name;
        this.date = date;
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
}

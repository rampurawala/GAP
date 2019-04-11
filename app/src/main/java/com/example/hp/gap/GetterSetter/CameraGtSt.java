package com.example.hp.gap.GetterSetter;

import android.graphics.Bitmap;

import com.example.hp.gap.btmNavFragment.StreamFragment;

public class CameraGtSt {
Bitmap imgBitmap;
String imgName;


    public CameraGtSt(Bitmap imgBitmap, String imgName) {
        this.imgBitmap = imgBitmap;
        this.imgName = imgName;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}

package com.example.hp.gap.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.hp.gap.R;


public class CommonUtil {


    static AlertDialog alertDialog;
    public static void loading(Context context) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mview = layoutInflater.inflate(R.layout.loading, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(mview);

        alertDialog = builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    public static void cancelLoading() {
        alertDialog.dismiss();
    }





}

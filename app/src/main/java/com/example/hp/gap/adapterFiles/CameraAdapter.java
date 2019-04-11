package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.gap.GetterSetter.CameraGtSt;


import com.example.hp.gap.R;

import java.util.ArrayList;

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.ItemViewHolder> {
    Context context;
    CameraGtSt[] itemModels;

    public CameraAdapter(CameraGtSt[] itemModels) {
        this.itemModels = itemModels;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.img_rclayout_row, parent, false);

        return new CameraAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.imgName.setText(itemModels[position].getImgName());
        holder.imgCamera.setImageBitmap(itemModels[position].getImgBitmap());
    }

    @Override
    public int getItemCount() {
        if (itemModels == null)
            return 0;
        else return itemModels.length;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCamera;
        TextView imgName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgName = itemView.findViewById(R.id.img_name);

            imgCamera = itemView.findViewById(R.id.img_camera);

        }


    }

}

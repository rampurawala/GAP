package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.ClassShare.ImageVideoGtSt;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecyclerLinkAdapter extends RecyclerView.Adapter<RecyclerLinkAdapter.ItemViewHolder> {

    ArrayList<ImageVideoGtSt> mArrayUri = new ArrayList<ImageVideoGtSt>();
    PreferenceManager preferenceManager;
    Context context;


    public RecyclerLinkAdapter(ArrayList<ImageVideoGtSt> mArrayUri, Context context) {
        this.mArrayUri = mArrayUri;
        this.context = context;
        preferenceManager = new PreferenceManager(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_row_layout, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        final ImageVideoGtSt list = mArrayUri.get(position);
        Picasso.with((context)).load(R.drawable.link_image).placeholder(R.mipmap.ic_launcher).into(holder.img);
        // holder.img.setImageURI(Uri.parse(list.getImg()));
        holder.imgname.setText(list.getName());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemLabel = list.getName();

                // Remove the item on remove/button click
                mArrayUri.remove(position);

                notifyItemRemoved(position);

                ArrayList<String> getmArrayLink = new ArrayList<>();

                for (int i = 0; i < mArrayUri.size(); i++) {
                    getmArrayLink.add(mArrayUri.get(i).getName());

                }


                //Preference manager


                Log.d("sizeafter", String.valueOf(getmArrayLink.size()));

                notifyItemRangeChanged(position, mArrayUri.size());

                Set<String> setmArraylink = new HashSet<>();

                setmArraylink.addAll(getmArrayLink);

                preferenceManager.setmarrayLink(setmArraylink);

                Log.e("RemoveDataUri", String.valueOf(preferenceManager.getmarrayuri().size()));

                Toast.makeText(context, "Removed : " + itemLabel, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mArrayUri == null)
            return 0;
        else return mArrayUri.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView imgname;
        Button remove;
        ImageView img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgname = itemView.findViewById(R.id.imgname);
            img = itemView.findViewById(R.id.img);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}

package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.gap.GetterSetter.ClassGtSt;
import com.example.hp.gap.GetterSetter.StreamGtSt;
import com.example.hp.gap.R;

import java.util.ArrayList;

public class AdapterClassFragment extends RecyclerView.Adapter<AdapterClassFragment.ItemViewHolder>{

    Context context;
    ArrayList<ClassGtSt> itemModels;

    public AdapterClassFragment(Context context, ArrayList<ClassGtSt> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_classwork, parent, false);

        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final ClassGtSt itemModal = itemModels.get(position);
        holder.name.setText(itemModal.getName());
        holder.date.setText(itemModal.getDate());
        holder.img.setImageResource(itemModal.getImage());
    }

    @Override
    public int getItemCount() {
        if (itemModels == null)
            return 0;
        else return itemModels.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name,date;
        ImageView img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.assName);
            date = itemView.findViewById(R.id.assDate);
            img = itemView.findViewById(R.id.img);
        }


    }
}

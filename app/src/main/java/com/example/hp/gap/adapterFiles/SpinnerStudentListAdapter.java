package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.R;


import java.util.List;

public class SpinnerStudentListAdapter extends ArrayAdapter<ClassStudentResponse> {

    private int layoutId;
    int flag = 0;

    public SpinnerStudentListAdapter(@NonNull Context context, int resource, @NonNull List<ClassStudentResponse> objects) {
        super(context, resource, objects);
        this.layoutId = resource;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ClassStudentResponse classStudentResponse = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutId, parent, false);

            viewHolder.textView = (TextView) convertView.findViewById(R.id.studentList);


            convertView.setTag(viewHolder); // view lookup cache stored in tag

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


            viewHolder.textView.setText(classStudentResponse.getUName());



        return convertView;
    }

    static class ViewHolder {
        TextView textView, all_static;

    }


}

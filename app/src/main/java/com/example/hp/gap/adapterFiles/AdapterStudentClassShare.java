package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.R;

import java.util.List;



    public class AdapterStudentClassShare extends ArrayAdapter<ClassStudentResponse> {


        private int layoutId;

        public AdapterStudentClassShare(@NonNull Context context, int resource, @NonNull List<ClassStudentResponse> objects) {
            super(context, resource, objects);
            this.layoutId = resource;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ClassStudentResponse brand=getItem(position);

           ViewHolder viewHolder;
            if (convertView==null) {

                viewHolder = new ViewHolder();

                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layoutId, parent, false);

               // viewHolder.stu_name = (TextView) convertView.findViewById(R.id.stu_name);
                viewHolder.stu_chk = convertView.findViewById(R.id.stu_chk);
                convertView.setTag(viewHolder); // view lookup cache stored in tag

            }else {

                viewHolder = (ViewHolder) convertView.getTag();

            }

            viewHolder.stu_chk.setText(brand.getUName());
            //   viewHolder.imageView.setImageResource(brand.getImage());


            return convertView;
        }
        public static class ViewHolder{

            CheckedTextView stu_chk;
        }


    }




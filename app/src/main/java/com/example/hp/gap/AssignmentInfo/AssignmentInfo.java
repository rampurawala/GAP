package com.example.hp.gap.AssignmentInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.gap.R;

public class AssignmentInfo extends AppCompatActivity {
    TextView show, ass_name, ass_display;
    int flag=0;
ImageView showBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);
        show = findViewById(R.id.show);
        ass_name = findViewById(R.id.ass_name);
        ass_display = findViewById(R.id.ass_display);
        showBtn=findViewById(R.id.showBtn);
        show.setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View v) {
             if(flag==0) {
                 showBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                 show.setText("Hide Details");
                 ass_name.setVisibility(View.VISIBLE);
                 ass_display.setVisibility(View.VISIBLE);
                 flag=1;
             }
             else{
                 showBtn.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                 show.setText("Show Details");
                 ass_name.setVisibility(View.GONE);
                 ass_display.setVisibility(View.GONE);
                 flag=0;
             }
            }
        });

    }
}

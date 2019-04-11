package com.example.hp.gap.MessageInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.FileList;
import com.example.hp.gap.APIresponse.MessageFileListResponse;
import com.example.hp.gap.APIresponse.TeacherListResponse;
import com.example.hp.gap.APIresponse.TeacherResponse;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.R;
import com.example.hp.gap.adapterFiles.AdapterFileList;
import com.example.hp.gap.adapterFiles.AdapterTeacherClassList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageInfo extends AppCompatActivity {
    TextView senderName, messageTitle;
    RecyclerView messageList;
ImageView img;
    List<FileList> messageFileList = new ArrayList<FileList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_message_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        senderName = findViewById(R.id.senderName);
        messageTitle = findViewById(R.id.messageTitle);
        messageList = findViewById(R.id.messageFileList);
        //messageList.setHasFixedSize(true);
        img=findViewById(R.id.img);
        messageList.setLayoutManager(new LinearLayoutManager(this));


        senderName.setText(i.getStringExtra("senderName"));
        messageTitle.setText(i.getStringExtra("assignmentTitle"));
        Picasso.with((MessageInfo.this)).load(i.getStringExtra("profilePic")).placeholder(R.drawable.person_default_theme).into(img);

        loadData(Integer.parseInt(i.getStringExtra("message_id")));

    }

    private void loadData(int messageId) {
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<MessageFileListResponse> getBrandResponseCall = apiInterface.MESSAGE_FILE_LIST_RESPONSE_CALL("qw", messageId);

        getBrandResponseCall.enqueue(new Callback<MessageFileListResponse>() {

            @Override
            public void onResponse(Call<MessageFileListResponse> call, Response<MessageFileListResponse> response) {
                if (response.body().getSuccess() == 200) {

                    messageFileList = response.body().getFileList();
                    Log.e("##", String.valueOf(messageFileList.size()));
                    AdapterFileList filelistAdapter = new AdapterFileList(messageFileList, MessageInfo.this);
                    messageList.setAdapter(filelistAdapter);


                } else {
                    Toast.makeText(MessageInfo.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageFileListResponse> call, Throwable t) {
                Toast.makeText(MessageInfo.this, "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }


}

package com.example.spacechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private EditText edtText;
    private TextView txtUsernameChat;
    private ProgressBar progressBar;
    private ImageView img_toolbar, imgSendMessage;
    private ArrayList<Message> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        imgSendMessage = findViewById(R.id.imgSendMessage);
        img_toolbar = findViewById(R.id.img_toolbar);
        recyclerView = findViewById(R.id.recyclerMessages);
        edtText = findViewById(R.id.edtText);
        txtUsernameChat = findViewById(R.id.txtUsernameChat);
        progressBar = findViewById(R.id.progressBar);
        messages = new ArrayList<>();


    }
}
package com.example.eneadodi.gci_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class sendMessage extends AppCompatActivity {
    private Button back,send;
    private TextView sender,reciever;
    private EditText message;
    private DataBaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back = (Button)findViewById(R.id.goBack);
        String s = getIntent().getStringExtra("username");
        String r = getIntent().getStringExtra("password");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rintent = new Intent();
                setResult(RESULT_OK,rintent);
                finish();
            }
        });
        send = (Button)findViewById(R.id.sendMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message m = new Message(r,s);
                m.setMessage(m.breakUp(m.getMessage()));
                try {
                    mydb.addData(m);
                }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        sender = (TextView)findViewById(R.id.sender);
        sender.setText();
        reciever = (TextView)findViewById(R.id.reciever);
        reciever.setText();
        message = (EditText)findViewById(R.id.messageToSender);
        setContentView(R.layout.activity_send_message);
    }



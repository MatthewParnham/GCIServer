package com.example.eneadodi.gci_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LogInPage extends AppCompatActivity {
    private Button login,signup;
    private EditText username,password,hostET,portET;
    private ImageView logo;
    private DataBaseHelper mydb;
    private int port;
    private String host;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hostET = (EditText) findViewById(R.id.host);
        portET = (EditText) findViewById(R.id.port);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString();
                String pass = password.getText().toString();
                if(mydb.containsUsername(usr)){
                    Cursor c = mydb.getItemPassword(usr);
                    String str;
                    if(c.moveToFirst()){
                        str = c.getString(c.getColumnIndex("Username"));
                    }
                    if(str.equals((pass))){
                        startMainAcitivity();
                    }
                }
                else{
                    toastMessage("Username Does not Exist");
                }
            }
        });
        signup = (Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateAccountActivity();
            }
        });
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        logo = (ImageView)findViewById(R.id.logo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
    }

    protected  void startCreateAccountActivity(){
        Intent i = new Intent(this, createAccount.class);
        startActivityForResult(i,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(resultCode,resultCode,data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String[] arr = {data.getStringExtra("username"), data.getStringExtra("password")};
                addData(arr);
            }
        }
    }

    public void addData(String[] newEntry){
        boolean insertData = mydb.addData(newEntry);
        if(insertData){
            toastMessage("Account successfully created");
        }
        else{
            toastMessage("Account creation unsuccessful");
        }
    }

    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void startMainAcitivity(){
        Intent i = new Intent(this, mainActivity.class);
        i.putExtra("username",username.getText().toString());
        i.putExtra("host", hostET.getText().toString());
        i.putExtra("path", portET.getText().toString());
        startActivity(i);
    }
}

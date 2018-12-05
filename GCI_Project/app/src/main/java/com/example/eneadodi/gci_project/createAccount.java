package com.example.eneadodi.gci_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createAccount extends AppCompatActivity {
    private EditText usrname,pass1,pass2;
    private Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        usrname = (EditText)findViewById(R.id.username);
        pass1 = (EditText)findViewById(R.id.password);
        pass2 = (EditText)findViewById(R.id.password2);
        signup = (Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass1.getText().toString().equals(pass2.getText().toString())){
                    if(pass1.getText().toString().length() < 6){
                        toastMessage("Password must be longer than 6 characters long!");
                    }
                    else if(!pass1.getText().toString().matches(".*\\d+.*")){
                        toastMessage("Password must contain atleast one digit!");
                    }
                    else{
                        String usr = usrname.getText().toString();
                        String pass = pass1.getText().toString();
                        Intent rintent = new Intent();
                        rintent.putExtra("username",usr);
                        rintent.putExtra("password",pass);
                        setResult(RESULT_OK,rintent);
                        finish();
                    }
                }
                else{
                    toastMessage("Passwords do not Match! try again");
                }
            }
        });
    }
    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}

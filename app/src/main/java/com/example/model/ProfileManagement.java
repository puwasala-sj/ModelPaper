package com.example.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import Database.DBHelper;

public class ProfileManagement extends AppCompatActivity {
    DBHelper db;
    Button update,register;
    EditText user,pass,dob;
    RadioButton female,male;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        db = new DBHelper(this);
        user = (EditText)findViewById(R.id.user);
        dob = (EditText)findViewById(R.id.db);
        pass = (EditText)findViewById(R.id.word);
        female = (RadioButton) findViewById(R.id.f);
        male = (RadioButton)findViewById(R.id.m);

        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString();
                String date = dob.getText().toString();
                String password = pass.getText().toString();

                 boolean res = db.addInfo(username,date,password,gender);
                 if(res == true){
                     Toast.makeText(ProfileManagement.this,"Successfully registered",Toast.LENGTH_SHORT).show();
                     Intent login = new Intent(ProfileManagement.this,Home.class);
                     startActivity(login);
                 }
                 else {
                     Toast.makeText(ProfileManagement.this,"Error",Toast.LENGTH_SHORT).show();
                 }
            }
        });


        update = (Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent up = new Intent(ProfileManagement.this, EditProfile.class);
                startActivity(up);
            }
        });


    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.m:
                if (checked)
                    gender = "male";
                break;
            case R.id.f:
                if (checked)
                    gender = "female";
                break;
        }
    }
}

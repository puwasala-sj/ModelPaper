package com.example.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBHelper;

public class Home extends AppCompatActivity {

    Button register,login;
    EditText user,pass;
    long userId = -1;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DBHelper(this);
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);

        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString();
                String password = pass.getText().toString();

                userId = db.checkUser(username, password);
                if (userId != -1) {
                    Intent intent = new Intent(Home.this, EditProfile.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                } else {
                    Toast.makeText(Home.this, "Error.. Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(Home.this,ProfileManagement.class);
                startActivity(reg);
            }
        });
    }
}

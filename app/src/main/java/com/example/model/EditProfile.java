package com.example.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import Database.DBHelper;
import Database.UserProfile;

public class EditProfile extends AppCompatActivity {
    Button search,edit,delete;
    DBHelper db;
    long userId;
    EditText username,password,date;
    RadioButton male,female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = (EditText)findViewById(R.id.user);
        date = (EditText)findViewById(R.id.db);
        password = (EditText)findViewById(R.id.word);
        male = findViewById(R.id.m);
        female = findViewById(R.id.f);

        db = new DBHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getLongExtra("userId", -1);
        }

        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();

                Cursor cursor = db.readAllInfor();

                while (cursor.moveToNext()) {
                    if (user.equals(cursor.getString(1))) {
                         userId = cursor.getLong(0);
                         date.setText(cursor.getString(2));
                         password.setText(cursor.getString(3));

                        if (cursor.getString(4) != null) {
                            if (cursor.getString(4).equals("Male")) {
                                male.setChecked(true);
                            } else {
                                female.setChecked(false);
                            }
                        }
                    }
                }
                cursor.close();
                }

        });

        edit = (Button)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                String dob = date.getText().toString();
                String pass = password.getText().toString();

                //edit logged in user
                if (db.updateInfo(userId, uname, dob, pass, gender)) {
                    Toast.makeText(EditProfile.this, "Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile.this, "Cannot update!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.deleteInfo(userId)) {
                    Toast.makeText(EditProfile.this, "Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile.this, "User not in the table!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

package com.example.mymusicapp.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymusicapp.R;
import com.example.mymusicapp.models.UserModel;
import com.google.gson.Gson;

import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity {

    ArrayList<UserModel> users;
    Button registerButton;
    EditText name;
    EditText surname;
    EditText phone;
    EditText email;
    EditText password1;
    EditText password2;
    String name_text;
    String surname_text;
    String phone_text;
    String email_text;
    String password1_text;
    String password2_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //todo add photo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        users = (ArrayList<UserModel>) bundle.getSerializable("users");

        registerButton = findViewById(R.id.register_button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = findViewById(R.id.register_edittext_name);
                surname = findViewById(R.id.register_edittext_surname);
                phone = findViewById(R.id.register_edittext_phone);
                email = findViewById(R.id.register_edittext_email);
                password1 = findViewById(R.id.register_edittext_password1);
                password2 = findViewById(R.id.register_Edittext_password2);

                name_text = name.getText().toString();
                surname_text = surname.getText().toString();
                phone_text = phone.getText().toString();
                email_text = email.getText().toString();
                password1_text = password1.getText().toString();
                password2_text = password2.getText().toString();
                //input checks
                //all areas must be filled
                //there can not be same email address
                //passwords should be same
                //there can be same name users(real life it can so why not)
                if(name_text.equals("") ||surname_text.equals("") || phone_text.equals("")
                        || email_text.equals("") || password1_text.equals("") || password2_text.equals("")){
                    Toast.makeText(RegisterActivity.this, "You need to fill all the fields.", Toast.LENGTH_SHORT).show();
                }
                else if(!password1_text.equals(password2_text)){
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
                else if(checkExistingEmail(email_text)){
                    Toast.makeText(RegisterActivity.this, "That E-Mail is already in use.", Toast.LENGTH_SHORT).show();
                }
                // saving new user
                else{
                    users.add(new UserModel(name_text, surname_text, email_text, phone_text, password1_text));
                    saveNewUser();
                    //send email


                    Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("users", users);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            }
        });

    }

    private boolean checkExistingEmail(String email) {
        for (UserModel userInfo : users) {
            if(userInfo.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    private void saveNewUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        editor.putString("users", json);
        editor.apply();
    }


}
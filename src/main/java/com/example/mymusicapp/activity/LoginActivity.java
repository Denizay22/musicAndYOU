package com.example.mymusicapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymusicapp.R;
import com.example.mymusicapp.models.UserModel;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    private ArrayList<UserModel> users;
    EditText email;
    EditText password;
    String email_text;
    String password_text;
    Button login;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();
        users = (ArrayList<UserModel>) bundle.getSerializable("users");
        Log.println(Log.DEBUG, "3131313131",users.toString());


        login = findViewById(R.id.login_button_login);
        register = findViewById(R.id.login_button_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = findViewById(R.id.login_edittext_email);
                password = findViewById(R.id.login_edittext_password);

                email_text = email.getText().toString();
                password_text = password.getText().toString();
                //input checks
                if(email_text.equals("") || password_text.equals("")){
                    Toast.makeText(LoginActivity.this, "You need to enter your E-Mail and Password.", Toast.LENGTH_SHORT).show();
                }
                //email password
                //situations:
                //1 = everything is fine
                //2 = password is wrong
                //3 = user with email does not exist
                else{
                    Bundle bundle = new Bundle();
                    int check = checkLogin(email_text, password_text, bundle);
                    switch (check){
                        case 1:
                            Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                            //send user info to register screen and start register activity
                            Intent i = new Intent(LoginActivity.this, ListMusicsActivity.class);
                            i.putExtras(bundle);
                            startActivity(i);
                            break;
                        case 2:
                            //password is wrong
                            Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            //email not found
                            Toast.makeText(LoginActivity.this, "User with E-Mail \"" + email_text + "\" not found.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send user info to register screen and start register activity
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("users", users);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    private int checkLogin(String email, String password, Bundle bundle){
        for(UserModel user : users){
            if(user.getEmail().equals(email)){
                if(user.getPassword().equals(password)){
                    bundle.putSerializable("logged_in_user", user);
                    return 1;
                }
                else{
                    return 2;
                }
            }
        }
        Log.println(Log.DEBUG, "3131313131", "USER NOT FOUND!" + "\n entered email: " +
                email + "\n" + users.toString());
        return 3;
    }
}
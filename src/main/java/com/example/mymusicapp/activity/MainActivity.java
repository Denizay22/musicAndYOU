package com.example.mymusicapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.example.mymusicapp.R;
import com.example.mymusicapp.models.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<UserModel> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        new CountDownTimer(5000, 1000){
            @Override
            public void onTick(long l) {
            }

            public void onFinish(){
                //send user info to login screen
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("users", users);
                i.putExtras(bundle);
                startActivity(i);

            }
        }.start();
        //todo change app name and logo
    }


    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("users", null);
        Type type = new TypeToken<ArrayList<UserModel>>() {}.getType();
        users = gson.fromJson(json, type);

        if(users == null) {
            users = new ArrayList<>();
        }
    }


}
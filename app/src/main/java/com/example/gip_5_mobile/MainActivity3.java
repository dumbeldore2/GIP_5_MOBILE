package com.example.gip_5_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    //basic auth string
    Intent intent;
    String base;

    // url string
    String url = "http://192.168.1.103:8080/api/v1/";

    //views initen
    ConstraintLayout constraintLayout1,constraintLayout2,constraintLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //base string maken
        intent = getIntent();
        base = intent.getStringExtra("base");

        // views connecten
        constraintLayout1 = findViewById(R.id.constraint_2);
        constraintLayout2 = findViewById(R.id.constraint_3);
        constraintLayout3 = findViewById(R.id.constraint_4);

        //functies
        fun_click_1();
        //fun_click_2();
        //fun_click_3();
    }

    public void fun_click_1(){
        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity2.class);
                intent.putExtra("base", base);
                startActivity(intent);
            }
        });
    }
    public void fun_click_2(){
        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity2.class);
                intent.putExtra("base", base);
                startActivity(intent);
            }
        });
    }
    public void fun_click_3(){
        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity2.class);
                intent.putExtra("base", base);
                startActivity(intent);
            }
        });
    }
}
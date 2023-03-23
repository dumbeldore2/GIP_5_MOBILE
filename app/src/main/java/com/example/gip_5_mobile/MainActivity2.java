package com.example.gip_5_mobile;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity2 extends AppCompatActivity {

    //initen van views


    //basic auth string
    Intent intent;
    String base;
    // url string
    String url = "http://192.168.1.103:8080/api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //base string maken
        intent = getIntent();
        base = intent.getStringExtra("base");

        //conecten van views
        //functies
        //vragen achter inventory
        //fun_api();
    }

    public void fun_api(){

        //api request
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url + "item/getall", null,
                new Response.Listener<JSONObject>() {
                    @Override public void onResponse(JSONObject response) {
                        //System.out.println(response.toString());
                        if (!response.toString().isEmpty()){
                            System.out.println(response.toString());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Authorization", "Basic " + base);

                //headers.put("Content-Length", "<calculated when request is sent>");
                //headers.put("Host", "<calculated when request is sent>");
                //headers.put("Accept-Encoding", "gzip, deflate,br");
                //headers.put("Connection", "keep-alive");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
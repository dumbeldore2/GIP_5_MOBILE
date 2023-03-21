package com.example.gip_5_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
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

public class MainActivity extends AppCompatActivity {

    // url string
    String url = "http://192.168.1.103:8080/api/v1/";

    //initen van views
    EditText editText1,editText2;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //views conecten
        editText1 = findViewById(R.id.email);
        editText2 = findViewById(R.id.password);
        button1 = findViewById(R.id.login);


        //functie
        click_fun_1();
    }

    public void click_fun_1(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //api request
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url + "user/email" +
                        "/yago.engels@gmail.com", null,
                        new Response.Listener<JSONObject>() {
                            @Override public void onResponse(JSONObject response) {
                                System.out.println(response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }

                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError{
                        Map<String, String> headers = new HashMap<>();

                        //autherization
                        String auth = getEditText1() + ":" + getEditText2();
                        byte [] bytes = auth.getBytes(StandardCharsets.UTF_8);
                        String base = Base64.getEncoder().encodeToString(bytes);
                        System.out.println(base);

                        headers.put("Content-Type", "application/json;charset=UTF-8");
                        headers.put("Authorization", "Basic " + base);
                        System.out.println("Basic " + auth);
                        //headers.put("Content-Length", "<calculated when request is sent>");
                        //headers.put("Host", "<calculated when request is sent>");
                        //headers.put("Accept-Encoding", "gzip, deflate,br");
                        //headers.put("Connection", "keep-alive");
                        return headers;
                    }
                };

                requestQueue.add(jsonObjectRequest);

                //Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                //startActivity(intent);
            }
        });
    }

    public String getEditText1() {
        return editText1.getText().toString();
    }

    public String getEditText2() {
        return editText2.getText().toString();
    }
}
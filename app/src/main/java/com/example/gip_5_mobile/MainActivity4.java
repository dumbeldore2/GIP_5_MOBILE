package com.example.gip_5_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity {

    //basic auth string
    Intent intent;
    String base;
    String email;

    // url string
    String url = "http://192.168.1.103:8080/api/v1/";

    //views initen
    TextView textView1,textView2;
    EditText editText0,editText1,editText2,editText3,editText4,editText5;
    String string0,string1,string2,string3,string4,string5,string6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //base string maken
        intent = getIntent();
        base = intent.getStringExtra("base");
        email = intent.getStringExtra("email");

        //views conecten
        textView1 = findViewById(R.id.text_1);
        textView2 = findViewById(R.id.text_2);

        editText0 = findViewById(R.id.edit_text_0);
        editText1 = findViewById(R.id.edit_text_1);
        editText2 = findViewById(R.id.edit_text_2);
        editText3 = findViewById(R.id.edit_text_3);
        editText4 = findViewById(R.id.edit_text_4);
        editText5 = findViewById(R.id.edit_text_5);

        //fun
        fun_get_api();
    }
    public void fun_get_api(){
        //api request
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url + "user/email" +
                        "/" + email, null,
                new Response.Listener<JSONObject>() {
                    @Override public void onResponse(JSONObject response) {
                        //System.out.println(response.toString());
                        //als er data word teruggegeven zal er worden door gegaan naar de
                        // volgende activity
                        if (!response.toString().isEmpty()){
                            toJava(response.toString());
                            setEdittexts();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                //System.out.println(error.networkResponse.statusCode);
                if (error.networkResponse.statusCode == 401){
                    textView1.setVisibility(View.VISIBLE);
                    textView1.setText("fout met laden van de user");
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                //autherization

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

    public void toJava(String s){
        String s1 [] = s.split(",");
        for (int i = 0; i < s1.length ; i++){
            String s2 [] = s1[i].split(":");
            if (i == 0){
                string0 = s2[1];
            }
            if (i == 1){
                string1 = s2[1];
            }
            if (i == 2){
                string2 = s2[1];
            }
            if (i == 3){
                string3 = s2[1];
            }
            if (i == 4){
                string4 = s2[1];
            }
            if (i == 5){
                string5 = s2[1];
            }
            if (i == 6){
                string6 = s2[1];
            }
        }
    }

    public void setEdittexts(){
        editText0.setHint(string0);
        editText1.setHint(string1.substring(1,string1.length()-1));
        editText2.setHint(string2.substring(1,string2.length()-1));
        editText3.setHint(string3.substring(1,string3.length()-1));
        editText4.setHint(string4.substring(1,string4.length()-1));
        editText5.setHint(string6.substring(1,string6.length()-1));
    }

    public void click_safe(){
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // Make new json object and put params in it
                JSONObject jsonParams = new JSONObject();
                try {
                    if (!editText1.getText().equals(string1)){
                        jsonParams.put("firstname", editText1.getText().toString());
                    } else {
                        jsonParams.put("firstname", string1);
                    }
                    if (!editText2.getText().equals(string2)){
                        jsonParams.put("lastname", editText2.getText().toString());
                    } else {
                        jsonParams.put("lastname", string2);
                    }
                    if (!editText3.getText().equals(string3)){
                        jsonParams.put("email", editText3.getText().toString());
                    } else {
                        jsonParams.put("email", string3);
                    }
                    if (!editText4.getText().equals(string4)){
                        jsonParams.put("birthdate", editText4.getText().toString());
                    } else {
                        jsonParams.put("birthdate", string4);
                    }
                    jsonParams.put("roles", string5);

                    if (!editText5.getText().equals(string6)){
                        jsonParams.put("password", editText5.getText().toString());
                    } else {
                        jsonParams.put("password", string6);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //api request
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(Request.Method.POST,
                                url + "user/update/" + Integer.parseInt(string0), jsonParams,
                                new Response.Listener<JSONObject>() {
                                    @Override public void onResponse(JSONObject response) {
                                        //System.out.println(response.toString());
                                        //als er data word teruggegeven zal er worden door gegaan
                                        // naar de volgende mainacitivty
                                        if (!response.toString().isEmpty()){
                                            fun_get_api();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override public void onErrorResponse(VolleyError error) {
                                //System.out.println(error.networkResponse.statusCode);
                                if (error.networkResponse.statusCode == 401){
                                }
                            }

                        });

                requestQueue.add(jsonObjectRequest);

            }
        });
    }
}
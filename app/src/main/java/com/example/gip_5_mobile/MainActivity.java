package com.example.gip_5_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    // url string
    String url = "http://192.168.1.103:8080/api/v1/";

    //initen van views
    TextView textView1,textView2;
    EditText editText1,editText2;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //views conecten
        textView1 = findViewById(R.id.fout);
        textView2 = findViewById(R.id.register);

        editText1 = findViewById(R.id.email);
        editText2 = findViewById(R.id.password);

        button1 = findViewById(R.id.login);


        //functie
        //normaal inlog
        click_fun_1();
        //register
        click_fun_2();
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
                                //System.out.println(response.toString());
                                //als er data word teruggegeven zal er worden door gegaan naar de
                                // volgende activity
                                if (!response.toString().isEmpty()){
                                    Intent intent = new Intent(getApplicationContext(),
                                            MainActivity2.class);
                                    startActivity(intent);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        //System.out.println(error.networkResponse.statusCode);
                        if (error.networkResponse.statusCode == 401){
                            textView1.setVisibility(View.VISIBLE);
                            textView1.setText("email or password is wrong \n if account doenst " +
                                    "exist try to register one!");
                        }
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

    public void click_fun_2(){
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                System.out.println("llo");
                final Dialog dialog = new Dialog(MainActivity.this);
                //title ios meegegeven dus niet nodig
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //buiten het view klikken beindig de dialog
                dialog.setCancelable(true);
                //dialog linken
                dialog.setContentView(R.layout.activity_main_dialog);

                //init de views van het dialoog
                final EditText dialog_editText = dialog.findViewById(R.id.edit_text_1);
                final EditText dialog_editText2 = dialog.findViewById(R.id.edit_text_2);
                final EditText dialog_editText3 = dialog.findViewById(R.id.edit_text_3);
                final EditText dialog_editText4 = dialog.findViewById(R.id.edit_text_4);
                final EditText dialog_editText5 = dialog.findViewById(R.id.edit_text_5);

                Button button = dialog.findViewById(R.id.button);

                //button function
                button.setOnClickListener(v1 -> {
                    //check of alles wel is ingevuld
                    if (dialog_editText.getText().length() <= 0){
                        dialog_editText.setHint("first name cant be empty");
                        dialog_editText.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    if (dialog_editText2.getText().length() <= 0){
                        dialog_editText2.setHint("last name cant be empty");
                        dialog_editText2.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    if (dialog_editText3.getText().length() <= 0){
                        dialog_editText3.setHint("birth date cant be empty");
                        dialog_editText3.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    if (dialog_editText4.getText().length() <= 0){
                        dialog_editText4.setHint("email cant be empty");
                        dialog_editText4.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    if (dialog_editText5.getText().length() <= 0){
                        dialog_editText5.setHint("password cant be empty");
                        dialog_editText5.setBackgroundColor(getResources().getColor(R.color.red));
                    }

                    if (!dialog_editText.getText().toString().isEmpty() && !dialog_editText2.getText().toString().isEmpty() && !dialog_editText3.getText().toString().isEmpty() && !dialog_editText4.getText().toString().isEmpty() && !dialog_editText5.getText().toString().isEmpty()){

                        // Make new json object and put params in it
                        JSONObject jsonParams = new JSONObject();
                        try {
                            jsonParams.put("firstname", dialog_editText.getText().toString());
                            jsonParams.put("lastname", dialog_editText2.getText().toString());
                            jsonParams.put("email", dialog_editText4.getText().toString());
                            jsonParams.put("birthdate", dialog_editText3.getText().toString());
                            jsonParams.put("roles", "USER");
                            jsonParams.put("password", dialog_editText5.getText().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //api request
                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        JsonObjectRequest jsonObjectRequest =
                                new JsonObjectRequest(Request.Method.POST,
                                url + "user/adduser", jsonParams,
                                new Response.Listener<JSONObject>() {
                                    @Override public void onResponse(JSONObject response) {
                                        //System.out.println(response.toString());
                                        //als er data word teruggegeven zal er worden door gegaan
                                        // naar de volgende mainacitivty
                                        if (!response.toString().isEmpty()){
                                            Intent intent = new Intent(getApplicationContext(),
                                                    MainActivity2.class);
                                            startActivity(intent);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override public void onErrorResponse(VolleyError error) {
                                //System.out.println(error.networkResponse.statusCode);
                                if (error.networkResponse.statusCode == 401){
                                    textView1.setVisibility(View.VISIBLE);
                                    textView1.setText("no user was added\nthis user already " +
                                            "exists");
                                }
                            }

                        });

                        requestQueue.add(jsonObjectRequest);


                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}
package com.example.gip_5_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    //initen van views
    Button button;
    TextView textView1;
    ImageView imageview1;
    ListView listView;
    MainActivity2_adapter mainActivity2_adapter;
    String s1,s2,s3,s4,s5,s6;

    //basic auth string
    Intent intent;
    String base;
    String email;

    //listview
    String id1 [] = new String[]{"eloo"};
    String name1 [] = new String[]{"eloo"};
    String type1 [] = new String[]{"eloo"};
    String extraInfo1 [] = new String[]{"eloo"};
    String price1 [] = new String[]{"eloo"};
    String amount1 [] = new String[]{"eloo"};
    String modelnr1 [] = new String[]{"eloo"};

    // url string
    String url = "http://192.168.1.103:8080/api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //base string maken
        intent = getIntent();
        base = intent.getStringExtra("base");
        email = intent.getStringExtra("email");

        //conecten van views
        button = findViewById(R.id.button);
        textView1 = findViewById(R.id.text_1);
        imageview1 = findViewById(R.id.image_1);
        listView =  findViewById(R.id.list);

        //functies
        //vragen achter inventory
        fun_add_item();
        fun_account();
        fun_click_item();

        //api request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,
                url + "item/getall", null,
                new Response.Listener<JSONArray>() {
                    @Override public void onResponse(JSONArray response) {
                        //String [][] array = new String[response.length()][];
                        try {
                            System.out.println(response.length());
                            String id [] = new String[response.length()];
                            String name [] = new String[response.length()];
                            String type [] = new String[response.length()];
                            String extraInfo [] = new String[response.length()];
                            String price [] = new String[response.length()];
                            String amount [] = new String[response.length()];
                            String modelnr [] = new String[response.length()];

                            for (int i = 0; i < response.length(); i++){
                                //System.out.println(i);
                                id[i] = (String) response.getJSONObject(i).get("id").toString();
                                name[i] = (String) response.getJSONObject(i).get("name").toString();
                                type[i] = (String) response.getJSONObject(i).get("type").toString();
                                extraInfo[i] = (String) response.getJSONObject(i).get("extraInfo").toString();
                                price[i] = (String) response.getJSONObject(i).get("price").toString();
                                amount[i] = (String) response.getJSONObject(i).get("amount").toString();
                                modelnr[i] = (String) response.getJSONObject(i).get("modelNr").toString();

                            }
                            id1 = id;
                            name1 = name;
                            type1 = type;
                            extraInfo1 = extraInfo;
                            price1 = price;
                            amount1 = amount;
                            modelnr1 = modelnr;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        mainActivity2_adapter = new MainActivity2_adapter(MainActivity2.this
                                ,id1, name1,type1,price1);
                        listView.setAdapter(mainActivity2_adapter);
                        //System.out.println(array[0][5]);
                        //System.out.println(array[1][2]);
                        //System.out.println(array[2][3]);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                //System.out.println("error");
                System.out.println(error);
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

    public void fun_add_item(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity2.this);
                //title ios meegegeven dus niet nodig
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //buiten het view klikken beindig de dialog
                dialog.setCancelable(true);
                //dialog linken
                dialog.setContentView(R.layout.activity_main2_dialog);

                //init de views van het dialoog
                final EditText dialog_editText = dialog.findViewById(R.id.edit_text_1);
                final EditText dialog_editText2 = dialog.findViewById(R.id.edit_text_2);
                final EditText dialog_editText3 = dialog.findViewById(R.id.edit_text_3);
                final EditText dialog_editText4 = dialog.findViewById(R.id.edit_text_4);
                final EditText dialog_editText5 = dialog.findViewById(R.id.edit_text_5);
                final EditText dialog_editText6 = dialog.findViewById(R.id.edit_text_6);

                Button button = dialog.findViewById(R.id.button);


                //button function
                button.setOnClickListener(v1 -> {
                    //check of alles wel is ingevuld
                    if (dialog_editText.getText().length() <= 0) {
                        dialog_editText.setHint("name cant be empty");
                    }
                    if (dialog_editText2.getText().length() <= 0) {
                        dialog_editText2.setHint("type cant be empty");
                    }
                    if (dialog_editText3.getText().length() <= 0) {
                        dialog_editText3.setHint("modelnr cant be empty");
                    }
                    if (dialog_editText4.getText().length() <= 0) {
                        dialog_editText4.setHint("amount cant be empty");
                    }
                    if (dialog_editText5.getText().length() <= 0) {
                        dialog_editText5.setHint("extra info cant be empty");
                    }
                    if (dialog_editText6.getText().length() <= 0) {
                        dialog_editText6.setHint("price cant be empty");
                    }
                    if (!dialog_editText.getText().toString().isEmpty() &&
                            !dialog_editText2.getText().toString().isEmpty() &&
                            !dialog_editText3.getText().toString().isEmpty() &&
                            !dialog_editText4.getText().toString().isEmpty() &&
                            !dialog_editText5.getText().toString().isEmpty() &&
                            !dialog_editText6.getText().toString().isEmpty()) {

                        // Make new json object and put params in it
                        JSONObject jsonParams = new JSONObject();
                        try {
                            jsonParams.put("name", dialog_editText.getText().toString());
                            jsonParams.put("type", dialog_editText2.getText().toString());
                            jsonParams.put("model_nr", dialog_editText3.getText().toString());
                            jsonParams.put("amount",
                                    Integer.parseInt(dialog_editText4.getText().toString()));
                            jsonParams.put("extraInfo", dialog_editText5.getText().toString());
                            jsonParams.put("price", dialog_editText6.getText().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //api request
                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
                        JsonObjectRequest jsonObjectRequest =
                                new JsonObjectRequest(Request.Method.POST,
                                        url + "item/add", jsonParams,
                                        new Response.Listener<JSONObject>() {
                                            @Override public void onResponse(JSONObject response) {
                                                //System.out.println(response.toString());
                                                if (!response.toString().isEmpty()) {
                                                    intent.putExtra("base", base);
                                                    startActivity(intent);
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override public void onErrorResponse(VolleyError error) {
                                        //System.out.println(error.networkResponse.statusCode);
                                        if (error.networkResponse.statusCode == 401) {
                                            textView1.setText("no item was added to the inventory");
                                        }
                                    }

                                }){
                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError{
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


                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
    }

    public void fun_account(){
        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity4.class);

                intent.putExtra("base",base);
                intent.putExtra("email",email);

                startActivity(intent);
            }
        });
    }

    public void fun_click_item(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //api request
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url + "item/get/" + Integer.parseInt(id1[position]), null,
                        new Response.Listener<JSONObject>() {
                            @Override public void onResponse(JSONObject response) {
                                final Dialog dialog = new Dialog(MainActivity2.this);
                                //title ios meegegeven dus niet nodig
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                //buiten het view klikken beindig de dialog
                                dialog.setCancelable(true);
                                //dialog linken
                                dialog.setContentView(R.layout.activity_main2_dialog2);

                                //init de views van het dialoog
                                final EditText dialog2_editText = dialog.findViewById(R.id.edit_text_1);
                                final EditText dialog2_editText2 = dialog.findViewById(R.id.edit_text_2);
                                final EditText dialog2_editText3 = dialog.findViewById(R.id.edit_text_3);
                                final EditText dialog2_editText4 = dialog.findViewById(R.id.edit_text_4);
                                final EditText dialog2_editText5 = dialog.findViewById(R.id.edit_text_5);
                                final EditText dialog2_editText6 = dialog.findViewById(R.id.edit_text_6);

                                Button button = dialog.findViewById(R.id.button);
                                Button button2 = dialog.findViewById(R.id.button2);

                                try {
                                    s1 = response.get("name").toString();
                                    s2 = response.get("type").toString();
                                    s3 = response.get("modelNr").toString();
                                    s4 = response.get("amount").toString();
                                    s5 = response.get("extraInfo").toString();
                                    s6 = response.get("price").toString();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (!s1.isEmpty()){
                                    dialog2_editText.setHint("name =" + s1);
                                    dialog2_editText2.setHint("type =" + s2);
                                    dialog2_editText3.setHint("modelnr =" + s3);
                                    dialog2_editText4.setHint("amount =" + s4);
                                    dialog2_editText5.setHint("extra info =" + s5);
                                    dialog2_editText6.setHint("price = €" + s6);
                                }

                                button.setOnClickListener(v1->{
                                    JSONObject jsonParams = new JSONObject();
                                    try {
                                        if (dialog2_editText.getText().length() != 0){
                                            jsonParams.put("name", dialog2_editText.getText().toString());
                                            System.out.println(dialog2_editText.getText().toString());
                                        } else {
                                            jsonParams.put("name", s1);
                                        }
                                        if (dialog2_editText2.getText().length() != 0){
                                            jsonParams.put("type",
                                                    dialog2_editText2.getText().toString());
                                            System.out.println(dialog2_editText2.getText().toString());
                                        } else {
                                            jsonParams.put("type", s2);
                                        }
                                        if (dialog2_editText3.getText().length() != 0){
                                            jsonParams.put("modelNr",
                                                    dialog2_editText3.getText().toString());
                                            System.out.println(dialog2_editText3.getText().toString());
                                        } else {
                                            jsonParams.put("modelNr", s3);
                                        }
                                        if (dialog2_editText4.getText().length() != 0){
                                            jsonParams.put("amount",
                                                    Integer.parseInt(dialog2_editText4.getText().toString()));
                                            System.out.println(dialog2_editText4.getText().toString());
                                        } else {
                                            jsonParams.put("amount", Integer.parseInt(s4));
                                        }
                                        if (dialog2_editText5.getText().length() != 0){
                                            jsonParams.put("extraInfo",
                                                    dialog2_editText5.getText().toString());
                                        } else {
                                            jsonParams.put("extraInfo", s5);
                                        }
                                        if (dialog2_editText6.getText().length() != 0){
                                            jsonParams.put("modelNr",
                                                    dialog2_editText6.getText().toString());
                                        } else {
                                            jsonParams.put("modelNr", s6);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    fun_api_update(jsonParams,position);
                                    dialog.dismiss();
                                });

                                button2.setOnClickListener(v1->{
                                    fun_delete(position);
                                    dialog.dismiss();
                                });

                                dialog.show();

                            }

                        }, new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        //System.out.println("error");
                        System.out.println(error);
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
        });
    }

    public void fun_api_update(JSONObject jsonObject, int i){
        //api request
        RequestQueue requestQueue =
                Volley.newRequestQueue(MainActivity2.this);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.PUT,
                        url + "item/update/" + Integer.parseInt(id1[i]),
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override public void onResponse(JSONObject response) {
                                //System.out.println(response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        System.out.println(error);

                    }

                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError{
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

    public void fun_delete(int i){
        //api request
        RequestQueue requestQueue =
                Volley.newRequestQueue(MainActivity2.this);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.DELETE,
                        url + "item/delete/" + Integer.parseInt(id1[i]),
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override public void onResponse(JSONObject response) {
                                //System.out.println(response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        System.out.println(error);

                    }

                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError{
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
}
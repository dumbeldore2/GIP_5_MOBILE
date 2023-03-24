package com.example.gip_5_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity5 extends AppCompatActivity {

    //basic auth string
    Intent intent;
    String base;
    String email;

    MainActivity5_adapter mainActivity5_adapter;

    //listview
    String id1 [] = new String[]{"eloo"};
    String name1 [] = new String[]{"eloo"};
    String lastName1 [] = new String[]{"eloo"};
    String role1 [] = new String[]{"eloo"};
    ListView listView;

    // url string
    String url = "http://192.168.1.103:8080/api/v1/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        //base string maken
        intent = getIntent();
        base = intent.getStringExtra("base");
        email = intent.getStringExtra("email");

        listView = findViewById(R.id.list);
        //api request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,
                url + "user/getall", null,
                new Response.Listener<JSONArray>() {
                    @Override public void onResponse(JSONArray response) {
                        //String [][] array = new String[response.length()][];
                        try {
                            System.out.println(response.length());
                            String id[] = new String[response.length()];
                            String name[] = new String[response.length()];
                            String lastName[] = new String[response.length()];
                            String role[] = new String[response.length()];

                            for (int i = 0; i < response.length(); i++) {
                                //System.out.println(i);
                                id[i] = (String) response.getJSONObject(i).get("id").toString();
                                name[i] =
                                        (String) response.getJSONObject(i).get("firstname")
                                                .toString();
                                lastName[i] =
                                        (String) response.getJSONObject(i).get("lastname")
                                                .toString();
                                role[i] =
                                        (String) response.getJSONObject(i).get("roles").toString();
                            }

                            id1 = id;
                            name1 = name;
                            lastName1 = lastName;
                            role1 = role;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mainActivity5_adapter = new MainActivity5_adapter(MainActivity5.this
                                , id1, name1, lastName1, role1);
                        listView.setAdapter(mainActivity5_adapter);
                        //System.out.println(array[0][5]);
                        //System.out.println(array[1][2]);
                        //System.out.println(array[2][3]);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                //System.out.println("error");
                System.out.println(error);
            }
        }) {
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
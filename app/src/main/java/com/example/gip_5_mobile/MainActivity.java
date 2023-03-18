package com.example.gip_5_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

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
        button1.setOnClickListener(V -> {
            // check password hier
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(intent);
        });
    }

    public String getEditText1() {
        return editText1.getText().toString();
    }

    public String getEditText2() {
        return editText2.getText().toString();
    }
}
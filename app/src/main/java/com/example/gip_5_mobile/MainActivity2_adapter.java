package com.example.gip_5_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MainActivity2_adapter extends ArrayAdapter<String> {
    Context context;

    String id [];
    String name [];
    String type [];
    String extraInfo [];
    String price [];
    String amount [];
    String modelnr [];

    public MainActivity2_adapter(@NonNull Context context, String id [], String name [],
                                 String type [], String price []){
        super(context,R.layout.activity_main2_list,R.id.list,id);
        this.context = context;
        this.id = id;
        this.name = name;
        this.type = type;
        this.extraInfo = extraInfo;
        this.price = price;
        this.amount = amount;
        this.modelnr = modelnr;
    }

    @NonNull
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.activity_main2_list,parent,false);

        TextView textView1 = row.findViewById(R.id.text_1);
        TextView textView2 = row.findViewById(R.id.text_2);
        TextView textView3 = row.findViewById(R.id.text_3);
        TextView textView4 = row.findViewById(R.id.text_4);

        textView1.setText(id[position]);
        textView2.setText(name[position]);
        textView3.setText(type[position]);
        textView4.setText("€ "+price[position]);

        return row;
    }
}

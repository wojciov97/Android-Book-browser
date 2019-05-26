package com.example.projekt;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    String path;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portrait);

       ListView list = (ListView) findViewById(R.id.listaautorow);
        ArrayList <String> authorList = new ArrayList <>();
        ArrayList <String> titleList  = new ArrayList<>();

        BazaDanych db = new BazaDanych(this);

       // db.addBook("Symfonia C++", "Jerzy Grebosz", "@drawable/symfonia");
          //  db.addBook("Symfonia","Jurek");
       Cursor buff = db.getAll();
        while (buff.moveToNext()){
            int nr = buff.getInt(0);
             titleList.add(buff.getString(1));
             authorList.add(buff.getString(2));
            path = buff.getString(3);
        }


        ArrayAdapter adapterTitle = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList);
        list.setAdapter(adapterTitle);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              setContentView(R.layout.konkretna_pozycja);
              imgView = (ImageView) findViewById(R.id.imageView);
            int imageres =getResources().getIdentifier(path,null,getPackageName());
                Drawable res =getResources().getDrawable(imageres);
                imgView.setImageDrawable(res);


            }
        });


    }
}

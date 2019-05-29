package com.example.projekt;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    BazaDanych db = new BazaDanych(this);
    ArrayList <String> titleList  = new ArrayList<>();
    ArrayList <String> pathList  = new ArrayList<>();
    ArrayList <String> authorList  = new ArrayList<>();
    ArrayList <String> pubHouseList  = new ArrayList<>();
    ListView list;
    public void openAddBook() {
        Intent intent = new Intent(this, Add_Book.class);
        startActivity(intent);
    }

    public void openPossition(int position) {
        Intent intent = new Intent(this, ChosenPossition.class);
        intent.putExtra("pozycja", position);
        intent.putExtra("titleList", titleList);
        intent.putExtra("pathList", pathList);
        intent.putExtra("authorList", authorList);
        intent.putExtra("pubHouseList", pubHouseList);
        startActivity(intent);
    }
    public void showAll(ListView list){
        titleList.clear();
        authorList.clear();
        pathList.clear();
        pubHouseList.clear();
        Cursor buff = db.getAll();
        while (buff.moveToNext()) {
            titleList.add(buff.getString(1));
            authorList.add(buff.getString(2));
            pathList.add(buff.getString(4));
            pubHouseList.add(buff.getString(3));

        }
        ArrayAdapter adapterTitle = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList);
        list.setAdapter(adapterTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portrait);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddBook();
            }
        });

        list = (ListView) findViewById(R.id.listaautorow);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPossition(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAll(list);
        Toast.makeText(getApplicationContext(),pathList.get(0),Toast.LENGTH_LONG).show();
    }
}

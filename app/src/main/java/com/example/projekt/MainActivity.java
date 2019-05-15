package com.example.projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portrait);

        String [ ] autorzy = {"Henryk Sienkiewicz", "Juliusz Słowacki","Wisława Szymborska","Stefan Żeromski","Adam Misckiewicz","Bolesław Prus"};
        ListView lista = (ListView) findViewById(R.id.listaautorow);
        ArrayAdapter adapter_listy = new ArrayAdapter(this, android.R.layout.simple_list_item_1,autorzy);
        lista.setAdapter(adapter_listy);




    }
}

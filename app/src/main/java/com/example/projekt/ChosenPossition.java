package com.example.projekt;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChosenPossition extends AppCompatActivity {

    ImageView imgView;
    TextView txtAutor, txtTytul, txtWydawnictwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konkretna_pozycja);

        ArrayList <String> titleList  = new ArrayList<>();
        ArrayList <String> pathList  = new ArrayList<>();
        ArrayList <String> authorList  = new ArrayList<>();
        ArrayList <String> pubHouseList  = new ArrayList<>();

             int position = getIntent().getIntExtra("pozycja", -1) ;
            titleList = getIntent().getStringArrayListExtra("titleList");
            pathList = getIntent().getStringArrayListExtra("pathList");
            authorList = getIntent().getStringArrayListExtra("authorList");
            pubHouseList = getIntent().getStringArrayListExtra("pubHouseList");

                imgView = (ImageView) findViewById(R.id.imageView);
                int imageres =getResources().getIdentifier(pathList.get(position),null,getPackageName());
                Drawable res =getResources().getDrawable(imageres);
                imgView.setImageDrawable(res);
                txtAutor = (TextView) findViewById(R.id.txtAutor);
                txtTytul = (TextView) findViewById(R.id.txtTytul);
                txtWydawnictwo = (TextView) findViewById(R.id.txtWydawnictwo);
                txtAutor.setText(authorList.get(position));
                txtTytul.setText(titleList.get(position));
                txtWydawnictwo.setText(pubHouseList.get(position));

    }
}

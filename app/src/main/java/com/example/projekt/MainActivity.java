package com.example.projekt;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

    int nr;
    Button btnAdd, btnConfirmAdd ;
    String author, title, pubhouse, path, path1;
    ImageView imgView;
    EditText txtAuthor, txtTitle, txtPubHouse, txtPath;
    TextView txtAutor, txtTytul, txtWydawnictwo;
    BazaDanych db = new BazaDanych(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portrait);

       ListView list = (ListView) findViewById(R.id.listaautorow);
        ArrayList <String> authorList = new ArrayList <>();
        final ArrayList <String> titleList  = new ArrayList<>();
        final ArrayList <String> pathList = new ArrayList<>();



       Cursor buff = db.getAll();
        while (buff.moveToNext()){
            nr = buff.getInt(0);
             titleList.add(buff.getString(1));
             authorList.add(buff.getString(2));
             pubhouse = buff.getString(3);
            pathList.add(buff.getString(4));

            Toast.makeText(getApplicationContext(),Integer.toString(nr), Toast.LENGTH_LONG).show();
        }


        ArrayAdapter adapterTitle = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList);
        list.setAdapter(adapterTitle);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              setContentView(R.layout.konkretna_pozycja);
              imgView = (ImageView) findViewById(R.id.imageView);
            int imageres =getResources().getIdentifier(pathList.get(0),null,getPackageName());
                Drawable res =getResources().getDrawable(imageres);
                imgView.setImageDrawable(res);
                txtAutor = (TextView) findViewById(R.id.txtAutor);
                txtTytul = (TextView) findViewById(R.id.txtTytul);
                txtWydawnictwo = (TextView) findViewById(R.id.txtWydawnictwo);
//                titleList.get(titleList.size())

            }
        });
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.addbook);
                txtAuthor = (EditText) findViewById(R.id.txtAuthor);
                txtTitle = (EditText) findViewById(R.id.txtTitle);
                txtPubHouse = (EditText) findViewById(R.id.txtPubHouse);
                txtPath = (EditText) findViewById(R.id.txtPath1);



                btnConfirmAdd = (Button) findViewById(R.id.btnConfirmAdd);
                btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        author = txtAuthor.getText().toString();
                        title  = txtTitle.getText().toString();
                        pubhouse= txtPubHouse.getText().toString();
                        path1 = txtPath.getText().toString();

                        if (TextUtils.isEmpty(author) || TextUtils.isEmpty(title) || TextUtils.isEmpty(pubhouse) || TextUtils.isEmpty(path1) ){
                            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_LONG).show();
                            return;
                        }
                       else {
                            db.addBook(title, author, pubhouse, path1);
                            Toast.makeText(getApplicationContext(),"Dodano pomyślnie", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });



    }
}

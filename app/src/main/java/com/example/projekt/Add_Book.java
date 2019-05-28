package com.example.projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Book extends AppCompatActivity {

    Button  btnConfirmAdd ;
    String author, title, pubhouse, path, path1;
    EditText txtAuthor, txtTitle, txtPubHouse, txtPath;
    BazaDanych db = new BazaDanych(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                            txtAuthor.setText("");
                            txtPath.setText("");
                            txtPubHouse.setText("");
                            txtTitle.setText("");

                        }

                    }
                });

            }




    }


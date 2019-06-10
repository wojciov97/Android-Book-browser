package com.example.projekt;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_Book extends AppCompatActivity {

    private int CAMERA_PERMISSION_CODE = 1,GALLERY_PERMISSION_CODE = 2, requestCameraCode =1, requestGalleryCode = 2;
    private String []  permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    Button btnPhoto, btnConfirmAdd,btnGallery ;
    String author, title, pubhouse, path;
    EditText txtAuthor, txtTitle, txtPubHouse, txtPath;
    BazaDanych db = new BazaDanych(this);
    ImageView imgPodglad;
    Uri imageURI;



    private void requestNewPermisson(){
        if( ContextCompat.checkSelfPermission(this, permission[0]) != PackageManager.PERMISSION_GRANTED)
        ActivityCompat.requestPermissions(this , permission , GALLERY_PERMISSION_CODE);
        if (ContextCompat.checkSelfPermission(this, permission[1]) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, permission, requestCameraCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbook);

        btnPhoto = (Button) findViewById(R.id.btnPhoto);
        imgPodglad = (ImageView) findViewById(R.id.imgPodglad);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Add_Book.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(Add_Book.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                   path = takePhoto();
                }
                else{
                    requestNewPermisson();

                }


            }
        });


                txtAuthor = (EditText) findViewById(R.id.txtAuthor);
                txtTitle = (EditText) findViewById(R.id.txtTitle);
                txtPubHouse = (EditText) findViewById(R.id.txtPubHouse);
               // txtPath = (EditText) findViewById(R.id.txtPath1);

                btnConfirmAdd = (Button) findViewById(R.id.btnConfirmAdd);
                btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        author = txtAuthor.getText().toString();
                        title  = txtTitle.getText().toString();
                        pubhouse= txtPubHouse.getText().toString();
                       // path1 = txtPath.getText().toString();

                        if (TextUtils.isEmpty(author) || TextUtils.isEmpty(title) || TextUtils.isEmpty(pubhouse) || path == null ){
                            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
//                            Toast.makeText(getApplicationContext(),path, Toast.LENGTH_SHORT).show();
                            db.addBook(title, author, pubhouse, path);
                            Toast.makeText(getApplicationContext(),"Dodano pomyślnie", Toast.LENGTH_SHORT).show();
                            txtAuthor.setText("");
                           // txtPath.setText("");
                            txtPubHouse.setText("");
                            txtTitle.setText("");
                            path = null;

                        }

                    }
                });


                btnGallery = (Button) findViewById(R.id.btnGallery);
                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });

            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == requestCameraCode) {
                File image = new File(path);
                if(image.exists()) {

                    Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
                    imgPodglad.setImageBitmap(bitmap);

                }

                else
                    Toast.makeText(getApplicationContext(),"NIE ISTNIEJE",Toast.LENGTH_LONG).show();
            }
            if (requestCode == requestGalleryCode){
                imageURI = data.getData();
                path = getPathFromURI(imageURI);
                imgPodglad.setImageURI(imageURI);
            }
    }

    private  String takePhoto(){

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File okladka = null;
        Calendar c = Calendar.getInstance();
        String nazwapliku = "okladka_"+c.get(Calendar.YEAR)+"_"+c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"
                +c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND);
        String rozszerzenie = ".jpg";
        File katalog = new File(Environment.getExternalStorageDirectory()+"/Book_Browser");
        if (!katalog.exists()){
            katalog.mkdir();
        }
        try{

            okladka =File.createTempFile(nazwapliku,rozszerzenie,katalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(okladka));
        startActivityForResult(intent,requestCameraCode);
        String sciezka = okladka.getAbsolutePath();

            return sciezka;
            }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, requestGalleryCode);



    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}


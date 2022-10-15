package com.example.spacechat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class Perfil extends AppCompatActivity {

    private Button btnSair, btnAlterarFotoPerfil;
    private ImageView imgPerfil;
    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btnSair = findViewById(R.id.btnLogOut);
        imgPerfil = findViewById(R.id.perfil_img);
        btnAlterarFotoPerfil = findViewById(R.id.btnAlterarFotoPerfil);


        btnAlterarFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlterarFotodePerfil();
            }
        });


        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Perfil.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){
            imagePath = data.getData();
            getImageInImageView();
        }
    }

    private void getImageInImageView() {

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
        }catch (IOException e){
            e.printStackTrace();
        }
        imgPerfil.setImageBitmap(bitmap);
    }
    public void AlterarFotodePerfil(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Alterando...");
        progressDialog.show();
    }
}
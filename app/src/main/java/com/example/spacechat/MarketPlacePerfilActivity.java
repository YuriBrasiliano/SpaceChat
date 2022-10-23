package com.example.spacechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MarketPlacePerfilActivity extends AppCompatActivity {

    private ImageView imgPerfil;
    private TextView  username, novoProduto, txtMeusProdutos;
    StorageReference storageReference;
    private Button btnPesquisarProdutos;
    DatabaseReference reference;
    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place_perfil);
        imgPerfil = findViewById(R.id.perfil_img);
        menu = findViewById(R.id.bottom_navigation2);
        txtMeusProdutos = findViewById(R.id.txtMeusProdutos);
        novoProduto = findViewById(R.id.txtnovoProduto);
        btnPesquisarProdutos = findViewById(R.id.btnPesquisarProdutos);
        username = findViewById(R.id.txtUserName);
        reference = FirebaseDatabase.getInstance().getReference("user");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String UserName = String.valueOf(dataSnapshot.child("username").getValue());
                        username.setText("\nUsername\n"+UserName);
                    }

                }
            }
        });
        novoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarketPlacePerfilActivity.this, CadastroProduto.class));
            }
        });
        txtMeusProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarketPlacePerfilActivity.this, MeusProdutosActivity.class));
            }
        });


        String imageID = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());

        storageReference = FirebaseStorage.getInstance().getReference("images/"+imageID);

        try {
            File localfile = File.createTempFile("tempfile",".jpeg");
            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    imgPerfil.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.mnPerfil):
                        startActivity(new Intent(MarketPlacePerfilActivity.this, Perfil.class));break;
                    case (R.id.mnChats):
                        startActivity(new Intent(MarketPlacePerfilActivity.this, AmigosActivity.class));break;
                }
                return true;
            }});
        btnPesquisarProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarketPlacePerfilActivity.this, MarketPlaceActivity.class));
            }
        });

    }
}
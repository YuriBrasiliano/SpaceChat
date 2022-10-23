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
import android.widget.Toast;

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

public class ViewProduto extends AppCompatActivity {

    private BottomNavigationView menu;
    TextView valorProduto, descricaoProduto, txtNomeProduto;
    String nomeProduto, produtoID;
    DatabaseReference reference;
    ImageView viewProdutoImg;
    Button msgVendedor;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produto);
        menu = findViewById(R.id.bottom_navigation2);
        valorProduto = findViewById(R.id.ValorProdutoView);
        txtNomeProduto = findViewById(R.id.NomeProdutoView);
        viewProdutoImg = findViewById(R.id.imgProdutoView);
        msgVendedor = findViewById(R.id.btnmsgVendedor);
        nomeProduto = getIntent().getStringExtra("ProdutoNome");
        descricaoProduto = findViewById(R.id.DescricaoProdutoView);
        txtNomeProduto.setText(nomeProduto);

        produtoID = nomeProduto+FirebaseAuth.getInstance().getCurrentUser().getUid();

        msgVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ViewProduto.this, AmigosActivity.class));
            }
        });

        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.mnMarketPlace):
                        startActivity(new Intent(ViewProduto.this, MarketPlacePerfilActivity.class));break;
                    case (R.id.mnChats):
                        startActivity(new Intent(ViewProduto.this, AmigosActivity.class));break;
                    case (R.id.mnPerfil):
                        startActivity(new Intent(ViewProduto.this, Perfil.class));break;
                }
                return true;
            }});
        reference = FirebaseDatabase.getInstance().getReference("produto");
        reference.child(produtoID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        descricaoProduto.setText(String.valueOf(dataSnapshot.child("descricaoProduto").getValue()));
                        valorProduto.setText(String.valueOf(dataSnapshot.child("precoProduto").getValue()));
                    }

                }
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("ImagensProdutos/"+produtoID);

        try {
            File localfile = File.createTempFile("tempfile",".jpeg");
            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    viewProdutoImg.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
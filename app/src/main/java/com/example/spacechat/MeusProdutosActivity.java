package com.example.spacechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MeusProdutosActivity extends AppCompatActivity {
    private BottomNavigationView mns;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Produto produto;
    ProdutosAdapter.OnProdutosClickListener onProdutosClickListener;
    private ProdutosAdapter produtosAdapter;
    private ArrayList<Produto> produtos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        mns = findViewById(R.id.bottom_navigation3);
        produtos = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerProduto);

        mns.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.mnChats):
                        startActivity(new Intent(MeusProdutosActivity.this, AmigosActivity.class));break;
                    case (R.id.mnPerfil):
                        startActivity(new Intent(MeusProdutosActivity.this, Perfil.class));break;
                    case (R.id.mnMarketPlace):
                        startActivity(new Intent(MeusProdutosActivity.this, MarketPlacePerfilActivity.class));break;

                }
                return true;
            }
        });
        onProdutosClickListener = new ProdutosAdapter.OnProdutosClickListener() {
            @Override
            public void onProdutosClicked(int position) {
                startActivity(new Intent(MeusProdutosActivity.this,ViewProduto.class)
                        .putExtra("ProdutoNome", produtos.get(position).getNomeProduto())
                );
            }
        };

        getProdutos();
    }

    private void getProdutos(){
        produtos.clear();
        FirebaseDatabase.getInstance().getReference("produto").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    produto = dataSnapshot.getValue(Produto.class);
                    if (produto.getIdProduto().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    produtos.add(dataSnapshot.getValue(Produto.class));}
                    else{}

                    produtosAdapter = new ProdutosAdapter(produtos, MeusProdutosActivity.this,onProdutosClickListener);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MeusProdutosActivity.this));
                    recyclerView.setAdapter(produtosAdapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

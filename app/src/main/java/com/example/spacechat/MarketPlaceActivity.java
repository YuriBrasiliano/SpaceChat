package com.example.spacechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
public class MarketPlaceActivity extends AppCompatActivity {
    private BottomNavigationView mns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        mns = findViewById(R.id.bottom_navigation3);
        mns.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.mnChats):
                        startActivity(new Intent(MarketPlaceActivity.this, AmigosActivity.class));break;
                    case (R.id.mnPerfil):
                        startActivity(new Intent(MarketPlaceActivity.this, Perfil.class));break;
                }
                return true;
            }
        });
    }
}
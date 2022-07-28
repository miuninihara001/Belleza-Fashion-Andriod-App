package com.example.bellezafashion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button shop,designer,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        shop = (Button) findViewById(R.id.hbtn1);
        designer = (Button) findViewById(R.id.hbtn2);
        profile = (Button) findViewById(R.id.hbtn3);

         profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityProfile();

            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShop();
            }
        });

    }

    private void openShop() {
        Intent intent = new Intent(HomeActivity.this, ProductList.class);
        startActivity(intent);
    }

    private void openActivityProfile() {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
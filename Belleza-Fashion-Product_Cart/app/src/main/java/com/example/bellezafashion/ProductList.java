package com.example.bellezafashion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {
    private static final String TAG = "ProductList";
    Button viewCart;
    RecyclerView recyclerView;
    DatabaseReference database;
    ProductAdapter proAdapter;
    ArrayList<Product> list;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Log.d(TAG,"OnCreate: started");

        recyclerView = findViewById(R.id.product_list);
        viewCart = findViewById(R.id.vcart);
        database = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        proAdapter = new ProductAdapter(this,list);
        recyclerView.setAdapter(proAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //onDataChange that fetch all data inside the product
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    list.add(product);
                }
                proAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCart();
            }
        });
    }

    private void ViewCart() {
        Intent intent = new Intent(ProductList.this, CartActivity.class);
        startActivity(intent);
    }
}
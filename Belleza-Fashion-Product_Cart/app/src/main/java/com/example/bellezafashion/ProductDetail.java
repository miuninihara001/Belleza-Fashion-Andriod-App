package com.example.bellezafashion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.Prevalent.Prevalent;
import com.example.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class ProductDetail extends AppCompatActivity {

    private TextView in_product_detail_name,in_product_detail_price,in_product_detail_designer,in_product_detail_description;
    private ImageView in_product_image_detail;
    private Button in_product_add_to_cart;
    ElegantNumberButton in_number_BTN;
    private Product product;
    private String productID="";
    String saveCurrentDate,saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productID = getIntent().getStringExtra("pid");

        in_product_detail_name = (TextView)findViewById(R.id.in_product_detail_name);
        in_product_detail_price = (TextView)findViewById(R.id.in_product_detail_price);
        in_product_detail_designer = (TextView)findViewById(R.id.in_product_detail_designer);
        in_product_detail_description = (TextView)findViewById(R.id.in_product_detail_description);
        in_product_image_detail=(ImageView)findViewById(R.id.in_product_image_detail);
        in_product_add_to_cart=(Button)findViewById(R.id.in_product_add_to_cart);
        //in_product_detail_pid=(TextView)findViewById(R.id.in_product_detail_pid);
        in_number_BTN = (ElegantNumberButton)findViewById(R.id.in_number_BTN);


        getProductInfo(productID);

        in_product_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });

    }

    private void addingToCartList() {

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("name",in_product_detail_name.getText().toString());
        cartMap.put("price",in_product_detail_price.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",in_number_BTN.getNumber());

        cartListRef.child("UserView").child(Prevalent.CurrentOnlineUser.getUsername())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListRef.child("AdminView").child(Prevalent.CurrentOnlineUser.getUsername())
                                    .child("Products").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ProductDetail.this,"Added to the Cart List",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ProductDetail.this, ProductList.class);
                                            startActivity(intent);
                                        }
                                    });
                        }
                    }
                });

    }

    private void getProductInfo(String productID) {

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Products");


        dbRef.child(productID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){

                    Product product = snapshot.getValue(Product.class);
                    //in_product_detail_pid.setText(product.getPid());
                    in_product_detail_name.setText(product.getName());
                    in_product_detail_price.setText(product.getPrice());
                    in_product_detail_designer.setText(product.getDesigner());
                    in_product_detail_description.setText(product.getDescription());
                    Picasso.get().load(product.getImage()).into(in_product_image_detail);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
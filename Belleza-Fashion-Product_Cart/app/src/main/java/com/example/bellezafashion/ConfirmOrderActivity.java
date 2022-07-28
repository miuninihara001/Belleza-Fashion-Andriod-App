package com.example.bellezafashion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Button cash,card;
    private TextView tott;

    private String tot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        cash = (Button) findViewById(R.id.cash);
        card = (Button) findViewById(R.id.card);
        tott = (TextView) findViewById(R.id.total);

        tot = getIntent().getStringExtra("Total Price");
        tott.setText("Total Price = LKR"+ tot);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this,PaymentMethodActivity.class);
                startActivity(intent);
            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmOrderActivity.this);
                builder.setMessage("Order Placed Successfully!");
                builder.show();
            }
        });
    }
}
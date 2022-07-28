package com.example.bellezafashion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fevziomurtekin.payview.Payview;

public class PaymentMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

            // on below line we are creating a variable
            // for our pay view and initializing it.
            Payview payview = findViewById(R.id.payview);

            // on below line we are setting pay on listener for our card.
            payview.setPayOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // after clicking on pay we are displaying toast message as card added.
                    Toast.makeText(PaymentMethodActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

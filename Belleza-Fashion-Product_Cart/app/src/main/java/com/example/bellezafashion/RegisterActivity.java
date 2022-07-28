package com.example.bellezafashion;

//import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText name,username,password,email;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.btn4);
        name = (EditText) findViewById(R.id.rtxt2);
        username = (EditText) findViewById(R.id.rtxt3);
        password = (EditText) findViewById(R.id.rtxt4);
        email = (EditText) findViewById(R.id.rtxt5);
        loadingBar = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String Name = name.getText().toString();
        String un = username.getText().toString();
        String pw = password.getText().toString();
        String mail = email.getText().toString();
        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this,"Please Enter Your Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(un)){
            Toast.makeText(this,"Please Enter Your UserName", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pw)){
            Toast.makeText(this,"Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            isValidEmail(mail);
            ValidateUsername(Name,un,pw,mail);
        }
    }


    public void isValidEmail(String mail) {
       if (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
           Toast.makeText(this,"Email verrified",Toast.LENGTH_SHORT).show();
       }
       else{
           loadingBar.dismiss();
           Toast.makeText(this,"Invalid Email",Toast.LENGTH_SHORT).show();
       }
    }


    private void ValidateUsername(String name, String un, String pw,String mail) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(un).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("username",un);
                    userdataMap.put("password",pw);
                    userdataMap.put("name", name);
                    userdataMap.put("email", mail);

                    RootRef.child("Users").child(un).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Your Account has been Created", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Network Error, Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(RegisterActivity.this,"This "+ un + "already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                   // Toast.makeText(RegisterActivity.this,"Please try again with a another username", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.bellezafashion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private EditText name,username,email;
    private Button save,logout;
    private TextView changeProf;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = (ImageView) findViewById(R.id.img);
        save = (Button) findViewById(R.id.ptxt);
        logout = (Button) findViewById(R.id.pbtn);
        username = (EditText) findViewById(R.id.ptxt1);
        name = (EditText) findViewById(R.id.ptxt2);
        email = (EditText) findViewById(R.id.ptxt3);
        changeProf = (TextView) findViewById(R.id.textView);

        userInfoDisplay(username,name,email);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void userInfoDisplay(EditText username, EditText name, EditText email) {
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(Prevalent.CurrentOnlineUser.getUsername());
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("username").exists()){
                        name.setText(snapshot.child("name").getValue().toString());
                        username.setText(snapshot.child("username").getValue().toString());
                        email.setText(snapshot.child("email").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void update(){
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("name",name.getText().toString());
        userMap.put("username",username.getText().toString());
        userMap.put("email",email.getText().toString());

        UserRef.child(Prevalent.CurrentOnlineUser.getUsername()).updateChildren(userMap);

        Toast.makeText(this,"Updated successfully",Toast.LENGTH_SHORT).show();
    }
}



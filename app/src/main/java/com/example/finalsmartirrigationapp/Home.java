package com.example.finalsmartirrigationapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText Username, Password;

    ConstantMethods constantMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        constantMethods = new ConstantMethods();
        constantMethods.SweetAlertDialog(this);

    }

    public void SignIn(View view) {


        constantMethods.pDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Username.getText().toString(), Password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        Intent intent = new Intent(Home.this, MainActivity.class);
                        intent.putExtra("currentuser", currentuser);
                        startActivity(intent);
                        constantMethods.pDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                constantMethods.pDialog.dismiss();
                Toast.makeText(Home.this, "faild to login", Toast.LENGTH_SHORT).show();

            }
        });


    }
}

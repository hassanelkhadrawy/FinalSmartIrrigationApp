package com.example.finalsmartirrigationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.john.waveview.WaveView;

public class Humtity_Control extends AppCompatActivity {
    Button On, Off;
    WaveView waveView;
    TextView Water_Degree;
    String userId;
    Model model;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humtity__control);
        On=findViewById(R.id.open);
        Off=findViewById(R.id.close);
                Water_Degree = findViewById(R.id.water_degree);
                waveView = (WaveView) findViewById(R.id.wave_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users_Status");
        savedInstanceState = getIntent().getExtras();
        model = new Model();

        userId = savedInstanceState.getString("currentuser");


        On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(userId).child("Pump_Control").setValue(1);

            }
        });

        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(userId).child("Pump_Control").setValue(0);
            }
        });
        GetData();

    }

    private void GetData() {
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model user = dataSnapshot.getValue(Model.class);
                waveView.setProgress(user.Punmp_Status);
                Water_Degree.setText(" Humidity degree:\n" + user.Punmp_Status);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(Humtity_Control.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

    }



}

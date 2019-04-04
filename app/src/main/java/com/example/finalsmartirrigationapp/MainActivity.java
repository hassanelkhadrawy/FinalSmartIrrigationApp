package com.example.finalsmartirrigationapp;

import android.support.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Button On, Off;
    private WaveView waveView;
    Model model;
    TextView Water_Degree;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        On = findViewById(R.id.open);
        Off = findViewById(R.id.close);
        Water_Degree = findViewById(R.id.water_degree);
        waveView = (WaveView) findViewById(R.id.wave_view);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users_Status");
        model = new Model();
        savedInstanceState = getIntent().getExtras();

        userId = savedInstanceState.getString("currentuser");


        GetData();

        On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.setVisibility(View.VISIBLE);

                model = new Model("hassan", 55, 43);
                databaseReference.child(userId).setValue(model);

            }
        });

        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                waveView.setVisibility(View.GONE);

            }
        });

    }

    private void GetData() {

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Model user = dataSnapshot.getValue(Model.class);

                waveView.setProgress(user.Punmp_Status);
                Water_Degree.setText(" درجه الرطوبه:" + "\n" + user.Punmp_Status);

                Toast.makeText(MainActivity.this, "heat : " + user.Punmp_Status, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

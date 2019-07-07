package com.example.finalsmartirrigationapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.john.waveview.WaveView;

public class Control_Heat extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Button Increase, Decrease;
    private WaveView waveView;
    Model model;
    TextView Water_Degree,Heat_Degree;
    String userId;
    private SpeedometerGauge speedometer;
    int Counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_heat);


        Increase = findViewById(R.id.increase);
        Decrease = findViewById(R.id.decrease);

        speedometer = findViewById(R.id.speedometer);
        Heat_Degree=findViewById(R.id.heat_degree);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users_Status");
        model = new Model();
        savedInstanceState = getIntent().getExtras();

        userId = savedInstanceState.getString("currentuser");


        GetData();

        Increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                model = new Model("hassan", 55, 43);
                databaseReference.child(userId).child("Heat_Control").setValue(1);

            }
        });

        Decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Counter--;
                databaseReference.child(userId).child("Heat_Control").setValue(0);
            }
        });

        speedometer.setLabelConverter(new SpeedometerGauge.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });

        // configure value range and ticks
        speedometer.setMaxSpeed(300);
        speedometer.setMajorTickStep(30);
        speedometer.setMinorTicks(2);

        // Configure value range colors
        speedometer.addColoredRange(30, 140, Color.GREEN);
        speedometer.addColoredRange(140, 180, Color.YELLOW);
        speedometer.addColoredRange(180, 400, Color.RED);


    }

    private void GetData() {

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model user = dataSnapshot.getValue(Model.class);
               // waveView.setProgress(user.Punmp_Status);
                Heat_Degree.setText(user.Heat_degree+"C");
                Counter=user.Heat_degree;
//                Water_Degree.setText(" درجه الرطوبه:" + "\n" + user.Punmp_Status);
                speedometer.setSpeed(user.Heat_degree);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(Control_Heat.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

    }




}

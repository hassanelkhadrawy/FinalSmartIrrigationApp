package com.example.finalsmartirrigationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.john.waveview.WaveView;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Button On, Off;
    private WaveView waveView;
    Model model;
    TextView Water_Degree, Heat_Degree;
    String userId;
    private SpeedometerGauge speedometer;

    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<>();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users_Status");
        model = new Model();
        savedInstanceState = getIntent().getExtras();
        userId = savedInstanceState.getString("currentuser");
        GetData();
        barChart = findViewById(R.id.barchart);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.humtity) {
            Intent intent = new Intent(this, Humtity_Control.class);
            intent.putExtra("currentuser", userId);
            startActivity(intent);

        } else if (id == R.id.heat) {
            Intent intent = new Intent(this, Control_Heat.class);
            intent.putExtra("currentuser", userId);
            startActivity(intent);

        } else if (id == R.id.light_m) {
            Intent intent = new Intent(this, Light.class);
            intent.putExtra("currentuser", userId);
            startActivity(intent);
        } else if (id == R.id.about) {

        } else if (id == R.id.contact_us) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void GetData() {

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model user = dataSnapshot.getValue(Model.class);
                BARENTRY.clear();
                AddValuesToBarEntryLabels();
                BARENTRY.add(new BarEntry(user.Punmp_Status, 0));
                BARENTRY.add(new BarEntry(user.Heat_degree, 1));
                BARENTRY.add(new BarEntry(user.Semad_Status, 2));
                BARENTRY.add(new BarEntry(user.Air_Status, 3));
                Bardataset = new BarDataSet(BARENTRY, "montering");
                BARDATA = new BarData(BarEntryLabels, Bardataset);
                Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                barChart.setData(BARDATA);
                barChart.animateY(500);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(Home.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void AddValuesToBarEntryLabels() {
        BarEntryLabels.clear();
        BarEntryLabels.add("Humidity");
        BarEntryLabels.add("Heat");
        BarEntryLabels.add("Light");
        BarEntryLabels.add("Wind");


    }


}

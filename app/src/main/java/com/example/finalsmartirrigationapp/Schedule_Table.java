package com.example.finalsmartirrigationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Schedule_Table extends AppCompatActivity {
TableLayout yourRootLayout;
    String id;
    static int id2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__table);
        yourRootLayout=findViewById(R.id.table);
        actioCells();
    }

    //================== Method to get cell id which user has clicked ================
    public void actioCells() {
        int count = yourRootLayout.getChildCount();
        for (int i = 2; i < count; i++) {
            View v = yourRootLayout.getChildAt(i);
            if (v instanceof TableRow) {
                TableRow row = (TableRow) v;
                int rowCount = row.getChildCount();
                for (int r = 1; r < rowCount; r++) {
                    View v2 = row.getChildAt(r);
                    if (v2 instanceof TextView) {
                        final TextView tv = (TextView) v2;
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                id = v.getResources().getResourceName(v.getId());

                                id2 = v.getId();
                                Toast.makeText(Schedule_Table.this, ""+tv.getText().toString(), Toast.LENGTH_SHORT).show();

                                registerForContextMenu(tv);
                                openContextMenu(tv);

                            }
                        });
                    }
                }
            }


        }

    }

}

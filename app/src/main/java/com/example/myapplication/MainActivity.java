package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.GetLogsActivity;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CardView buttonGetLogs = findViewById(R.id.getlogs);


        buttonGetLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GetLogsActivity.class);
                startActivity(intent);
            }
        });

        CardView buttonGetApps = findViewById(R.id.getapps);


        buttonGetApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GetAppsActivity.class);
                startActivity(intent);
            }
        });

        CardView buttonSendLogs = findViewById(R.id.sendlogs);


        buttonSendLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SendLogsActivity.class);
                startActivity(intent);
            }
        });

        CardView buttonAddNewApp = findViewById(R.id.addapplication);


        buttonAddNewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddNewApplicationActivity.class);
                startActivity(intent);
            }
        });


    }
}

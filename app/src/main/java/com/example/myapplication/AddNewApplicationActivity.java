package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddNewApplicationActivity extends AppCompatActivity {

    private EditText appnameEditText;
    private EditText directorynameEditText;
    private EditText addresEditText;
    private EditText iisAppNameEditText;
    private EditText iisAppPoolNameEditText;
    private EditText pgsqlConnectionStringEditText;
    private Button addApplicationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_application);

        // Inicjalizacja widoków
        appnameEditText = findViewById(R.id.appnameEditText);
        directorynameEditText = findViewById(R.id.directorynameEditText);
        addresEditText = findViewById(R.id.addresEditText);
        iisAppNameEditText = findViewById(R.id.iisAppNameEditText);
        iisAppPoolNameEditText = findViewById(R.id.iisAppPoolNameEditText);
        pgsqlConnectionStringEditText = findViewById(R.id.pgsqlConnectionStringEditText);
        addApplicationButton = findViewById(R.id.addApplicationButton);

        // Obsługa kliknięcia przycisku
        addApplicationButton.setOnClickListener(v -> {
            // Pobierz dane z formularza
            String appname = appnameEditText.getText().toString();
            String directoryname = directorynameEditText.getText().toString();
            String addres = addresEditText.getText().toString();
            String iisAppName = iisAppNameEditText.getText().toString();
            String iisAppPoolName = iisAppPoolNameEditText.getText().toString();
            String pgsqlConnectionString = pgsqlConnectionStringEditText.getText().toString();

            // Walidacja pól (opcjonalnie)
            if (appname.isEmpty() || directoryname.isEmpty() || addres.isEmpty() || iisAppName.isEmpty() ||
                    iisAppPoolName.isEmpty() || pgsqlConnectionString.isEmpty()) {
                Toast.makeText(this, "Wszystkie pola muszą być wypełnione", Toast.LENGTH_SHORT).show();
                return;
            }

            // Wykonaj zapytanie POST
            new Thread(() -> {
                try {
                    sendPostRequest(appname, directoryname, addres, iisAppName, iisAppPoolName, pgsqlConnectionString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    private void sendPostRequest(String appname, String directoryname, String addres, String iisAppName,
                                 String iisAppPoolName, String pgsqlConnectionString) throws IOException {


        String urlString = "https://erponeupdator.masterdev.pl/Masterdev_Updater/AddNewApplication" +
                "?appName=" + appname +
                "&directoryname=" + directoryname +
                "&addres=" + addres +
                "&iisAppName=" + iisAppName +
                "&iisAppPoolName=" + iisAppPoolName +
                "&pgsqlConnectionString=" + pgsqlConnectionString;


        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Accept", "*/*");
        urlConnection.setRequestProperty("x-api-key", "x");
        urlConnection.setDoOutput(true);


        OutputStream os = urlConnection.getOutputStream();
        os.flush();
        os.close();


        int responseCode = urlConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            runOnUiThread(() -> Toast.makeText(this, "Aplikacja dodana pomyślnie", Toast.LENGTH_SHORT).show());
        } else {

            runOnUiThread(() -> Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show());
        }
    }
}

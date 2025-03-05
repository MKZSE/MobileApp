package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.GetAppModel;
import java.util.ArrayList;
import java.util.List;

import api.ApiRepository;

public class SendLogsActivity extends AppCompatActivity {

    private Spinner appSpinner;
    private EditText messageInput;
    private Button sendButton;
    private ApiRepository apiRepository;
    private List<GetAppModel> appList = new ArrayList<>(); // Inicjalizacja pustej listy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_logs);

        appSpinner = findViewById(R.id.appSpinner);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.buttonSendLogs);
        apiRepository = new ApiRepository();

        // Pobierz aplikacje z API
        fetchApps();

        // Obsługa przycisku wysyłania logów
        sendButton.setOnClickListener(v -> sendLogs());
    }

    // Pobieranie listy aplikacji z API
    private void fetchApps() {
        apiRepository.getApps(new ApiRepository.ApiAppsCallback() {
            @Override
            public void onSuccess(List<GetAppModel> apps) {
                appList = apps;
                updateSpinner();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(SendLogsActivity.this, "Błąd pobierania aplikacji: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Aktualizacja Spinnera po pobraniu aplikacji
    private void updateSpinner() {
        List<String> appNames = new ArrayList<>();
        for (GetAppModel app : appList) {
            appNames.add(app.getId() + " - " + app.getApp_Name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, appNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appSpinner.setAdapter(adapter);
    }

    // Wysyłanie logów po ID aplikacji
    private void sendLogs() {
        if (appList.isEmpty()) {
            Toast.makeText(this, "Brak aplikacji do wybrania", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedIndex = appSpinner.getSelectedItemPosition();
        int appId = appList.get(selectedIndex).getId();
        String message = messageInput.getText().toString().trim();

        if (message.isEmpty()) {
            Toast.makeText(this, "Wpisz wiadomość", Toast.LENGTH_SHORT).show();
            return;
        }

        apiRepository.sendLogs(appId, message, new ApiRepository.ApiCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(SendLogsActivity.this, "Log wysłany!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(SendLogsActivity.this, "Błąd: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import api.ApiClient;
import api.ApiService;
import com.example.myapplication.Adapters.LogsAdapter;
import com.example.myapplication.Models.GetAppModel;
import com.example.myapplication.Models.GetLogsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class GetLogsActivity extends AppCompatActivity {

    private ApiService apiService;
    private RecyclerView recyclerView;
    private LogsAdapter adapter;
    private TextView noLogsMessage;
    private Spinner appSpinner;
    private Button downloadLogsButton;
    private List<GetAppModel> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_logs);

        // Inicjalizacja widoków
        recyclerView = findViewById(R.id.logsRecyclerView);
        appSpinner = findViewById(R.id.appSpinner);
        downloadLogsButton = findViewById(R.id.downloadLogsButton);
        noLogsMessage = findViewById(R.id.noLogsMessage);

        // Inicjalizacja RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Inicjalizacja API
        apiService = ApiClient.getClient().create(ApiService.class);

        // Pobranie listy aplikacji do Spinnera
        loadApps();

        // Obsługa przycisku pobierania logów
        downloadLogsButton.setOnClickListener(v -> fetchLogs());
    }

    private void loadApps() {
        apiService.getApps().enqueue(new Callback<List<GetAppModel>>() {
            @Override
            public void onResponse(Call<List<GetAppModel>> call, Response<List<GetAppModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    appList = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(GetLogsActivity.this, android.R.layout.simple_spinner_item);
                    for (GetAppModel app : appList) {
                        adapter.add(app.getId() + " - " + app.getApp_Name());
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    appSpinner.setAdapter(adapter);
                } else {
                    Log.e("GetLogsActivity", "Błąd pobierania aplikacji");
                }
            }

            @Override
            public void onFailure(Call<List<GetAppModel>> call, Throwable t) {
                Log.e("GetLogsActivity", "Błąd połączenia: " + t.getMessage());
            }
        });
    }

    private void fetchLogs() {
        if (appList == null || appList.isEmpty()) {
            Log.e("GetLogsActivity", "Brak dostępnych aplikacji");
            return;
        }

        // Pobranie ID wybranej aplikacji
        int selectedIndex = appSpinner.getSelectedItemPosition();
        int appId = appList.get(selectedIndex).getId();

        apiService.getLogs(appId).enqueue(new Callback<List<GetLogsModel>>() {
            @Override
            public void onResponse(Call<List<GetLogsModel>> call, Response<List<GetLogsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GetLogsModel> logs = response.body();
                    if (logs.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        noLogsMessage.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        noLogsMessage.setVisibility(View.GONE);
                        adapter.setLogs(logs);
                    }
                } else {
                    Log.e("GetLogsActivity", "Błąd odpowiedzi API");
                }
            }

            @Override
            public void onFailure(Call<List<GetLogsModel>> call, Throwable t) {
                Log.e("GetLogsActivity", "Błąd połączenia: " + t.getMessage());
            }
        });
    }
}

package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;  // Import DividerItemDecoration
import api.ApiClient;
import api.ApiService;
import com.example.myapplication.Adapters.LogsAdapter;
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
    private EditText appIdInput;
    private Button downloadLogsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_logs);

        // Inicjalizacja widoków
        recyclerView = findViewById(R.id.logsRecyclerView);
        appIdInput = findViewById(R.id.appIdInput);
        downloadLogsButton = findViewById(R.id.downloadLogsButton);
        noLogsMessage = findViewById(R.id.noLogsMessage);

        // Inicjalizacja RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Dodanie separatora do RecyclerView
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Inicjalizacja ApiService
        apiService = ApiClient.getClient().create(ApiService.class);

        // Obsługa kliknięcia przycisku "Download Logs"
        downloadLogsButton.setOnClickListener(v -> {
            String appIdString = appIdInput.getText().toString();
            if (!appIdString.isEmpty()) {
                int appId = Integer.parseInt(appIdString);
                fetchLogs(appId);
            }
        });
    }

    private void fetchLogs(int appId) {
        // Wywołanie API
        Call<List<GetLogsModel>> call = apiService.getLogs(appId);
        call.enqueue(new Callback<List<GetLogsModel>>() {
            @Override
            public void onResponse(Call<List<GetLogsModel>> call, Response<List<GetLogsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GetLogsModel> logs = response.body();
                    // Zaktualizuj adapter z nowymi logami
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

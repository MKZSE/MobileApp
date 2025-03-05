package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.AppsAdapter;
import com.example.myapplication.Models.GetAppModel;

import java.util.ArrayList;
import java.util.List;

import api.ApiClient;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAppsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppsAdapter adapter;
    private Button btnFetchApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_apps);

        recyclerView = findViewById(R.id.recyclerViewApps);
        btnFetchApps = findViewById(R.id.btnFetchApps);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        btnFetchApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchApps();
            }
        });
    }

    private void fetchApps() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<GetAppModel>> call = apiService.getApps();

        call.enqueue(new Callback<List<GetAppModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetAppModel>> call, @NonNull Response<List<GetAppModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GetAppModel> apps = response.body();
                    adapter.setApps(apps);
                } else {
                    Log.e("GetAppsActivity", "Błąd odpowiedzi API: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetAppModel>> call, @NonNull Throwable t) {
                Log.e("GetAppsActivity", "Błąd połączenia: " + t.getMessage());
            }
        });
    }
}

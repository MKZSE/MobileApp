package api;

import com.example.myapplication.Models.GetAppModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private final ApiService apiService;

    public ApiRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://erponeupdator.masterdev.pl/Masterdev_Updater/") // Zmień na swój URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public interface ApiAppsCallback {
        void onSuccess(List<GetAppModel> apps);
        void onFailure(String error);
    }

    public void getApps(ApiAppsCallback callback) {
        Call<List<GetAppModel>> call = apiService.getApps();
        call.enqueue(new Callback<List<GetAppModel>>() {
            @Override
            public void onResponse(Call<List<GetAppModel>> call, Response<List<GetAppModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Błąd serwera");
                }
            }

            @Override
            public void onFailure(Call<List<GetAppModel>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ApiCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public void sendLogs(int appId, String message, ApiCallback callback) {
        Call<Void> call = apiService.sendLogs(appId, message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure("Błąd wysyłania logów");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

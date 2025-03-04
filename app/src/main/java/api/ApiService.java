package api;

import com.example.myapplication.Models.GetLogsModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    @GET("GetLogs")
    @Headers({
            "accept: */*",
            "x-api-key: x"
    })
    Call<List<GetLogsModel>> getLogs(@Query("appId") int appId);

    
}

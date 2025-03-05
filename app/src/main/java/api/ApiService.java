package api;

import com.example.myapplication.Models.GetAppModel;
import com.example.myapplication.Models.GetLogsModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("GetLogs")
    @Headers({
            "accept: */*",
            "x-api-key: x"
    })
    Call<List<GetLogsModel>> getLogs(@Query("appId") int appId);

    @GET("GetApps")
    @Headers({
            "accept: */*",
            "x-api-key: x"
    })
    Call<List<GetAppModel>> getApps();

    @GET("SendLogs")
    @Headers({
            "accept: */*",
            "x-api-key: x"
    })
    Call<Void> sendLogs(
            @Query("appId") int appId,
            @Query("message") String message
    );

    @POST("AddNewApplication")
    Call<Void> addNewApplication(
            @Query("appName") String appname,
            @Query("directoryname") String directoryname,
            @Query("addres") String addres,
            @Query("iisAppName") String iisAppName,
            @Query("iisAppPoolName") String iisAppPoolName,
            @Query("pgsqlConnectionString") String pgsqlConnectionString
    );

}

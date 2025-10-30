package AirAware.com.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client Retrofit Singleton pour les appels API
 */
public class RetrofitClient {
    private static RetrofitClient instance;
    private Retrofit retrofit;
    private OpenWeatherApiService apiService;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(OpenWeatherApiService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public OpenWeatherApiService getApiService() {
        return apiService;
    }
}

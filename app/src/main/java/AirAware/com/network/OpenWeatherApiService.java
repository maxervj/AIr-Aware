package AirAware.com.network;

import AirAware.com.data.AirPollutionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface Retrofit pour l'API OpenWeather Air Pollution
 */
public interface OpenWeatherApiService {

    /**
     * URL de base de l'API OpenWeather
     */
    String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    /**
     * Récupère les données de pollution de l'air actuelles
     * @param lat Latitude
     * @param lon Longitude
     * @param appid Clé API OpenWeather
     * @return Response contenant les données de pollution
     */
    @GET("air_pollution")
    Call<AirPollutionResponse> getCurrentAirPollution(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid
    );

    /**
     * Récupère les prévisions de pollution de l'air
     * @param lat Latitude
     * @param lon Longitude
     * @param appid Clé API OpenWeather
     * @return Response contenant les prévisions de pollution
     */
    @GET("air_pollution/forecast")
    Call<AirPollutionResponse> getForecastAirPollution(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid
    );

    /**
     * Récupère l'historique de pollution de l'air
     * @param lat Latitude
     * @param lon Longitude
     * @param start Timestamp de début (Unix timestamp)
     * @param end Timestamp de fin (Unix timestamp)
     * @param appid Clé API OpenWeather
     * @return Response contenant l'historique de pollution
     */
    @GET("air_pollution/history")
    Call<AirPollutionResponse> getHistoricalAirPollution(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("start") long start,
            @Query("end") long end,
            @Query("appid") String appid
    );
}

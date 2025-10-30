package AirAware.com.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import AirAware.com.data.AirPollutionResponse;
import AirAware.com.model.AirQuality;
import AirAware.com.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository pour gérer les données de qualité de l'air via l'API OpenWeather
 * Cette classe sert d'intermédiaire entre le ViewModel et les sources de données
 */
public class AirQualityRepository {
    private static final String TAG = "AirQualityRepository";
    private static AirQualityRepository instance;
    private MutableLiveData<List<AirQuality>> airQualityData;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Boolean> isLoading;

    // TODO: Remplacer par votre clé API OpenWeather
    private static final String API_KEY = "VOTRE_CLE_API_ICI";

    private AirQualityRepository() {
        airQualityData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
    }

    public static synchronized AirQualityRepository getInstance() {
        if (instance == null) {
            instance = new AirQualityRepository();
        }
        return instance;
    }

    /**
     * Récupère les données de qualité de l'air pour une localisation donnée
     */
    public LiveData<List<AirQuality>> getAirQualityData(double latitude, double longitude, String locationName) {
        isLoading.setValue(true);
        fetchAirPollutionData(latitude, longitude, locationName);
        return airQualityData;
    }

    /**
     * Récupère les données depuis l'API OpenWeather
     */
    private void fetchAirPollutionData(double latitude, double longitude, String locationName) {
        RetrofitClient.getInstance()
                .getApiService()
                .getCurrentAirPollution(latitude, longitude, API_KEY)
                .enqueue(new Callback<AirPollutionResponse>() {
                    @Override
                    public void onResponse(Call<AirPollutionResponse> call, Response<AirPollutionResponse> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            AirPollutionResponse apiResponse = response.body();
                            List<AirQuality> airQualityList = convertToAirQualityList(apiResponse, locationName);
                            airQualityData.setValue(airQualityList);
                            Log.d(TAG, "Données récupérées avec succès");
                        } else {
                            String error = "Erreur API: " + response.code();
                            errorMessage.setValue(error);
                            Log.e(TAG, error);
                        }
                    }

                    @Override
                    public void onFailure(Call<AirPollutionResponse> call, Throwable t) {
                        isLoading.setValue(false);
                        String error = "Erreur réseau: " + t.getMessage();
                        errorMessage.setValue(error);
                        Log.e(TAG, error, t);
                    }
                });
    }

    /**
     * Convertit la réponse API en liste d'objets AirQuality
     */
    private List<AirQuality> convertToAirQualityList(AirPollutionResponse response, String locationName) {
        List<AirQuality> airQualityList = new ArrayList<>();

        if (response.getList() != null && !response.getList().isEmpty()) {
            for (AirPollutionResponse.AirPollutionData data : response.getList()) {
                AirQuality airQuality = new AirQuality();

                // Coordonnées
                airQuality.setLatitude(response.getCoord().getLat());
                airQuality.setLongitude(response.getCoord().getLon());
                airQuality.setLocation(locationName);

                // AQI et timestamp
                airQuality.setAqi(data.getMain().getAqi());
                airQuality.setTimestamp(data.getDt());

                // Components de pollution
                AirPollutionResponse.Components comp = data.getComponents();
                airQuality.setCo(comp.getCo());
                airQuality.setNo(comp.getNo());
                airQuality.setNo2(comp.getNo2());
                airQuality.setO3(comp.getO3());
                airQuality.setSo2(comp.getSo2());
                airQuality.setPm2_5(comp.getPm2_5());
                airQuality.setPm10(comp.getPm10());
                airQuality.setNh3(comp.getNh3());

                airQualityList.add(airQuality);
            }
        }

        return airQualityList;
    }

    /**
     * Rafraîchit les données pour une localisation
     */
    public void refreshData(double latitude, double longitude, String locationName) {
        fetchAirPollutionData(latitude, longitude, locationName);
    }

    /**
     * Récupère les messages d'erreur
     */
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Récupère l'état de chargement
     */
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}

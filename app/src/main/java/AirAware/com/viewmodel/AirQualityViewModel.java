package AirAware.com.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import AirAware.com.model.AirQuality;
import AirAware.com.repository.AirQualityRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel pour gérer la logique de présentation des données de qualité de l'air
 * Sert d'intermédiaire entre la View (Activity/Fragment) et le Repository
 * Utilise Hilt pour l'injection de dépendances
 */
@HiltViewModel
public class AirQualityViewModel extends ViewModel {
    private final AirQualityRepository repository;
    private LiveData<List<AirQuality>> airQualityData;
    private LiveData<String> errorMessage;
    private LiveData<Boolean> isLoading;

    @Inject
    public AirQualityViewModel(AirQualityRepository repository) {
        this.repository = repository;
    }

    /**
     * Charge les données de qualité de l'air pour une localisation donnée
     */
    public void loadAirQualityData(double latitude, double longitude, String locationName) {
        airQualityData = repository.getAirQualityData(latitude, longitude, locationName);
        errorMessage = repository.getErrorMessage();
        isLoading = repository.getIsLoading();
    }

    /**
     * Expose les données de qualité de l'air à la View
     */
    public LiveData<List<AirQuality>> getAirQualityData() {
        return airQualityData;
    }

    /**
     * Expose les messages d'erreur
     */
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Expose l'état de chargement
     */
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * Rafraîchit les données pour une localisation
     */
    public void refreshData(double latitude, double longitude, String locationName) {
        repository.refreshData(latitude, longitude, locationName);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Nettoyer les ressources si nécessaire
    }
}

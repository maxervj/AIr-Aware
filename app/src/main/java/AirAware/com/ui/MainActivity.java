package AirAware.com.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import AirAware.com.R;
import AirAware.com.model.AirQuality;
import AirAware.com.viewmodel.AirQualityViewModel;

/**
 * MainActivity utilisant l'architecture MVVM avec l'API OpenWeather
 * La View observe les données du ViewModel via LiveData
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AirQualityViewModel viewModel;

    // Coordonnées par défaut (Paris)
    private static final double DEFAULT_LATITUDE = 48.8566;
    private static final double DEFAULT_LONGITUDE = 2.3522;
    private static final String DEFAULT_LOCATION = "Paris";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser le ViewModel
        initViewModel();

        // Charger les données de pollution pour Paris
        loadAirQualityData();

        // Observer les données
        observeData();
    }

    /**
     * Initialise le ViewModel
     */
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AirQualityViewModel.class);
    }

    /**
     * Charge les données de qualité de l'air
     */
    private void loadAirQualityData() {
        viewModel.loadAirQualityData(DEFAULT_LATITUDE, DEFAULT_LONGITUDE, DEFAULT_LOCATION);
    }

    /**
     * Observer les changements de données du ViewModel
     */
    private void observeData() {
        // Observer les données de pollution
        viewModel.getAirQualityData().observe(this, airQualityList -> {
            if (airQualityList != null && !airQualityList.isEmpty()) {
                Log.d(TAG, "Données reçues: " + airQualityList.size() + " éléments");

                // Afficher les données dans les logs
                for (AirQuality airQuality : airQualityList) {
                    Log.d(TAG, "Location: " + airQuality.getLocation() +
                               ", AQI: " + airQuality.getAqi() +
                               ", Status: " + airQuality.getStatus() +
                               ", PM2.5: " + airQuality.getPm2_5() +
                               ", PM10: " + airQuality.getPm10() +
                               ", CO: " + airQuality.getCo() +
                               ", NO2: " + airQuality.getNo2() +
                               ", O3: " + airQuality.getO3());
                }

                // Mettre à jour l'UI
                updateUI(airQualityList);
            }
        });

        // Observer les erreurs
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Log.e(TAG, "Erreur: " + error);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        // Observer l'état de chargement
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    Log.d(TAG, "Chargement des données...");
                    // TODO: Afficher un indicateur de chargement
                } else {
                    Log.d(TAG, "Chargement terminé");
                    // TODO: Masquer l'indicateur de chargement
                }
            }
        });
    }

    /**
     * Met à jour l'interface utilisateur avec les données
     */
    private void updateUI(java.util.List<AirQuality> airQualityList) {
        // TODO: Implémenter la mise à jour de l'UI
        // Exemple: afficher dans un RecyclerView, TextView, etc.
        if (!airQualityList.isEmpty()) {
            AirQuality firstData = airQualityList.get(0);
            String message = String.format("Qualité de l'air à %s: %s (AQI: %d)\nPM2.5: %.2f μg/m³\nPM10: %.2f μg/m³",
                    firstData.getLocation(),
                    firstData.getStatus(),
                    firstData.getAqi(),
                    firstData.getPm2_5(),
                    firstData.getPm10());
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Rafraîchir les données (peut être appelé par un bouton ou un pull-to-refresh)
     */
    private void refreshData() {
        viewModel.refreshData(DEFAULT_LATITUDE, DEFAULT_LONGITUDE, DEFAULT_LOCATION);
    }
}

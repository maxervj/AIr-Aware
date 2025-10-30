package AirAware.com.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import AirAware.com.R;
import AirAware.com.databinding.ActivityMainBinding;
import AirAware.com.ui.fragments.ImagesFragment;
import AirAware.com.ui.fragments.PollutionListFragment;
import AirAware.com.viewmodel.AirQualityViewModel;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * MainActivity utilisant l'architecture MVVM avec l'API OpenWeather
 * Gère la navigation entre les fragments via BottomNavigationView
 * Utilise Hilt pour l'injection de dépendances
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private AirQualityViewModel viewModel;

    // Coordonnées par défaut (Paris)
    private static final double DEFAULT_LATITUDE = 48.8566;
    private static final double DEFAULT_LONGITUDE = 2.3522;
    private static final String DEFAULT_LOCATION = "Paris";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Initialiser le ViewModel
        initViewModel();

        // Configurer la navigation
        setupBottomNavigation();

        // Charger le fragment initial
        if (savedInstanceState == null) {
            loadFragment(new PollutionListFragment());
        }

        // Charger les données de pollution pour Paris
        loadAirQualityData();
    }

    /**
     * Initialise le ViewModel
     */
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AirQualityViewModel.class);
        Log.d(TAG, "ViewModel initialisé");
    }

    /**
     * Configure le BottomNavigationView
     */
    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_list) {
                selectedFragment = new PollutionListFragment();
                Log.d(TAG, "Fragment Liste sélectionné");
            } else if (itemId == R.id.navigation_images) {
                selectedFragment = new ImagesFragment();
                Log.d(TAG, "Fragment Images sélectionné");
            }

            if (selectedFragment != null) {
                return loadFragment(selectedFragment);
            }
            return false;
        });
    }

    /**
     * Charge un fragment dans le conteneur
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    /**
     * Charge les données de qualité de l'air
     */
    private void loadAirQualityData() {
        viewModel.loadAirQualityData(DEFAULT_LATITUDE, DEFAULT_LONGITUDE, DEFAULT_LOCATION);
        Log.d(TAG, "Chargement des données pour " + DEFAULT_LOCATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

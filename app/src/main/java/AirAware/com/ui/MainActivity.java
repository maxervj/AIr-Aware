package AirAware.com.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
import AirAware.com.model.City;
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

    // Liste des villes disponibles
    private City[] cities;
    private City selectedCity;

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

        // Configurer le sélecteur de ville
        setupCitySelector();

        // Charger explicitement les données initiales AVANT de charger le fragment
        // Ceci garantit que les LiveData sont initialisées dans le ViewModel
        loadAirQualityData();

        // Charger le fragment initial (les LiveData sont maintenant initialisées)
        if (savedInstanceState == null) {
            loadFragment(new PollutionListFragment());
        }
    }

    /**
     * Initialise le ViewModel
     */
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AirQualityViewModel.class);
        Log.d(TAG, "ViewModel initialisé");
    }

    /**
     * Configure le sélecteur de ville
     */
    private void setupCitySelector() {
        cities = City.getAvailableCities();
        selectedCity = cities[0]; // Paris par défaut

        // Créer un ArrayAdapter pour le Spinner
        ArrayAdapter<City> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                cities
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.citySpinner.setAdapter(adapter);

        // Gérer la sélection de ville
        binding.citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cities[position];
                loadAirQualityData();
                Log.d(TAG, "Ville sélectionnée : " + selectedCity.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire
            }
        });
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
     * Charge les données de qualité de l'air pour la ville sélectionnée
     */
    private void loadAirQualityData() {
        if (selectedCity != null) {
            viewModel.loadAirQualityData(
                    selectedCity.getLatitude(),
                    selectedCity.getLongitude(),
                    selectedCity.getName()
            );
            Log.d(TAG, "Chargement des données pour " + selectedCity.getName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

package AirAware.com.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import AirAware.com.R;
import AirAware.com.databinding.FragmentHomeBinding;
import AirAware.com.model.AirQuality;
import AirAware.com.utils.AirQualityClassifier;
import AirAware.com.utils.HealthRecommendationGenerator;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragment d'accueil affichant un résumé de l'application AIrAware
 * et la qualité de l'air actuelle si des données sont disponibles
 */
@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private AirAware.com.viewmodel.AirQualityViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AirAware.com.viewmodel.AirQualityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getAirQualityData().observe(getViewLifecycleOwner(), airQualityList -> {
            if (airQualityList != null && !airQualityList.isEmpty()) {
                AirQuality currentAirQuality = airQualityList.get(0);
                displayAirQualityData(currentAirQuality);
            } else {
                showNoDataState();
            }
        });
    }

    private void displayAirQualityData(AirQuality airQuality) {
        // Masquer le message "pas de données"
        binding.textNoData.setVisibility(View.GONE);
        binding.layoutAirQualityData.setVisibility(View.VISIBLE);

        // Afficher le nom de la ville
        binding.textCurrentCity.setText("🌍 " + airQuality.getLocation());

        // Afficher l'indice AQI
        binding.textAqiValue.setText(String.valueOf(airQuality.getAqi()));

        // Classifier le niveau de pollution
        AirQualityClassifier.NiveauPollution niveau =
            AirQualityClassifier.classifierPollution(airQuality);

        // Afficher le niveau de qualité avec l'emoji
        String emoji = AirQualityClassifier.obtenirEmoji(niveau);
        String niveauText = getNiveauText(niveau);
        binding.textQualityLevel.setText(emoji + " " + niveauText);

        // Appliquer la couleur appropriée
        int color = getColorForNiveau(niveau);
        binding.textQualityLevel.setTextColor(color);
        binding.textAqiValue.setTextColor(color);

        // Afficher le résumé santé
        String healthSummary = HealthRecommendationGenerator.genererResumeCourt(airQuality);
        binding.textHealthSummary.setText(healthSummary);

        // Adapter la couleur de fond du résumé santé
        int backgroundColor = getBackgroundColorForNiveau(niveau);
        int textColor = getHealthTextColorForNiveau(niveau);
        binding.textHealthSummary.setBackgroundColor(backgroundColor);
        binding.textHealthSummary.setTextColor(textColor);
    }

    private void showNoDataState() {
        binding.textNoData.setVisibility(View.VISIBLE);
        binding.layoutAirQualityData.setVisibility(View.GONE);
    }

    private String getNiveauText(AirQualityClassifier.NiveauPollution niveau) {
        switch (niveau) {
            case EXCELLENT:
                return "Excellent";
            case BON:
                return "Bon";
            case MODERE:
                return "Modéré";
            case MAUVAIS:
                return "Mauvais";
            case TRES_MAUVAIS:
                return "Très Mauvais";
            case EXTREMEMENT_MAUVAIS:
                return "Extrêmement Mauvais";
            default:
                return "Inconnu";
        }
    }

    private int getColorForNiveau(AirQualityClassifier.NiveauPollution niveau) {
        switch (niveau) {
            case EXCELLENT:
                return 0xFF00C853; // Vert foncé
            case BON:
                return 0xFF4CAF50; // Vert
            case MODERE:
                return 0xFFFFC107; // Jaune/Orange
            case MAUVAIS:
                return 0xFFFF5722; // Orange foncé
            case TRES_MAUVAIS:
                return 0xFFF44336; // Rouge
            case EXTREMEMENT_MAUVAIS:
                return 0xFF9C27B0; // Violet
            default:
                return 0xFF757575; // Gris
        }
    }

    private int getBackgroundColorForNiveau(AirQualityClassifier.NiveauPollution niveau) {
        switch (niveau) {
            case EXCELLENT:
                return 0xFFE8F5E9; // Vert clair
            case BON:
                return 0xFFE8F5E9; // Vert clair
            case MODERE:
                return 0xFFFFF3E0; // Orange clair
            case MAUVAIS:
                return 0xFFFFE0B2; // Orange très clair
            case TRES_MAUVAIS:
                return 0xFFFFCDD2; // Rouge clair
            case EXTREMEMENT_MAUVAIS:
                return 0xFFF3E5F5; // Violet clair
            default:
                return 0xFFF5F5F5; // Gris clair
        }
    }

    private int getHealthTextColorForNiveau(AirQualityClassifier.NiveauPollution niveau) {
        switch (niveau) {
            case EXCELLENT:
                return 0xFF1B5E20; // Vert foncé
            case BON:
                return 0xFF2E7D32; // Vert foncé
            case MODERE:
                return 0xFFE65100; // Orange foncé
            case MAUVAIS:
                return 0xFFBF360C; // Orange très foncé
            case TRES_MAUVAIS:
                return 0xFFC62828; // Rouge foncé
            case EXTREMEMENT_MAUVAIS:
                return 0xFF6A1B9A; // Violet foncé
            default:
                return 0xFF424242; // Gris foncé
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

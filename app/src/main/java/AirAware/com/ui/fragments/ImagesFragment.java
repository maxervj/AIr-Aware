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
import AirAware.com.databinding.FragmentImagesBinding;
import AirAware.com.model.AirQuality;
import AirAware.com.utils.AirQualityClassifier;
import AirAware.com.viewmodel.AirQualityViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragment pour afficher des images représentant les différents niveaux de pollution
 */
@AndroidEntryPoint
public class ImagesFragment extends Fragment {

    private FragmentImagesBinding binding;
    private AirQualityViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AirQualityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentImagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupImages();
        observeViewModel();

        // Charger les données par défaut (Paris)
        viewModel.loadAirQualityData(48.8566, 2.3522, "Paris");
    }

    private void setupImages() {
        // Configurer les images de fond pour chaque niveau
        binding.imageViewExcellent.setBackgroundResource(R.drawable.bg_excellent);
        binding.imageViewGood.setBackgroundResource(R.drawable.bg_good);
        binding.imageViewModerate.setBackgroundResource(R.drawable.bg_moderate);
        binding.imageViewPoor.setBackgroundResource(R.drawable.bg_poor);
        binding.imageViewVeryPoor.setBackgroundResource(R.drawable.bg_very_poor);
        binding.imageViewExtreme.setBackgroundResource(R.drawable.bg_extreme);
    }

    private void observeViewModel() {
        viewModel.getAirQualityData().observe(getViewLifecycleOwner(), airQualityList -> {
            if (airQualityList != null && !airQualityList.isEmpty()) {
                AirQuality currentAirQuality = airQualityList.get(0);
                updateMainImage(currentAirQuality);
            }
        });
    }

    private void updateMainImage(AirQuality airQuality) {
        AirQualityClassifier.NiveauPollution niveau =
                AirQualityClassifier.classifierPollution(airQuality);

        // Mettre à jour l'image principale en fonction du niveau
        int backgroundResource = getBackgroundForNiveau(niveau);
        binding.imageViewMain.setBackgroundResource(backgroundResource);

        // Mettre à jour le texte de la légende
        String caption = getNiveauText(niveau) + " - AQI: " + airQuality.getAqi();
        binding.textViewMainImageCaption.setText(caption);

        // Mettre à jour la visibilité des cartes pour mettre en évidence le niveau actuel
        highlightCurrentLevel(niveau);
    }

    private int getBackgroundForNiveau(AirQualityClassifier.NiveauPollution niveau) {
        switch (niveau) {
            case EXCELLENT:
                return R.drawable.bg_excellent;
            case BON:
                return R.drawable.bg_good;
            case MODERE:
                return R.drawable.bg_moderate;
            case MAUVAIS:
                return R.drawable.bg_poor;
            case TRES_MAUVAIS:
                return R.drawable.bg_very_poor;
            case EXTREMEMENT_MAUVAIS:
                return R.drawable.bg_extreme;
            default:
                return R.drawable.bg_moderate;
        }
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

    private void highlightCurrentLevel(AirQualityClassifier.NiveauPollution niveau) {
        // Réinitialiser toutes les elevations
        float normalElevation = 4f;
        float highlightedElevation = 12f;

        binding.cardExcellent.setCardElevation(normalElevation);
        binding.cardGood.setCardElevation(normalElevation);
        binding.cardModerate.setCardElevation(normalElevation);
        binding.cardPoor.setCardElevation(normalElevation);
        binding.cardVeryPoor.setCardElevation(normalElevation);
        binding.cardExtreme.setCardElevation(normalElevation);

        // Mettre en évidence le niveau actuel
        switch (niveau) {
            case EXCELLENT:
                binding.cardExcellent.setCardElevation(highlightedElevation);
                break;
            case BON:
                binding.cardGood.setCardElevation(highlightedElevation);
                break;
            case MODERE:
                binding.cardModerate.setCardElevation(highlightedElevation);
                break;
            case MAUVAIS:
                binding.cardPoor.setCardElevation(highlightedElevation);
                break;
            case TRES_MAUVAIS:
                binding.cardVeryPoor.setCardElevation(highlightedElevation);
                break;
            case EXTREMEMENT_MAUVAIS:
                binding.cardExtreme.setCardElevation(highlightedElevation);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

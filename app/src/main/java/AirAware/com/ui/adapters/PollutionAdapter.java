package AirAware.com.ui.adapters;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import AirAware.com.databinding.ItemPollutionBinding;
import AirAware.com.model.AirQuality;
import AirAware.com.model.HealthRecommendation;
import AirAware.com.utils.AirQualityClassifier;
import AirAware.com.utils.HealthRecommendationGenerator;

/**
 * Adapter pour afficher les données de pollution dans un RecyclerView
 */
public class PollutionAdapter extends ListAdapter<AirQuality, PollutionAdapter.PollutionViewHolder> {

    public PollutionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<AirQuality> DIFF_CALLBACK = new DiffUtil.ItemCallback<AirQuality>() {
        @Override
        public boolean areItemsTheSame(@NonNull AirQuality oldItem, @NonNull AirQuality newItem) {
            return oldItem.getLatitude() == newItem.getLatitude() &&
                    oldItem.getLongitude() == newItem.getLongitude() &&
                    oldItem.getTimestamp() == newItem.getTimestamp();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AirQuality oldItem, @NonNull AirQuality newItem) {
            return oldItem.getAqi() == newItem.getAqi() &&
                    oldItem.getPm2_5() == newItem.getPm2_5() &&
                    oldItem.getPm10() == newItem.getPm10() &&
                    oldItem.getNo2() == newItem.getNo2() &&
                    oldItem.getO3() == newItem.getO3();
        }
    };

    @NonNull
    @Override
    public PollutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPollutionBinding binding = ItemPollutionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PollutionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PollutionViewHolder holder, int position) {
        AirQuality airQuality = getItem(position);
        holder.bind(airQuality);
    }

    static class PollutionViewHolder extends RecyclerView.ViewHolder {
        private final ItemPollutionBinding binding;

        public PollutionViewHolder(@NonNull ItemPollutionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AirQuality airQuality) {
            // Classifier le niveau de pollution
            AirQualityClassifier.NiveauPollution niveau =
                    AirQualityClassifier.classifierPollution(airQuality);

            // Définir la localisation
            String location = airQuality.getLocation() != null && !airQuality.getLocation().isEmpty()
                    ? airQuality.getLocation()
                    : String.format(Locale.getDefault(), "Lat: %.4f, Lon: %.4f",
                    airQuality.getLatitude(), airQuality.getLongitude());
            binding.textViewLocation.setText(location);

            // Définir l'AQI
            binding.textViewAQI.setText(String.format(Locale.getDefault(), "AQI: %d", airQuality.getAqi()));

            // Définir le status avec le niveau français
            String status = getNiveauText(niveau);
            binding.textViewStatus.setText(status);

            // Définir la couleur de l'indicateur
            String colorCode = AirQualityClassifier.obtenirCodeCouleur(niveau);
            binding.viewColorIndicator.setBackgroundColor(Color.parseColor(colorCode));

            // Définir la couleur de fond de l'AQI badge
            int aqiColor = getAqiColor(airQuality.getAqi());
            binding.textViewAQI.setBackgroundColor(aqiColor);

            // Définir les valeurs des polluants
            binding.textViewPM25.setText(String.format(Locale.getDefault(), "%.1f μg/m³", airQuality.getPm2_5()));
            binding.textViewPM10.setText(String.format(Locale.getDefault(), "%.1f μg/m³", airQuality.getPm10()));
            binding.textViewNO2.setText(String.format(Locale.getDefault(), "%.1f μg/m³", airQuality.getNo2()));
            binding.textViewO3.setText(String.format(Locale.getDefault(), "%.1f μg/m³", airQuality.getO3()));

            // Afficher le résumé des recommandations santé
            String healthSummary = HealthRecommendationGenerator.genererResumeCourt(airQuality);
            binding.textViewHealthSummary.setText(healthSummary);

            // Configurer le bouton pour afficher les détails
            binding.buttonViewDetails.setOnClickListener(v -> {
                showHealthRecommendationDialog(airQuality);
            });
        }

        private void showHealthRecommendationDialog(AirQuality airQuality) {
            // Générer la recommandation complète
            HealthRecommendation recommendation = HealthRecommendationGenerator.genererRecommandation(airQuality);

            // Créer un TextView pour afficher les détails
            TextView textView = new TextView(binding.getRoot().getContext());
            textView.setText(recommendation.getRecommendationComplete());
            textView.setTextSize(13);
            textView.setPadding(40, 40, 40, 40);
            textView.setTextIsSelectable(true);

            // Mettre le TextView dans un ScrollView
            ScrollView scrollView = new ScrollView(binding.getRoot().getContext());
            scrollView.addView(textView);

            // Créer et afficher le dialog
            new AlertDialog.Builder(binding.getRoot().getContext())
                    .setTitle("🏥 Recommandations Santé Détaillées")
                    .setView(scrollView)
                    .setPositiveButton("Compris", null)
                    .setIcon(getDialogIcon(recommendation.getNiveauRisque()))
                    .show();
        }

        private int getDialogIcon(int niveauRisque) {
            // Retourne l'icône appropriée selon le niveau de risque
            if (niveauRisque <= 1) {
                return android.R.drawable.ic_dialog_info; // Info pour bon air
            } else if (niveauRisque <= 3) {
                return android.R.drawable.ic_dialog_alert; // Alerte pour modéré/mauvais
            } else {
                return android.R.drawable.ic_dialog_alert; // Alerte critique
            }
        }

        private String getNiveauText(AirQualityClassifier.NiveauPollution niveau) {
            String emoji = AirQualityClassifier.obtenirEmoji(niveau);
            switch (niveau) {
                case EXCELLENT:
                    return emoji + " Excellent";
                case BON:
                    return emoji + " Bon";
                case MODERE:
                    return emoji + " Modéré";
                case MAUVAIS:
                    return emoji + " Mauvais";
                case TRES_MAUVAIS:
                    return emoji + " Très Mauvais";
                case EXTREMEMENT_MAUVAIS:
                    return emoji + " Extrêmement Mauvais";
                default:
                    return emoji + " Inconnu";
            }
        }

        private int getAqiColor(int aqi) {
            switch (aqi) {
                case 1:
                    return Color.parseColor("#00E400");
                case 2:
                    return Color.parseColor("#92D050");
                case 3:
                    return Color.parseColor("#FFFF00");
                case 4:
                    return Color.parseColor("#FF7E00");
                case 5:
                    return Color.parseColor("#FF0000");
                default:
                    return Color.parseColor("#8F3F97");
            }
        }
    }
}

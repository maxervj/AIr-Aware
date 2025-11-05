package AirAware.com.model;

import java.util.List;

import AirAware.com.utils.AirQualityClassifier.NiveauPollution;

/**
 * Modèle pour les recommandations santé basées sur la qualité de l'air
 */
public class HealthRecommendation {

    private NiveauPollution niveauPollution;
    private String recommendationGenerale;
    private List<String> groupesARisque;
    private List<String> symptomesAsurveiller;
    private List<String> precautionsAPrendre;
    private String conseillActivite;
    private int niveauRisque; // 0-5

    public HealthRecommendation(
            NiveauPollution niveauPollution,
            String recommendationGenerale,
            List<String> groupesARisque,
            List<String> symptomesAsurveiller,
            List<String> precautionsAPrendre,
            String conseillActivite,
            int niveauRisque) {
        this.niveauPollution = niveauPollution;
        this.recommendationGenerale = recommendationGenerale;
        this.groupesARisque = groupesARisque;
        this.symptomesAsurveiller = symptomesAsurveiller;
        this.precautionsAPrendre = precautionsAPrendre;
        this.conseillActivite = conseillActivite;
        this.niveauRisque = niveauRisque;
    }

    // Getters
    public NiveauPollution getNiveauPollution() {
        return niveauPollution;
    }

    public String getRecommendationGenerale() {
        return recommendationGenerale;
    }

    public List<String> getGroupesARisque() {
        return groupesARisque;
    }

    public List<String> getSymptomesAsurveiller() {
        return symptomesAsurveiller;
    }

    public List<String> getPrecautionsAPrendre() {
        return precautionsAPrendre;
    }

    public String getConseillActivite() {
        return conseillActivite;
    }

    public int getNiveauRisque() {
        return niveauRisque;
    }

    /**
     * Retourne une chaîne formatée avec toutes les recommandations
     */
    public String getRecommendationComplete() {
        StringBuilder sb = new StringBuilder();

        sb.append("🌡️ NIVEAU: ").append(niveauPollution.getLabel()).append("\n\n");

        sb.append("📋 RECOMMANDATION GÉNÉRALE:\n");
        sb.append(recommendationGenerale).append("\n\n");

        if (!groupesARisque.isEmpty()) {
            sb.append("⚠️ GROUPES À RISQUE:\n");
            for (String groupe : groupesARisque) {
                sb.append("• ").append(groupe).append("\n");
            }
            sb.append("\n");
        }

        if (!symptomesAsurveiller.isEmpty()) {
            sb.append("🩺 SYMPTÔMES À SURVEILLER:\n");
            for (String symptome : symptomesAsurveiller) {
                sb.append("• ").append(symptome).append("\n");
            }
            sb.append("\n");
        }

        if (!precautionsAPrendre.isEmpty()) {
            sb.append("🛡️ PRÉCAUTIONS À PRENDRE:\n");
            for (String precaution : precautionsAPrendre) {
                sb.append("• ").append(precaution).append("\n");
            }
            sb.append("\n");
        }

        sb.append("🏃 ACTIVITÉS PHYSIQUES:\n");
        sb.append(conseillActivite);

        return sb.toString();
    }
}

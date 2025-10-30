package AirAware.com.utils;

import AirAware.com.model.AirQuality;

/**
 * Classe utilitaire pour classifier et analyser la qualité de l'air
 * Toutes les méthodes et noms sont en français pour refléter le degré de pollution
 */
public class AirQualityClassifier {

    /**
     * Niveaux de pollution (Degré de pollution en français)
     */
    public enum NiveauPollution {
        EXCELLENT("Excellent", "Air très pur"),
        BON("Bon", "Qualité de l'air satisfaisante"),
        MODERE("Modéré", "Qualité acceptable pour la plupart"),
        MAUVAIS("Mauvais", "Effets sur la santé pour certains groupes"),
        TRES_MAUVAIS("Très mauvais", "Alerte santé, tout le monde peut être affecté"),
        EXTREMEMENT_MAUVAIS("Extrêmement mauvais", "Urgence sanitaire, danger pour tous");

        private final String label;
        private final String description;

        NiveauPollution(String label, String description) {
            this.label = label;
            this.description = description;
        }

        public String getLabel() {
            return label;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Classifie le niveau de pollution selon l'AQI et les composants
     */
    public static NiveauPollution classifierPollution(AirQuality airQuality) {
        if (airQuality == null) {
            return NiveauPollution.MODERE;
        }

        int aqi = airQuality.getAqi();
        double pm25 = airQuality.getPm2_5();
        double pm10 = airQuality.getPm10();

        // Classification basée sur l'AQI OpenWeather (1-5)
        // et les concentrations de PM2.5 et PM10
        if (aqi == 1 && pm25 < 10 && pm10 < 20) {
            return NiveauPollution.EXCELLENT;
        } else if (aqi == 1 || (aqi == 2 && pm25 < 25)) {
            return NiveauPollution.BON;
        } else if (aqi == 2 || (aqi == 3 && pm25 < 50)) {
            return NiveauPollution.MODERE;
        } else if (aqi == 3 || (aqi == 4 && pm25 < 75)) {
            return NiveauPollution.MAUVAIS;
        } else if (aqi == 4 || (aqi == 5 && pm25 < 150)) {
            return NiveauPollution.TRES_MAUVAIS;
        } else {
            return NiveauPollution.EXTREMEMENT_MAUVAIS;
        }
    }

    /**
     * Détermine si la qualité de l'air est dangereuse
     */
    public static boolean estDangereux(AirQuality airQuality) {
        if (airQuality == null) return false;
        NiveauPollution niveau = classifierPollution(airQuality);
        return niveau == NiveauPollution.TRES_MAUVAIS || niveau == NiveauPollution.EXTREMEMENT_MAUVAIS;
    }

    /**
     * Détermine si la qualité de l'air est saine
     */
    public static boolean estSain(AirQuality airQuality) {
        if (airQuality == null) return false;
        NiveauPollution niveau = classifierPollution(airQuality);
        return niveau == NiveauPollution.EXCELLENT || niveau == NiveauPollution.BON;
    }

    /**
     * Retourne un message de recommandation basé sur la qualité de l'air
     */
    public static String obtenirRecommandation(AirQuality airQuality) {
        NiveauPollution niveau = classifierPollution(airQuality);

        switch (niveau) {
            case EXCELLENT:
                return "Conditions idéales pour toutes activités extérieures.";
            case BON:
                return "Profitez de vos activités extérieures en toute sécurité.";
            case MODERE:
                return "Qualité acceptable. Personnes sensibles : limitez les efforts prolongés.";
            case MAUVAIS:
                return "Réduisez les activités physiques intenses à l'extérieur.";
            case TRES_MAUVAIS:
                return "Évitez les activités extérieures. Restez à l'intérieur si possible.";
            case EXTREMEMENT_MAUVAIS:
                return "ALERTE ! Restez à l'intérieur. Portez un masque si vous devez sortir.";
            default:
                return "Données insuffisantes pour une recommandation.";
        }
    }

    /**
     * Calcule un score de pollution global (0-100, où 100 = très pollué)
     */
    public static int calculerScorePollution(AirQuality airQuality) {
        if (airQuality == null) return 0;

        // Score basé sur l'AQI (0-100)
        int aqiScore = (airQuality.getAqi() - 1) * 25; // 1->0, 2->25, 3->50, 4->75, 5->100

        // Score basé sur PM2.5 (0-40 points)
        int pm25Score = Math.min(40, (int) (airQuality.getPm2_5() / 5));

        // Score basé sur PM10 (0-30 points)
        int pm10Score = Math.min(30, (int) (airQuality.getPm10() / 10));

        // Score basé sur NO2 (0-20 points)
        int no2Score = Math.min(20, (int) (airQuality.getNo2() / 10));

        // Score basé sur O3 (0-10 points)
        int o3Score = Math.min(10, (int) (airQuality.getO3() / 30));

        return Math.min(100, aqiScore + pm25Score + pm10Score + no2Score + o3Score);
    }

    /**
     * Détermine la couleur associée au niveau de pollution (pour l'UI)
     */
    public static String obtenirCodeCouleur(AirQuality airQuality) {
        NiveauPollution niveau = classifierPollution(airQuality);

        switch (niveau) {
            case EXCELLENT:
                return "#00E400"; // Vert foncé
            case BON:
                return "#92D050"; // Vert clair
            case MODERE:
                return "#FFFF00"; // Jaune
            case MAUVAIS:
                return "#FF7E00"; // Orange
            case TRES_MAUVAIS:
                return "#FF0000"; // Rouge
            case EXTREMEMENT_MAUVAIS:
                return "#8F3F97"; // Violet
            default:
                return "#808080"; // Gris
        }
    }

    // ====== MÉTHODES DE COMPATIBILITÉ (pour code existant) ======

    /**
     * @deprecated Utiliser {@link #classifierPollution(AirQuality)} à la place
     */
    @Deprecated
    public static NiveauPollution classifyPollution(AirQuality airQuality) {
        return classifierPollution(airQuality);
    }

    /**
     * @deprecated Utiliser {@link #estDangereux(AirQuality)} à la place
     */
    @Deprecated
    public static boolean isDangerous(AirQuality airQuality) {
        return estDangereux(airQuality);
    }

    /**
     * @deprecated Utiliser {@link #estSain(AirQuality)} à la place
     */
    @Deprecated
    public static boolean isHealthy(AirQuality airQuality) {
        return estSain(airQuality);
    }

    /**
     * @deprecated Utiliser {@link #obtenirRecommandation(AirQuality)} à la place
     */
    @Deprecated
    public static String getRecommendation(AirQuality airQuality) {
        return obtenirRecommandation(airQuality);
    }

    /**
     * @deprecated Utiliser {@link #calculerScorePollution(AirQuality)} à la place
     */
    @Deprecated
    public static int calculatePollutionScore(AirQuality airQuality) {
        return calculerScorePollution(airQuality);
    }

    /**
     * @deprecated Utiliser {@link #obtenirCodeCouleur(AirQuality)} à la place
     */
    @Deprecated
    public static String getColorCode(AirQuality airQuality) {
        return obtenirCodeCouleur(airQuality);
    }
}

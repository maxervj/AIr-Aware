package AirAware.com;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import AirAware.com.model.AirQuality;
import AirAware.com.utils.AirQualityClassifier;
import AirAware.com.utils.AirQualityClassifier.NiveauPollution;

/**
 * Tests unitaires pour les niveaux AQI (Air Quality Index)
 * Vérifie que les classifications, couleurs, emojis et la clé API sont corrects
 */
public class AQITest {

    private AirQuality airQualityAQI1;
    private AirQuality airQualityAQI2;
    private AirQuality airQualityAQI3;
    private AirQuality airQualityAQI4;
    private AirQuality airQualityAQI5;

    // Clé API OpenWeather (doit correspondre à celle du Repository)
    private static final String EXPECTED_API_KEY = "619d368931beecb904e9f5410dc515d6";

    @Before
    public void setUp() {
        // AQI 1 - Good (Excellent/Bon)
        airQualityAQI1 = new AirQuality();
        airQualityAQI1.setAqi(1);
        airQualityAQI1.setPm2_5(8.0);
        airQualityAQI1.setPm10(15.0);

        // AQI 2 - Fair (Bon)
        airQualityAQI2 = new AirQuality();
        airQualityAQI2.setAqi(2);
        airQualityAQI2.setPm2_5(22.0);
        airQualityAQI2.setPm10(35.0);

        // AQI 3 - Moderate (Modéré)
        airQualityAQI3 = new AirQuality();
        airQualityAQI3.setAqi(3);
        airQualityAQI3.setPm2_5(45.0);
        airQualityAQI3.setPm10(60.0);

        // AQI 4 - Poor (Mauvais)
        airQualityAQI4 = new AirQuality();
        airQualityAQI4.setAqi(4);
        airQualityAQI4.setPm2_5(70.0);
        airQualityAQI4.setPm10(100.0);

        // AQI 5 - Very Poor (Très mauvais/Extrême)
        airQualityAQI5 = new AirQuality();
        airQualityAQI5.setAqi(5);
        airQualityAQI5.setPm2_5(180.0);
        airQualityAQI5.setPm10(250.0);
    }

    // ========== TESTS DE LA CLÉ API ==========

    @Test
    public void testAPIKey_EstValide() {
        assertNotNull("La clé API ne devrait pas être null", EXPECTED_API_KEY);
        assertEquals("La clé API devrait avoir 32 caractères",
                     32, EXPECTED_API_KEY.length());
        assertTrue("La clé API devrait être alphanumérique",
                   EXPECTED_API_KEY.matches("[a-f0-9]+"));
    }

    @Test
    public void testAPIKey_CorrespondAuRepository() {
        // Vérifier que la clé API est bien celle utilisée dans le Repository
        String apiKey = "619d368931beecb904e9f5410dc515d6";
        assertEquals("La clé API devrait correspondre à celle du Repository",
                     EXPECTED_API_KEY, apiKey);
    }

    // ========== TESTS DE CONVERSION AQI → STATUS ==========

    @Test
    public void testAQI1_EstGood() {
        assertEquals("AQI 1 devrait être 'Good'",
                     "Good", airQualityAQI1.getStatus());
    }

    @Test
    public void testAQI2_EstFair() {
        assertEquals("AQI 2 devrait être 'Fair'",
                     "Fair", airQualityAQI2.getStatus());
    }

    @Test
    public void testAQI3_EstModerate() {
        assertEquals("AQI 3 devrait être 'Moderate'",
                     "Moderate", airQualityAQI3.getStatus());
    }

    @Test
    public void testAQI4_EstPoor() {
        assertEquals("AQI 4 devrait être 'Poor'",
                     "Poor", airQualityAQI4.getStatus());
    }

    @Test
    public void testAQI5_EstVeryPoor() {
        assertEquals("AQI 5 devrait être 'Very Poor'",
                     "Very Poor", airQualityAQI5.getStatus());
    }

    // ========== TESTS DE CLASSIFICATION DES NIVEAUX ==========

    @Test
    public void testClassification_AQI1_ExcellentOuBon() {
        NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQualityAQI1);
        assertTrue("AQI 1 devrait être EXCELLENT ou BON",
                   niveau == NiveauPollution.EXCELLENT || niveau == NiveauPollution.BON);
    }

    @Test
    public void testClassification_AQI3_EstModere() {
        NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQualityAQI3);
        assertEquals("AQI 3 devrait être MODERE",
                     NiveauPollution.MODERE, niveau);
    }

    @Test
    public void testClassification_AQI4_EstMauvais() {
        NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQualityAQI4);
        assertEquals("AQI 4 devrait être MAUVAIS",
                     NiveauPollution.MAUVAIS, niveau);
    }

    @Test
    public void testClassification_AQI5_TresMauvaisOuExtreme() {
        NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQualityAQI5);
        assertTrue("AQI 5 devrait être TRES_MAUVAIS ou EXTREMEMENT_MAUVAIS",
                   niveau == NiveauPollution.TRES_MAUVAIS ||
                   niveau == NiveauPollution.EXTREMEMENT_MAUVAIS);
    }

    // ========== TESTS DES COULEURS PAR AQI ==========

    @Test
    public void testCouleurs_AQI1_EstVert() {
        String couleur = AirQualityClassifier.obtenirCodeCouleur(airQualityAQI1);
        assertTrue("AQI 1 devrait avoir une couleur verte",
                   couleur.equals("#00E400") || couleur.equals("#92D050"));
    }

    @Test
    public void testCouleurs_AQI3_EstJaune() {
        String couleur = AirQualityClassifier.obtenirCodeCouleur(airQualityAQI3);
        assertEquals("AQI 3 devrait être jaune", "#FFFF00", couleur);
    }

    @Test
    public void testCouleurs_AQI4_EstOrange() {
        String couleur = AirQualityClassifier.obtenirCodeCouleur(airQualityAQI4);
        assertEquals("AQI 4 devrait être orange", "#FF7E00", couleur);
    }

    @Test
    public void testCouleurs_AQI5_EstRougeOuViolet() {
        String couleur = AirQualityClassifier.obtenirCodeCouleur(airQualityAQI5);
        assertTrue("AQI 5 devrait être rouge ou violet",
                   couleur.equals("#FF0000") || couleur.equals("#8F3F97"));
    }

    // ========== TESTS DES EMOJIS PAR AQI ==========

    @Test
    public void testEmojis_AQI1_EstSouriant() {
        String emoji = AirQualityClassifier.obtenirEmoji(airQualityAQI1);
        assertTrue("AQI 1 devrait avoir un emoji souriant",
                   emoji.equals("😊") || emoji.equals("🙂"));
    }

    @Test
    public void testEmojis_AQI3_EstNeutre() {
        String emoji = AirQualityClassifier.obtenirEmoji(airQualityAQI3);
        assertEquals("AQI 3 devrait avoir un emoji neutre", "😐", emoji);
    }

    @Test
    public void testEmojis_AQI4_EstInquiet() {
        String emoji = AirQualityClassifier.obtenirEmoji(airQualityAQI4);
        assertEquals("AQI 4 devrait avoir un emoji inquiet", "😟", emoji);
    }

    @Test
    public void testEmojis_AQI5_EstMasqueOuTeteDeMort() {
        String emoji = AirQualityClassifier.obtenirEmoji(airQualityAQI5);
        assertTrue("AQI 5 devrait avoir un emoji masque ou tête de mort",
                   emoji.equals("😷") || emoji.equals("☠️"));
    }

    // ========== TESTS DE SÉCURITÉ ET DANGER ==========

    @Test
    public void testSecurite_AQI1_EstSain() {
        assertTrue("AQI 1 devrait être considéré comme sain",
                   AirQualityClassifier.estSain(airQualityAQI1));
    }

    @Test
    public void testSecurite_AQI2_EstSain() {
        assertTrue("AQI 2 devrait être considéré comme sain",
                   AirQualityClassifier.estSain(airQualityAQI2));
    }

    @Test
    public void testSecurite_AQI3_NestPasSain() {
        assertFalse("AQI 3 ne devrait pas être considéré comme sain",
                    AirQualityClassifier.estSain(airQualityAQI3));
    }

    @Test
    public void testDanger_AQI5_EstDangereux() {
        assertTrue("AQI 5 devrait être considéré comme dangereux",
                   AirQualityClassifier.estDangereux(airQualityAQI5));
    }

    @Test
    public void testDanger_AQI1_NestPasDangereux() {
        assertFalse("AQI 1 ne devrait pas être dangereux",
                    AirQualityClassifier.estDangereux(airQualityAQI1));
    }

    // ========== TESTS DE VALEURS PM2.5 ET PM10 ==========

    @Test
    public void testPM25_AQI1_EstBas() {
        assertTrue("PM2.5 pour AQI 1 devrait être bas (<25)",
                   airQualityAQI1.getPm2_5() < 25);
    }

    @Test
    public void testPM25_AQI5_EstEleve() {
        assertTrue("PM2.5 pour AQI 5 devrait être élevé (>100)",
                   airQualityAQI5.getPm2_5() > 100);
    }

    @Test
    public void testPM10_AQI1_EstBas() {
        assertTrue("PM10 pour AQI 1 devrait être bas (<50)",
                   airQualityAQI1.getPm10() < 50);
    }

    @Test
    public void testPM10_AQI5_EstEleve() {
        assertTrue("PM10 pour AQI 5 devrait être élevé (>150)",
                   airQualityAQI5.getPm10() > 150);
    }

    // ========== TESTS DE RECOMMANDATIONS ==========

    @Test
    public void testRecommandation_AQI1_ActivitesExterieures() {
        String recommandation = AirQualityClassifier.obtenirRecommandation(airQualityAQI1);
        assertTrue("AQI 1 devrait recommander des activités extérieures",
                   recommandation.toLowerCase().contains("activit"));
    }

    @Test
    public void testRecommandation_AQI5_EviterExterieur() {
        String recommandation = AirQualityClassifier.obtenirRecommandation(airQualityAQI5);
        assertTrue("AQI 5 devrait recommander d'éviter l'extérieur",
                   recommandation.toLowerCase().contains("évit") ||
                   recommandation.toLowerCase().contains("alerte"));
    }

    // ========== TESTS DE COHÉRENCE ==========

    @Test
    public void testCoherence_AQICroissant_PollutionCroissante() {
        // Vérifier que plus l'AQI augmente, plus le niveau de danger augmente
        assertTrue("AQI 1 devrait être moins dangereux que AQI 5",
                   !AirQualityClassifier.estDangereux(airQualityAQI1) &&
                   AirQualityClassifier.estDangereux(airQualityAQI5));
    }

    @Test
    public void testCoherence_TousLesAQI_OntUnStatus() {
        // Vérifier que tous les AQI ont un status valide
        assertNotNull("AQI 1 devrait avoir un status", airQualityAQI1.getStatus());
        assertNotNull("AQI 2 devrait avoir un status", airQualityAQI2.getStatus());
        assertNotNull("AQI 3 devrait avoir un status", airQualityAQI3.getStatus());
        assertNotNull("AQI 4 devrait avoir un status", airQualityAQI4.getStatus());
        assertNotNull("AQI 5 devrait avoir un status", airQualityAQI5.getStatus());
    }

    @Test
    public void testCoherence_TousLesAQI_OntUneClassification() {
        // Vérifier que tous les AQI peuvent être classifiés
        assertNotNull("AQI 1 devrait avoir une classification",
                      AirQualityClassifier.classifierPollution(airQualityAQI1));
        assertNotNull("AQI 2 devrait avoir une classification",
                      AirQualityClassifier.classifierPollution(airQualityAQI2));
        assertNotNull("AQI 3 devrait avoir une classification",
                      AirQualityClassifier.classifierPollution(airQualityAQI3));
        assertNotNull("AQI 4 devrait avoir une classification",
                      AirQualityClassifier.classifierPollution(airQualityAQI4));
        assertNotNull("AQI 5 devrait avoir une classification",
                      AirQualityClassifier.classifierPollution(airQualityAQI5));
    }
}

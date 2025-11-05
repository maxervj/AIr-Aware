package AirAware.com.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AirAware.com.model.AirQuality;
import AirAware.com.model.HealthRecommendation;
import AirAware.com.utils.AirQualityClassifier.NiveauPollution;

/**
 * Générateur de recommandations santé détaillées basées sur la qualité de l'air
 */
public class HealthRecommendationGenerator {

    /**
     * Génère une recommandation santé complète basée sur la qualité de l'air
     */
    public static HealthRecommendation genererRecommandation(AirQuality airQuality) {
        NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQuality);

        switch (niveau) {
            case EXCELLENT:
                return creerRecommendationExcellent();
            case BON:
                return creerRecommendationBon();
            case MODERE:
                return creerRecommendationModere();
            case MAUVAIS:
                return creerRecommendationMauvais();
            case TRES_MAUVAIS:
                return creerRecommendationTresMauvais();
            case EXTREMEMENT_MAUVAIS:
                return creerRecommendationExtremementMauvais();
            default:
                return creerRecommendationParDefaut();
        }
    }

    private static HealthRecommendation creerRecommendationExcellent() {
        return new HealthRecommendation(
            NiveauPollution.EXCELLENT,
            "La qualité de l'air est excellente. C'est le moment idéal pour profiter de l'extérieur et pratiquer toutes vos activités en plein air.",
            new ArrayList<>(), // Aucun groupe à risque
            new ArrayList<>(), // Aucun symptôme à surveiller
            Arrays.asList(
                "Profitez de l'air frais pour aérer votre domicile",
                "Idéal pour les activités sportives en extérieur",
                "Encouragez les enfants à jouer dehors"
            ),
            "✅ Toutes les activités physiques sont recommandées, y compris les sports intenses et de longue durée.",
            0 // Niveau de risque minimal
        );
    }

    private static HealthRecommendation creerRecommendationBon() {
        return new HealthRecommendation(
            NiveauPollution.BON,
            "La qualité de l'air est bonne. Les activités extérieures sont sûres pour tout le monde.",
            Arrays.asList(
                "Les personnes exceptionnellement sensibles peuvent ressentir une légère gêne"
            ),
            new ArrayList<>(), // Symptômes rares
            Arrays.asList(
                "Aucune précaution particulière nécessaire",
                "Fenêtres peuvent être ouvertes pour ventiler",
                "Activités de plein air recommandées"
            ),
            "✅ Vous pouvez pratiquer toutes les activités physiques sans restriction, y compris course à pied, vélo et sports intenses.",
            1 // Niveau de risque très faible
        );
    }

    private static HealthRecommendation creerRecommendationModere() {
        return new HealthRecommendation(
            NiveauPollution.MODERE,
            "La qualité de l'air est acceptable. La plupart des personnes peuvent profiter de leurs activités habituelles, mais les personnes sensibles devraient limiter les efforts prolongés.",
            Arrays.asList(
                "👶 Enfants et nourrissons",
                "🧓 Personnes âgées (65 ans et plus)",
                "🫁 Personnes asthmatiques ou souffrant de problèmes respiratoires",
                "❤️ Personnes avec maladies cardiovasculaires",
                "🤰 Femmes enceintes"
            ),
            Arrays.asList(
                "Essoufflement inhabituel lors d'activités légères",
                "Irritation des yeux ou de la gorge",
                "Toux légère ou gêne respiratoire",
                "Fatigue inhabituelle"
            ),
            Arrays.asList(
                "Personnes sensibles: réduire les efforts intenses et prolongés",
                "Surveiller l'apparition de symptômes respiratoires",
                "Garder les inhalateurs à portée de main (asthmatiques)",
                "Limiter la durée des activités extérieures intenses",
                "Privilégier les heures où la pollution est plus faible"
            ),
            "⚠️ Population générale: activités normales acceptables\n" +
            "⚠️ Personnes sensibles: limiter les activités prolongées ou très intenses à l'extérieur (course longue distance, cyclisme intensif)",
            2 // Niveau de risque modéré
        );
    }

    private static HealthRecommendation creerRecommendationMauvais() {
        return new HealthRecommendation(
            NiveauPollution.MAUVAIS,
            "La qualité de l'air est mauvaise. Tout le monde peut commencer à ressentir des effets sur la santé. Les personnes sensibles peuvent être plus sérieusement affectées.",
            Arrays.asList(
                "👶 Enfants et nourrissons (RISQUE ÉLEVÉ)",
                "🧓 Personnes âgées de 65 ans et plus (RISQUE ÉLEVÉ)",
                "🫁 Asthmatiques et personnes avec problèmes respiratoires (RISQUE ÉLEVÉ)",
                "❤️ Personnes avec maladies cardiaques (RISQUE ÉLEVÉ)",
                "🤰 Femmes enceintes (RISQUE ÉLEVÉ)",
                "🏃 Sportifs pratiquant des activités intenses",
                "🤧 Personnes avec allergies respiratoires"
            ),
            Arrays.asList(
                "Difficulté à respirer ou essoufflement",
                "Toux persistante ou aggravation de la toux",
                "Sifflements respiratoires (wheezing)",
                "Irritation sévère des yeux, du nez ou de la gorge",
                "Douleurs thoraciques ou oppression",
                "Maux de tête",
                "Fatigue anormale",
                "Rythme cardiaque irrégulier"
            ),
            Arrays.asList(
                "🏠 Rester à l'intérieur autant que possible",
                "🪟 Fermer les fenêtres et portes",
                "💨 Utiliser un purificateur d'air si disponible",
                "😷 Porter un masque FFP2/N95 si sortie nécessaire",
                "💊 Asthmatiques: avoir son inhalateur à portée de main",
                "🚗 Éviter les zones de fort trafic routier",
                "👥 Éviter les foules en extérieur",
                "🩺 Consulter un médecin si symptômes persistants"
            ),
            "🚫 Tout le monde: ÉVITER les activités physiques intenses à l'extérieur\n" +
            "⚠️ Personnes sensibles: AUCUNE activité physique extérieure\n" +
            "✅ Privilégier les activités intérieures légères",
            3 // Niveau de risque élevé
        );
    }

    private static HealthRecommendation creerRecommendationTresMauvais() {
        return new HealthRecommendation(
            NiveauPollution.TRES_MAUVAIS,
            "ALERTE SANTÉ: La qualité de l'air est très mauvaise. Tout le monde peut ressentir des effets importants sur la santé. Les personnes sensibles doivent éviter toute exposition extérieure.",
            Arrays.asList(
                "⚠️ TOUTE LA POPULATION est à risque",
                "🆘 RISQUE CRITIQUE pour:",
                "  • Enfants et nourrissons",
                "  • Personnes âgées",
                "  • Asthmatiques et personnes avec maladies respiratoires",
                "  • Personnes avec maladies cardiovasculaires",
                "  • Femmes enceintes",
                "  • Personnes immunodéprimées"
            ),
            Arrays.asList(
                "⚠️ Difficulté respiratoire importante",
                "⚠️ Toux sévère et persistante",
                "⚠️ Douleurs thoraciques",
                "⚠️ Palpitations cardiaques",
                "⚠️ Vertiges ou confusion",
                "⚠️ Nausées",
                "⚠️ Aggravation rapide de conditions existantes",
                "⚠️ Crise d'asthme",
                "⚠️ Irritation sévère des muqueuses"
            ),
            Arrays.asList(
                "🏠 RESTER À L'INTÉRIEUR - Sortir uniquement si absolument nécessaire",
                "🚪 FERMER toutes fenêtres et portes",
                "💨 UTILISER un purificateur d'air H13/HEPA",
                "😷 PORTER OBLIGATOIREMENT un masque FFP2/N95 lors des sorties",
                "💊 Asthmatiques: utiliser l'inhalateur préventivement selon prescription",
                "📱 Limiter les déplacements, privilégier télétravail/école à distance",
                "🚗 Éviter tout trajet non essentiel",
                "🩺 CONSULTER RAPIDEMENT en cas de symptômes",
                "🆘 Appeler urgences (15) si difficulté respiratoire sévère",
                "💧 S'hydrater régulièrement",
                "🧘 Rester calme, respirer lentement si gêne respiratoire"
            ),
            "🚫 TOUTE LA POPULATION: AUCUNE activité physique extérieure\n" +
            "🏠 Rester à l'intérieur et limiter les mouvements\n" +
            "❌ Annuler tout événement sportif ou activité extérieure prévue",
            4 // Niveau de risque très élevé
        );
    }

    private static HealthRecommendation creerRecommendationExtremementMauvais() {
        return new HealthRecommendation(
            NiveauPollution.EXTREMEMENT_MAUVAIS,
            "🆘 URGENCE SANITAIRE: La qualité de l'air présente un danger grave pour la santé. Conditions d'urgence sanitaire - tout le monde est affecté.",
            Arrays.asList(
                "🚨 DANGER POUR TOUTE LA POPULATION",
                "🆘 RISQUE VITAL pour les groupes vulnérables:",
                "  • Enfants et nourrissons",
                "  • Personnes âgées",
                "  • Personnes avec maladies respiratoires",
                "  • Personnes avec maladies cardiaques",
                "  • Femmes enceintes",
                "  • Personnes immunodéprimées",
                "  • Travailleurs extérieurs"
            ),
            Arrays.asList(
                "🆘 Détresse respiratoire sévère",
                "🆘 Douleur thoracique intense",
                "🆘 Arythmie cardiaque",
                "🆘 Confusion ou perte de conscience",
                "🆘 Lèvres ou ongles bleutés (cyanose)",
                "🆘 Crise d'asthme sévère ne répondant pas au traitement",
                "🆘 Vomissements persistants",
                "🆘 Saignements de nez répétés",
                "🆘 Vision trouble ou maux de tête violents"
            ),
            Arrays.asList(
                "🆘 CONFINEMENT STRICT À L'INTÉRIEUR - Ne sortez sous AUCUN prétexte sauf urgence vitale",
                "🚪 COLMATER fenêtres et portes avec serviettes humides",
                "💨 PURIFICATEUR D'AIR en marche constante (filtres HEPA H13)",
                "😷 Masque FFP3/N99 OBLIGATOIRE si sortie inévitable",
                "🏥 Contacter les autorités sanitaires pour évacuation si nécessaire",
                "💊 Prendre médicaments préventifs selon prescriptions",
                "📞 Maintenir contact avec proches et services médicaux",
                "🆘 APPELER LE 15 IMMÉDIATEMENT si symptômes graves",
                "💧 S'hydrater abondamment",
                "🧼 Se laver yeux/nez/gorge si exposition",
                "📻 Suivre les consignes des autorités sanitaires",
                "🏫 Fermeture écoles et lieux publics recommandée",
                "🚗 Évacuation possible selon consignes officielles"
            ),
            "🚨 INTERDICTION TOTALE de toute activité extérieure\n" +
            "🏠 CONFINEMENT STRICT recommandé\n" +
            "⛔ NE PAS SORTIR - Même les activités intérieures doivent être réduites\n" +
            "🆘 Urgence sanitaire - Suivre les directives officielles",
            5 // Niveau de risque maximal
        );
    }

    private static HealthRecommendation creerRecommendationParDefaut() {
        return new HealthRecommendation(
            NiveauPollution.MODERE,
            "Données insuffisantes pour évaluer la qualité de l'air. Par précaution, limitez les activités intenses.",
            Arrays.asList("Personnes sensibles"),
            new ArrayList<>(),
            Arrays.asList("Rester vigilant aux symptômes", "Consulter les mises à jour"),
            "⚠️ Par précaution, limiter les activités physiques intenses jusqu'à obtention de données complètes.",
            2
        );
    }

    /**
     * Génère un résumé court de la recommandation (pour affichage compact)
     */
    public static String genererResumeCourt(AirQuality airQuality) {
        NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQuality);

        switch (niveau) {
            case EXCELLENT:
                return "✅ Air pur - Toutes activités recommandées";
            case BON:
                return "✅ Bonne qualité - Activités normales";
            case MODERE:
                return "⚠️ Qualité acceptable - Sensibles: limiter efforts prolongés";
            case MAUVAIS:
                return "🚫 Mauvaise qualité - Réduire activités extérieures";
            case TRES_MAUVAIS:
                return "⛔ Très mauvais - Éviter sorties, rester à l'intérieur";
            case EXTREMEMENT_MAUVAIS:
                return "🆘 URGENCE - Confinement strict recommandé";
            default:
                return "⚠️ Données insuffisantes";
        }
    }

    /**
     * Retourne l'icône de risque appropriée
     */
    public static String obtenirIconeRisque(AirQuality airQuality) {
        NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQuality);

        switch (niveau) {
            case EXCELLENT:
            case BON:
                return "✅";
            case MODERE:
                return "⚠️";
            case MAUVAIS:
                return "🚫";
            case TRES_MAUVAIS:
                return "⛔";
            case EXTREMEMENT_MAUVAIS:
                return "🆘";
            default:
                return "❓";
        }
    }
}

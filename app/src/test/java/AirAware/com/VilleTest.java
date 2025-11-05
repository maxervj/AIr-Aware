package AirAware.com;

import org.junit.Test;

import static org.junit.Assert.*;

import AirAware.com.model.City;

/**
 * Tests unitaires pour les villes (City)
 * Vérifie que les 12 villes ont des coordonnées valides et cohérentes
 */
public class VilleTest {

    // ========== TESTS DE LA LISTE DES VILLES ==========

    @Test
    public void testVilles_ListeNonNulle() {
        City[] cities = City.getAvailableCities();
        assertNotNull("La liste des villes ne devrait pas être null", cities);
    }

    @Test
    public void testVilles_ListeNonVide() {
        City[] cities = City.getAvailableCities();
        assertTrue("La liste des villes devrait contenir au moins une ville",
                   cities.length > 0);
    }

    @Test
    public void testVilles_Contient12Villes() {
        City[] cities = City.getAvailableCities();
        assertEquals("La liste devrait contenir exactement 12 villes",
                     12, cities.length);
    }

    // ========== TESTS DES VILLES SPÉCIFIQUES ==========

    @Test
    public void testVille_Paris_Existe() {
        City[] cities = City.getAvailableCities();
        boolean parisFound = false;

        for (City city : cities) {
            if (city.getName().contains("Paris")) {
                parisFound = true;
                // Vérifier les coordonnées de Paris
                assertEquals("Latitude de Paris", 48.8566, city.getLatitude(), 0.0001);
                assertEquals("Longitude de Paris", 2.3522, city.getLongitude(), 0.0001);
                break;
            }
        }

        assertTrue("Paris devrait être dans la liste", parisFound);
    }

    @Test
    public void testVille_NewDelhi_Existe() {
        City[] cities = City.getAvailableCities();
        boolean delhiFound = false;

        for (City city : cities) {
            if (city.getName().contains("New Delhi")) {
                delhiFound = true;
                // Vérifier les coordonnées de New Delhi
                assertEquals("Latitude de New Delhi", 28.6139, city.getLatitude(), 0.0001);
                assertEquals("Longitude de New Delhi", 77.2090, city.getLongitude(), 0.0001);
                break;
            }
        }

        assertTrue("New Delhi devrait être dans la liste", delhiFound);
    }

    @Test
    public void testVille_Beijing_Existe() {
        City[] cities = City.getAvailableCities();
        boolean beijingFound = false;

        for (City city : cities) {
            if (city.getName().contains("Beijing")) {
                beijingFound = true;
                assertEquals("Latitude de Beijing", 39.9042, city.getLatitude(), 0.0001);
                assertEquals("Longitude de Beijing", 116.4074, city.getLongitude(), 0.0001);
                break;
            }
        }

        assertTrue("Beijing devrait être dans la liste", beijingFound);
    }

    @Test
    public void testVille_Reykjavik_Existe() {
        City[] cities = City.getAvailableCities();
        boolean reykjavikFound = false;

        for (City city : cities) {
            if (city.getName().contains("Reykjavik")) {
                reykjavikFound = true;
                assertEquals("Latitude de Reykjavik", 64.1466, city.getLatitude(), 0.0001);
                assertEquals("Longitude de Reykjavik", -21.9426, city.getLongitude(), 0.0001);
                break;
            }
        }

        assertTrue("Reykjavik devrait être dans la liste", reykjavikFound);
    }

    @Test
    public void testVille_Sydney_HemisphereSud() {
        City[] cities = City.getAvailableCities();
        boolean sydneyFound = false;

        for (City city : cities) {
            if (city.getName().contains("Sydney")) {
                sydneyFound = true;
                assertTrue("Sydney devrait avoir une latitude négative (hémisphère sud)",
                           city.getLatitude() < 0);
                assertEquals("Latitude de Sydney", -33.8688, city.getLatitude(), 0.0001);
                assertEquals("Longitude de Sydney", 151.2093, city.getLongitude(), 0.0001);
                break;
            }
        }

        assertTrue("Sydney devrait être dans la liste", sydneyFound);
    }

    // ========== TESTS DE VALIDITÉ DES COORDONNÉES ==========

    @Test
    public void testCoordonnees_LatitudeValide() {
        City[] cities = City.getAvailableCities();

        for (City city : cities) {
            double lat = city.getLatitude();
            assertTrue("Latitude de " + city.getName() + " devrait être entre -90 et 90",
                       lat >= -90 && lat <= 90);
        }
    }

    @Test
    public void testCoordonnees_LongitudeValide() {
        City[] cities = City.getAvailableCities();

        for (City city : cities) {
            double lon = city.getLongitude();
            assertTrue("Longitude de " + city.getName() + " devrait être entre -180 et 180",
                       lon >= -180 && lon <= 180);
        }
    }

    @Test
    public void testCoordonnees_PasToutes_0_0() {
        City[] cities = City.getAvailableCities();

        for (City city : cities) {
            // Vérifier qu'aucune ville n'a des coordonnées exactement à (0,0)
            boolean coordonneesValides =
                city.getLatitude() != 0.0 || city.getLongitude() != 0.0;

            assertTrue("Les coordonnées de " + city.getName() +
                       " ne devraient pas être toutes nulles (0,0)",
                       coordonneesValides);
        }
    }

    // ========== TESTS DE COHÉRENCE DES DONNÉES ==========

    @Test
    public void testCoherence_NomsUniques() {
        City[] cities = City.getAvailableCities();

        for (int i = 0; i < cities.length; i++) {
            for (int j = i + 1; j < cities.length; j++) {
                assertNotEquals("Les villes ne devraient pas avoir le même nom: " +
                                cities[i].getName(),
                                cities[i].getName(), cities[j].getName());
            }
        }
    }

    @Test
    public void testCoherence_ToutesLesVillesOntUnNom() {
        City[] cities = City.getAvailableCities();

        for (City city : cities) {
            assertNotNull("Chaque ville devrait avoir un nom", city.getName());
            assertFalse("Le nom de la ville ne devrait pas être vide",
                        city.getName().trim().isEmpty());
        }
    }

    @Test
    public void testCoherence_ToStringRetourneLeNom() {
        City paris = new City("Paris, France", 48.8566, 2.3522);
        assertEquals("toString() devrait retourner le nom de la ville",
                     "Paris, France", paris.toString());
    }

    // ========== TESTS DE DIVERSITÉ GÉOGRAPHIQUE ==========

    @Test
    public void testDiversite_HemisphereNord() {
        City[] cities = City.getAvailableCities();
        boolean hemisphereNordTrouve = false;

        for (City city : cities) {
            if (city.getLatitude() > 0) {
                hemisphereNordTrouve = true;
                break;
            }
        }

        assertTrue("Il devrait y avoir au moins une ville dans l'hémisphère nord",
                   hemisphereNordTrouve);
    }

    @Test
    public void testDiversite_HemisphereSud() {
        City[] cities = City.getAvailableCities();
        boolean hemisphereSudTrouve = false;

        for (City city : cities) {
            if (city.getLatitude() < 0) {
                hemisphereSudTrouve = true;
                break;
            }
        }

        assertTrue("Il devrait y avoir au moins une ville dans l'hémisphère sud",
                   hemisphereSudTrouve);
    }

    @Test
    public void testDiversite_LongitudeOuest() {
        City[] cities = City.getAvailableCities();
        boolean ouestTrouve = false;

        for (City city : cities) {
            if (city.getLongitude() < 0) {
                ouestTrouve = true;
                break;
            }
        }

        assertTrue("Il devrait y avoir au moins une ville à l'ouest de Greenwich",
                   ouestTrouve);
    }

    @Test
    public void testDiversite_LongitudeEst() {
        City[] cities = City.getAvailableCities();
        boolean estTrouve = false;

        for (City city : cities) {
            if (city.getLongitude() > 0) {
                estTrouve = true;
                break;
            }
        }

        assertTrue("Il devrait y avoir au moins une ville à l'est de Greenwich",
                   estTrouve);
    }

    @Test
    public void testDiversite_CouvertureMondiale() {
        City[] cities = City.getAvailableCities();

        double minLat = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double minLon = Double.MAX_VALUE;
        double maxLon = Double.MIN_VALUE;

        for (City city : cities) {
            minLat = Math.min(minLat, city.getLatitude());
            maxLat = Math.max(maxLat, city.getLatitude());
            minLon = Math.min(minLon, city.getLongitude());
            maxLon = Math.max(maxLon, city.getLongitude());
        }

        double latRange = maxLat - minLat;
        double lonRange = maxLon - minLon;

        assertTrue("Les villes devraient couvrir au moins 60° de latitude",
                   latRange > 60);
        assertTrue("Les villes devraient couvrir au moins 150° de longitude",
                   lonRange > 150);
    }

    // ========== TESTS DES VILLES POUR DIFFÉRENTS NIVEAUX DE POLLUTION ==========

    @Test
    public void testVilles_PourAirPur() {
        City[] cities = City.getAvailableCities();
        // Reykjavik devrait avoir un air pur (EXCELLENT)
        boolean reykjavikFound = false;

        for (City city : cities) {
            if (city.getName().contains("Reykjavik")) {
                reykjavikFound = true;
                break;
            }
        }

        assertTrue("La liste devrait contenir Reykjavik (air pur)", reykjavikFound);
    }

    @Test
    public void testVilles_PourAirPollue() {
        City[] cities = City.getAvailableCities();
        // Dhaka et New Delhi devraient avoir un air pollué
        boolean villePollueeTrouvee = false;

        for (City city : cities) {
            if (city.getName().contains("Dhaka") ||
                city.getName().contains("New Delhi") ||
                city.getName().contains("Beijing")) {
                villePollueeTrouvee = true;
                break;
            }
        }

        assertTrue("La liste devrait contenir des villes à air pollué",
                   villePollueeTrouvee);
    }

    @Test
    public void testVilles_PourAirModere() {
        City[] cities = City.getAvailableCities();
        // Paris, Londres, Tokyo devraient avoir un air modéré
        boolean villeModereeTrouvee = false;

        for (City city : cities) {
            if (city.getName().contains("Paris") ||
                city.getName().contains("Londres") ||
                city.getName().contains("Tokyo")) {
                villeModereeTrouvee = true;
                break;
            }
        }

        assertTrue("La liste devrait contenir des villes à air modéré",
                   villeModereeTrouvee);
    }

    // ========== TESTS DE CRÉATION D'OBJET CITY ==========

    @Test
    public void testCreation_VillePersonnalisee() {
        City tokyo = new City("Tokyo, Japon", 35.6762, 139.6503);

        assertEquals("Le nom devrait être correct", "Tokyo, Japon", tokyo.getName());
        assertEquals("La latitude devrait être correcte", 35.6762, tokyo.getLatitude(), 0.0001);
        assertEquals("La longitude devrait être correcte", 139.6503, tokyo.getLongitude(), 0.0001);
    }
}

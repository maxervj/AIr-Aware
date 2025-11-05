package AirAware.com.model;

/**
 * Modèle représentant une ville avec ses coordonnées géographiques
 */
public class City {
    private final String name;
    private final double latitude;
    private final double longitude;

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Liste des villes disponibles avec différents niveaux de pollution
     * Classées par ordre alphabétique pour faciliter la navigation
     */
    public static City[] getAvailableCities() {
        return new City[]{
                new City("Beijing, Chine", 39.9042, 116.4074),           // Pollution élevée (Très mauvais)
                new City("Dhaka, Bangladesh", 23.8103, 90.4125),         // Pollution extrême
                new City("Londres, Royaume-Uni", 51.5074, -0.1278),      // Pollution modérée
                new City("Los Angeles, USA", 34.0522, -118.2437),        // Pollution modérée
                new City("Mexico City, Mexique", 19.4326, -99.1332),     // Pollution mauvaise
                new City("Mumbai, Inde", 19.0760, 72.8777),              // Pollution très mauvaise
                new City("New Delhi, Inde", 28.6139, 77.2090),           // Pollution très mauvaise/extrême
                new City("Paris, France", 48.8566, 2.3522),              // Pollution modérée
                new City("Reykjavik, Islande", 64.1466, -21.9426),       // Excellent (air pur)
                new City("Sydney, Australie", -33.8688, 151.2093),       // Bonne qualité d'air
                new City("Tokyo, Japon", 35.6762, 139.6503),             // Pollution modérée
                new City("Zurich, Suisse", 47.3769, 8.5417)              // Bonne qualité d'air
        };
    }
}

package AirAware.com.model;

/**
 * Modèle de données pour la qualité de l'air (OpenWeather Air Pollution API)
 */
public class AirQuality {
    private double latitude;
    private double longitude;
    private String location;
    private int aqi; // Air Quality Index: 1=Good, 2=Fair, 3=Moderate, 4=Poor, 5=Very Poor
    private String status; // Good, Fair, Moderate, Poor, Very Poor
    private long timestamp;

    // Components de pollution (en μg/m3)
    private double co;      // Carbon monoxide
    private double no;      // Nitrogen monoxide
    private double no2;     // Nitrogen dioxide
    private double o3;      // Ozone
    private double so2;     // Sulphur dioxide
    private double pm2_5;   // Fine particles matter
    private double pm10;    // Coarse particulate matter
    private double nh3;     // Ammonia

    public AirQuality() {
    }

    // Getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }

    public int getAqi() {
        return aqi;
    }

    public String getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getCo() {
        return co;
    }

    public double getNo() {
        return no;
    }

    public double getNo2() {
        return no2;
    }

    public double getO3() {
        return o3;
    }

    public double getSo2() {
        return so2;
    }

    public double getPm2_5() {
        return pm2_5;
    }

    public double getPm10() {
        return pm10;
    }

    public double getNh3() {
        return nh3;
    }

    // Setters
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
        this.status = getStatusFromAqi(aqi);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public void setNo(double no) {
        this.no = no;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public void setPm2_5(double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public void setNh3(double nh3) {
        this.nh3 = nh3;
    }

    /**
     * Convertit l'AQI numérique en statut textuel
     */
    private String getStatusFromAqi(int aqi) {
        switch (aqi) {
            case 1:
                return "Good";
            case 2:
                return "Fair";
            case 3:
                return "Moderate";
            case 4:
                return "Poor";
            case 5:
                return "Very Poor";
            default:
                return "Unknown";
        }
    }
}

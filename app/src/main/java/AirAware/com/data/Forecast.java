package AirAware.com.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Classe pour les prévisions de pollution de l'air (Forecast)
 * Basée sur l'API OpenWeather Air Pollution Forecast
 */
public class Forecast {
    @SerializedName("coord")
    private Coord coord;

    @SerializedName("list")
    private List<ForecastData> list;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<ForecastData> getList() {
        return list;
    }

    public void setList(List<ForecastData> list) {
        this.list = list;
    }

    public static class Coord {
        @SerializedName("lon")
        private double lon;

        @SerializedName("lat")
        private double lat;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    public static class ForecastData {
        @SerializedName("dt")
        private long dt;

        @SerializedName("main")
        private Main main;

        @SerializedName("components")
        private Components components;

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public Components getComponents() {
            return components;
        }

        public void setComponents(Components components) {
            this.components = components;
        }
    }

    public static class Main {
        @SerializedName("aqi")
        private int aqi;

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }
    }

    public static class Components {
        @SerializedName("co")
        private double co;

        @SerializedName("no")
        private double no;

        @SerializedName("no2")
        private double no2;

        @SerializedName("o3")
        private double o3;

        @SerializedName("so2")
        private double so2;

        @SerializedName("pm2_5")
        private double pm2_5;

        @SerializedName("pm10")
        private double pm10;

        @SerializedName("nh3")
        private double nh3;

        public double getCo() {
            return co;
        }

        public void setCo(double co) {
            this.co = co;
        }

        public double getNo() {
            return no;
        }

        public void setNo(double no) {
            this.no = no;
        }

        public double getNo2() {
            return no2;
        }

        public void setNo2(double no2) {
            this.no2 = no2;
        }

        public double getO3() {
            return o3;
        }

        public void setO3(double o3) {
            this.o3 = o3;
        }

        public double getSo2() {
            return so2;
        }

        public void setSo2(double so2) {
            this.so2 = so2;
        }

        public double getPm2_5() {
            return pm2_5;
        }

        public void setPm2_5(double pm2_5) {
            this.pm2_5 = pm2_5;
        }

        public double getPm10() {
            return pm10;
        }

        public void setPm10(double pm10) {
            this.pm10 = pm10;
        }

        public double getNh3() {
            return nh3;
        }

        public void setNh3(double nh3) {
            this.nh3 = nh3;
        }
    }
}

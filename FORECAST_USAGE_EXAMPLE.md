# Utilisation de l'API de Prévisions de Pollution

## Vue d'ensemble

Le code a été adapté pour suivre l'architecture MVVM et utiliser Hilt pour l'injection de dépendances. La méthode `fetchAirPollutionForecast` permet de récupérer les prévisions de pollution de l'air pour les 5 prochains jours.

## Architecture mise en place

### 1. Classe de données : `Forecast.java`

Classe pour représenter les prévisions de pollution (similaire à `AirPollutionResponse` mais spécifique aux prévisions).

**Structure :**
```java
Forecast
  ├── Coord (latitude, longitude)
  └── List<ForecastData>
       ├── dt (timestamp)
       ├── Main (aqi)
       └── Components (co, no, no2, o3, so2, pm2_5, pm10, nh3)
```

### 2. Service API : `OpenWeatherApiService.java`

Interface Retrofit mise à jour avec la méthode :
```java
@GET("air_pollution/forecast")
Call<Forecast> getForecastAirPollution(
    @Query("lat") double lat,
    @Query("lon") double lon,
    @Query("appid") String appid
);
```

### 3. Repository : `AirQualityRepository.java`

Nouvelle méthode ajoutée :
```java
public LiveData<Forecast> getForecastData(double latitude, double longitude)
```

**Fonctionnalités :**
- Gestion asynchrone des appels API
- Gestion des erreurs et état de chargement
- Logging des succès/erreurs
- Retourne un LiveData observable

### 4. ViewModel : `AirQualityViewModel.java`

Méthode refactorisée pour respecter MVVM :
```java
public LiveData<Forecast> fetchAirPollutionForecast(double latitude, double longitude)
```

**Changements apportés :**
- ✅ Suppression de l'appel direct à l'API
- ✅ Utilisation du Repository (respect de l'architecture)
- ✅ Suppression du paramètre `apiKey` (géré dans le Repository)
- ✅ Nettoyage des imports inutiles
- ✅ Ajout de logging approprié
- ✅ Injection de dépendances avec Hilt

## Utilisation dans une Activity

### Exemple dans MainActivity

```java
public class MainActivity extends AppCompatActivity {
    private AirQualityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser le ViewModel (avec Hilt)
        viewModel = new ViewModelProvider(this).get(AirQualityViewModel.class);

        // Charger les prévisions pour Paris
        loadForecastData(48.8566, 2.3522);
    }

    private void loadForecastData(double latitude, double longitude) {
        // Observer les prévisions
        viewModel.fetchAirPollutionForecast(latitude, longitude)
            .observe(this, forecast -> {
                if (forecast != null && forecast.getList() != null) {
                    Log.d("MainActivity", "Prévisions reçues: " +
                          forecast.getList().size() + " points de données");

                    // Afficher les prévisions
                    displayForecast(forecast);
                } else {
                    Log.e("MainActivity", "Aucune prévision disponible");
                }
            });

        // Observer les erreurs
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, "Erreur: " + error, Toast.LENGTH_LONG).show();
            }
        });

        // Observer l'état de chargement
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                // Afficher/masquer un indicateur de chargement
                showLoading(isLoading);
            }
        });
    }

    private void displayForecast(Forecast forecast) {
        List<Forecast.ForecastData> forecastList = forecast.getList();

        for (Forecast.ForecastData data : forecastList) {
            // Récupérer les informations
            long timestamp = data.getDt();
            int aqi = data.getMain().getAqi();
            double pm25 = data.getComponents().getPm2_5();
            double pm10 = data.getComponents().getPm10();

            // Convertir le timestamp en date
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date(timestamp * 1000L));

            Log.d("Forecast", String.format(
                "Date: %s | AQI: %d | PM2.5: %.1f | PM10: %.1f",
                date, aqi, pm25, pm10
            ));

            // TODO: Afficher dans l'UI (RecyclerView, etc.)
        }
    }

    private void showLoading(boolean isLoading) {
        // Afficher/masquer un ProgressBar
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
```

## Différences avec l'ancien code

### ❌ Ancien code (non MVVM)
```java
// Appel direct à l'API depuis le ViewModel
Call<Forecast> call = openWeatherServiceRepo.getAirPollutionForecast(lat, lon, apiKey);
call.enqueue(new Callback<Forecast>() {
    // Gestion manuelle des callbacks
});
```

**Problèmes :**
- Violation de l'architecture MVVM (logique réseau dans le ViewModel)
- Référence directe au service API
- Clé API exposée dans les paramètres
- Gestion d'erreur dupliquée

### ✅ Nouveau code (MVVM propre)
```java
// Délégation au Repository
return repository.getForecastData(latitude, longitude);
```

**Avantages :**
- Respect de l'architecture MVVM
- Injection de dépendances avec Hilt
- Clé API cachée dans le Repository
- Gestion centralisée des erreurs
- Code plus testable

## Structure complète du flux de données

```
MainActivity (@AndroidEntryPoint)
    ↓ observe
AirQualityViewModel (@HiltViewModel)
    ↓ appelle
AirQualityRepository (@Singleton)
    ↓ utilise
OpenWeatherApiService (interface Retrofit)
    ↓ appelle
API OpenWeather Air Pollution Forecast
```

## Format de réponse API

L'API retourne des prévisions pour environ 96 heures (4 jours) avec des points de données toutes les heures.

**Exemple de réponse :**
```json
{
  "coord": {
    "lon": 2.3522,
    "lat": 48.8566
  },
  "list": [
    {
      "dt": 1698768000,
      "main": {
        "aqi": 2
      },
      "components": {
        "co": 280.35,
        "no": 0.16,
        "no2": 21.14,
        "o3": 68.66,
        "so2": 2.65,
        "pm2_5": 8.53,
        "pm10": 12.45,
        "nh3": 1.12
      }
    },
    // ... 95 autres points de données
  ]
}
```

## Interprétation des prévisions

### AQI (Air Quality Index)
- **1** : Good (Bon)
- **2** : Fair (Moyen)
- **3** : Moderate (Modéré)
- **4** : Poor (Mauvais)
- **5** : Very Poor (Très mauvais)

### Polluants (μg/m³)
- **PM2.5** : Particules fines (<2.5 μm)
- **PM10** : Particules grossières (<10 μm)
- **NO2** : Dioxyde d'azote (pollution du trafic)
- **O3** : Ozone
- **CO** : Monoxyde de carbone
- **SO2** : Dioxyde de soufre
- **NH3** : Ammoniac

## Affichage dans l'UI

### Exemple avec RecyclerView

```java
// Adapter pour afficher les prévisions
public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder> {
    private List<Forecast.ForecastData> forecastList;

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        Forecast.ForecastData data = forecastList.get(position);

        // Afficher la date
        String date = formatDate(data.getDt());
        holder.dateTextView.setText(date);

        // Afficher l'AQI avec couleur
        int aqi = data.getMain().getAqi();
        holder.aqiTextView.setText("AQI: " + aqi);
        holder.aqiTextView.setBackgroundColor(getAQIColor(aqi));

        // Afficher PM2.5 et PM10
        holder.pm25TextView.setText(String.format("PM2.5: %.1f μg/m³",
            data.getComponents().getPm2_5()));
        holder.pm10TextView.setText(String.format("PM10: %.1f μg/m³",
            data.getComponents().getPm10()));
    }

    private int getAQIColor(int aqi) {
        switch (aqi) {
            case 1: return Color.parseColor("#00E400"); // Vert
            case 2: return Color.parseColor("#92D050"); // Vert clair
            case 3: return Color.parseColor("#FFFF00"); // Jaune
            case 4: return Color.parseColor("#FF7E00"); // Orange
            case 5: return Color.parseColor("#FF0000"); // Rouge
            default: return Color.GRAY;
        }
    }
}
```

## Tests

Pour tester la fonctionnalité :

```java
@Test
public void testFetchForecast() {
    // Coordonnées de Paris
    double latitude = 48.8566;
    double longitude = 2.3522;

    // Récupérer les prévisions
    LiveData<Forecast> forecastLiveData =
        viewModel.fetchAirPollutionForecast(latitude, longitude);

    // Observer les résultats
    forecastLiveData.observeForever(forecast -> {
        assertNotNull(forecast);
        assertNotNull(forecast.getList());
        assertTrue(forecast.getList().size() > 0);
    });
}
```

## Ressources

- [OpenWeather Air Pollution API Docs](https://openweathermap.org/api/air-pollution)
- [Architecture MVVM Android](https://developer.android.com/topic/architecture)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [LiveData Overview](https://developer.android.com/topic/libraries/architecture/livedata)

## Prochaines étapes

1. Créer une interface graphique pour afficher les prévisions
2. Ajouter un graphique pour visualiser l'évolution de la pollution
3. Implémenter des notifications pour alerter sur les pics de pollution prévus
4. Ajouter la possibilité de changer de ville
5. Mettre en cache les prévisions pour une utilisation hors ligne

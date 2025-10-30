# Configuration de l'API OpenWeather

## Obtenir une clé API

1. Allez sur [OpenWeather](https://openweathermap.org/)
2. Créez un compte gratuit
3. Allez dans "API keys" dans votre profil
4. Copiez votre clé API

## Configuration du projet

1. Ouvrez le fichier `AirQualityRepository.java` situé dans :
   ```
   app/src/main/java/AirAware/com/repository/AirQualityRepository.java
   ```

2. Remplacez `VOTRE_CLE_API_ICI` par votre clé API OpenWeather à la ligne 31 :
   ```java
   private static final String API_KEY = "votre_cle_api_ici";
   ```

## API OpenWeather Air Pollution

L'application utilise l'API Air Pollution d'OpenWeather qui fournit :

### Données disponibles

- **AQI (Air Quality Index)** : Indice de qualité de l'air (1-5)
  - 1 = Good (Bon)
  - 2 = Fair (Moyen)
  - 3 = Moderate (Modéré)
  - 4 = Poor (Mauvais)
  - 5 = Very Poor (Très mauvais)

- **Composants de pollution** (en μg/m³) :
  - CO : Monoxyde de carbone
  - NO : Monoxyde d'azote
  - NO2 : Dioxyde d'azote
  - O3 : Ozone
  - SO2 : Dioxyde de soufre
  - PM2.5 : Particules fines
  - PM10 : Particules grossières
  - NH3 : Ammoniac

### Endpoints utilisés

- **Current Air Pollution** : `/data/2.5/air_pollution`
  - Paramètres : lat, lon, appid
  - Retourne les données de pollution actuelles

- **Forecast Air Pollution** : `/data/2.5/air_pollution/forecast`
  - Prévisions de pollution pour les 5 prochains jours

- **Historical Air Pollution** : `/data/2.5/air_pollution/history`
  - Historique de pollution

## Structure de l'architecture MVVM

```
AirAware.com/
├── model/          - AirQuality.java (modèle de données)
├── data/           - AirPollutionResponse.java (réponse API)
├── network/        - OpenWeatherApiService.java, RetrofitClient.java
├── repository/     - AirQualityRepository.java (gestion des données)
├── ui/             - MainActivity.java (interface utilisateur)
└── viewmodel/      - AirQualityViewModel.java (logique de présentation)
```

## Modifier la localisation par défaut

Dans `MainActivity.java` (lignes 27-29), vous pouvez modifier les coordonnées :

```java
private static final double DEFAULT_LATITUDE = 48.8566;  // Paris
private static final double DEFAULT_LONGITUDE = 2.3522;
private static final String DEFAULT_LOCATION = "Paris";
```

## Permissions requises

Les permissions suivantes sont déjà configurées dans `AndroidManifest.xml` :
- `INTERNET` : Pour les appels API
- `ACCESS_NETWORK_STATE` : Pour vérifier l'état de la connexion

## Plan tarifaire OpenWeather (gratuit)

- 60 appels/minute
- 1,000,000 appels/mois
- Données actuelles et prévisions
- Aucune carte de crédit requise

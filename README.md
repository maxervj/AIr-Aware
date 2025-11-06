# AIrAware - Application de Surveillance de la Qualité de l'Air

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Min SDK](https://img.shields.io/badge/Min%20SDK-30-blue.svg)
![Target SDK](https://img.shields.io/badge/Target%20SDK-36-blue.svg)
![Language](https://img.shields.io/badge/Language-Java-orange.svg)

## Table des matières
- [Présentation](#présentation)
- [Fonctionnalités](#fonctionnalités)
- [Architecture](#architecture)
- [Technologies utilisées](#technologies-utilisées)
- [Installation et configuration](#installation-et-configuration)
- [Structure du projet](#structure-du-projet)
- [Utilisation](#utilisation)
- [Système de classification](#système-de-classification)
- [Tests](#tests)
- [Documentation](#documentation)
- [Roadmap](#roadmap)

## Présentation

**AIrAware** est une application Android moderne qui fournit des informations en temps réel sur la qualité de l'air en utilisant l'API OpenWeather Air Pollution. L'application aide les utilisateurs à surveiller les niveaux de pollution atmosphérique et à recevoir des recommandations de santé basées sur les données actuelles de qualité de l'air.

### Objectif
Permettre aux utilisateurs de prendre des décisions éclairées concernant leurs activités extérieures en fonction de la qualité de l'air, avec une interface intuitive et des recommandations personnalisées en français.

## Fonctionnalités

### Fonctionnalités actuelles ✅
- **Surveillance en temps réel** : Affichage des données de pollution atmosphérique actuelles pour des emplacements spécifiques
- **Indice de Qualité de l'Air (IQA)** : Niveaux de pollution de 1 (Bon) à 5 (Très Mauvais)
- **Détails des polluants** : Suivi de 8 polluants différents :
  - PM2.5 (Particules fines)
  - PM10 (Particules grossières)
  - CO (Monoxyde de carbone)
  - NO (Monoxyde d'azote)
  - NO2 (Dioxyde d'azote)
  - O3 (Ozone)
  - SO2 (Dioxyde de soufre)
  - NH3 (Ammoniac)
- **Navigation par fragments** : BottomNavigationView avec 2 écrans
- **Fragment Liste (PollutionListFragment)** :
  - RecyclerView avec affichage détaillé des données
  - Indicateur de couleur par niveau de pollution
  - Badge AQI coloré
  - Valeurs des principaux polluants (PM2.5, PM10, NO2, O3)
  - Indicateur de chargement (ProgressBar)
  - Message d'état vide
- **Fragment Images (ImagesFragment)** :
  - Image principale montrant le niveau actuel
  - Galerie de 6 images représentant tous les niveaux
  - Gradients de couleur pour chaque niveau
  - Mise en évidence du niveau actuel
  - Légende dynamique avec AQI
- **Prévisions de pollution** : Capacité de prévision sur 5 jours
- **Classification française** : Système de classification localisé avec 6 niveaux
- **Recommandations santé** : Conseils contextuels basés sur les niveaux de pollution
- **Interface avec codes couleur** : Indicateurs visuels de la gravité de la pollution
- **Images et gradients** : Représentations visuelles pour chaque niveau de pollution

### Fonctionnalités prévues 🔜
- Rafraîchissement pull-to-refresh
- Sélection de localisation
- Affichage des prévisions détaillées
- Système de notifications pour les alertes pollution
- Mise en cache hors ligne
- Graphiques des tendances de pollution

## Architecture

AIrAware suit le pattern architectural **MVVM (Model-View-ViewModel)** avec une séparation claire des responsabilités :

```
┌─────────────────┐
│   MainActivity  │  ← Vue (UI)
│  @AndroidEntry  │
│      Point      │
└────────┬────────┘
         │ observe LiveData
         ↓
┌─────────────────┐
│ AirQualityView  │  ← ViewModel (Logique métier)
│     Model       │
│  @HiltViewModel │
└────────┬────────┘
         │ appelle
         ↓
┌─────────────────┐
│ AirQualityRepo  │  ← Repository (Couche de données)
│     sitory      │
│   @Singleton    │
└────────┬────────┘
         │ utilise
         ↓
┌─────────────────┐
│  OpenWeather    │  ← Service API (Réseau)
│   ApiService    │
│    (Retrofit)   │
└─────────────────┘
```

### Flux de données

1. **Vue (MainActivity)** : Observe les LiveData du ViewModel
2. **ViewModel** : Expose des LiveData, appelle le Repository
3. **Repository** : Gère les appels API, convertit les données
4. **Service API** : Interface Retrofit pour les requêtes réseau

### Injection de dépendances

Le projet utilise **Hilt** pour l'injection de dépendances :
- `@HiltAndroidApp` sur la classe Application
- `@AndroidEntryPoint` sur les Activities
- `@HiltViewModel` sur les ViewModels
- `@Singleton` pour le Repository

## Technologies utilisées

### Langage et SDK
- **Langage** : Java
- **Min SDK** : 30 (Android 11)
- **Target SDK** : 36
- **Compile SDK** : 36

### Bibliothèques principales

#### Architecture
- **Hilt 2.48** : Injection de dépendances (DI) de Google
- **LiveData** : Observation réactive des données
- **ViewModel** : Gestion du cycle de vie et de l'état

#### Réseau
- **Retrofit 2.9.0** : Client HTTP type-safe
- **Gson Converter** : Sérialisation/désérialisation JSON
- **OkHttp 4.12.0** : Client HTTP avec intercepteur de logs
- **Timeouts** : 30 secondes pour connect/read/write

#### Interface utilisateur
- **ViewBinding** : Accès type-safe aux vues
- **ConstraintLayout** : Layouts flexibles
- **CardView** : Cartes Material Design
- **RecyclerView** : Affichage de listes
- **EdgeToEdge** : Interface moderne plein écran

#### Tests
- **JUnit 4** : Tests unitaires
- **Mockito 5.5.0** : Framework de mocking
- **Espresso 3.5.1** : Tests UI
- **Hilt Testing** : DI pour les tests
- **AndroidX Test** : Infrastructure de test

## Installation et configuration

### Prérequis
- Android Studio Arctic Fox ou supérieur
- JDK 8 ou supérieur
- Appareil Android ou émulateur avec API 30+

### Étapes d'installation

1. **Cloner le projet**
```bash
git clone <repository-url>
cd AIrAware1
```

2. **Ouvrir dans Android Studio**
   - File → Open → Sélectionner le dossier du projet

3. **Synchroniser Gradle**
   - Android Studio synchronisera automatiquement les dépendances

4. **Configurer la clé API OpenWeather**
   - Obtenir une clé API gratuite sur [OpenWeather](https://openweathermap.org/api)
   - Remplacer la clé dans `AirQualityRepository.java` :
   ```java
   private static final String API_KEY = "VOTRE_CLE_API_ICI";
   ```

5. **Compiler et exécuter**
   - Build → Make Project
   - Run → Run 'app'

### Configuration du projet

Le projet utilise les plugins Gradle suivants :
- Android Application Plugin
- Hilt Android Plugin
- Kapt (pour le traitement d'annotations)

## Structure du projet

```
app/src/main/java/AirAware/com/
├── AirAwareApplication.java          # Point d'entrée avec @HiltAndroidApp
├── data/                              # Modèles de réponse API
│   ├── AirPollutionResponse.java     # Données de pollution actuelles
│   └── Forecast.java                 # Modèle de données de prévision
├── di/                                # Modules d'injection de dépendances
│   ├── NetworkModule.java            # Configuration Retrofit/OkHttp
│   └── RepositoryModule.java         # Fournisseurs de Repository
├── model/                             # Modèles de données métier
│   └── AirQuality.java               # Modèle de domaine
├── network/                           # Services API
│   ├── OpenWeatherApiService.java    # Interface Retrofit
│   └── RetrofitClientInstance.java   # Configuration client
├── repository/                        # Couche de données
│   └── AirQualityRepository.java     # Gestion des données avec LiveData
├── ui/                                # Couche de présentation
│   └── MainActivity.java             # Activité principale
├── utils/                             # Classes utilitaires
│   └── AirQualityClassifier.java     # Classification française
└── viewmodel/                         # Couche de logique métier
    └── AirQualityViewModel.java      # ViewModel avec LiveData
```

## Utilisation

### Démarrage de l'application

Au lancement, l'application :
1. Se connecte à l'API OpenWeather Air Pollution
2. Récupère les données de qualité de l'air pour Paris par défaut (48.8566, 2.3522)
3. Affiche les informations dans un Toast
4. Classifie automatiquement le niveau de pollution

### Endpoints API utilisés

1. **Pollution actuelle**
   ```
   GET https://api.openweathermap.org/data/2.5/air_pollution
   Paramètres : lat, lon, appid
   ```

2. **Prévisions de pollution**
   ```
   GET https://api.openweathermap.org/data/2.5/air_pollution/forecast
   Paramètres : lat, lon, appid
   Retourne : Prévisions sur 96 heures (4 jours)
   ```

3. **Historique de pollution**
   ```
   GET https://api.openweathermap.org/data/2.5/air_pollution/history
   Paramètres : lat, lon, start, end, appid
   ```

### Exemple de code

#### Observer les données de qualité de l'air
```java
viewModel.getAirQualityData().observe(this, airQualityList -> {
    if (airQualityList != null && !airQualityList.isEmpty()) {
        AirQuality airQuality = airQualityList.get(0);
        // Utiliser les données
    }
});
```

#### Charger les données pour une localisation
```java
viewModel.loadAirQualityData(latitude, longitude);
```

## Système de classification

AIrAware utilise un système de classification français en 6 niveaux basé sur l'IQA et les concentrations de PM2.5 et PM10.

### Les 6 niveaux de pollution

| Niveau | IQA | PM2.5 | PM10 | Couleur | Code |
|--------|-----|-------|------|---------|------|
| **Excellent** | 1 | <10 | <20 | Vert foncé | #00E400 |
| **Bon** | 1-2 | <25 | <50 | Vert clair | #92D050 |
| **Modéré** | 2-3 | <50 | <100 | Jaune | #FFFF00 |
| **Mauvais** | 3-4 | <75 | <150 | Orange | #FF7E00 |
| **Très Mauvais** | 4-5 | <150 | <200 | Rouge | #FF0000 |
| **Extrêmement Mauvais** | 5 | ≥150 | ≥200 | Violet | #8F3F97 |

### Méthodes utilitaires

La classe `AirQualityClassifier` fournit des méthodes pratiques :

```java
// Classifier le niveau de pollution
NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQuality);

// Vérifier si dangereux
boolean dangereux = AirQualityClassifier.estDangereux(airQuality);

// Vérifier si sain
boolean sain = AirQualityClassifier.estSain(airQuality);

// Obtenir une recommandation santé
String conseil = AirQualityClassifier.obtenirRecommandation(airQuality);

// Calculer un score de pollution (0-100)
int score = AirQualityClassifier.calculerScorePollution(airQuality);

// Obtenir le code couleur
String couleur = AirQualityClassifier.obtenirCodeCouleur(niveau);
```

### Exemples de recommandations

- **Excellent** : "Qualité de l'air excellente. Idéal pour les activités en plein air."
- **Bon** : "Bonne qualité de l'air. Vous pouvez profiter des activités extérieures."
- **Modéré** : "Qualité de l'air acceptable. Les personnes sensibles devraient limiter les efforts prolongés."
- **Mauvais** : "Qualité de l'air mauvaise. Évitez les activités physiques intenses à l'extérieur."
- **Très Mauvais** : "Qualité de l'air très mauvaise. Restez à l'intérieur et fermez les fenêtres."
- **Extrêmement Mauvais** : "Qualité de l'air dangereuse. Évitez absolument toute exposition extérieure."

## Tests

### Tests unitaires

Localisation : `app/src/test/java/AirAware/com/`

Le projet inclut 27+ tests unitaires couvrant :
- Classification de la pollution pour différentes villes
- Tests avec villes très polluées (Delhi, Pékin, Téhéran)
- Tests avec villes à air pur (Reykjavik, Wellington, Zurich)
- Validation des seuils de pollution
- Tests des méthodes utilitaires

#### Exécuter les tests unitaires
```bash
./gradlew test
```

### Tests instrumentés (UI)

Localisation : `app/src/androidTest/java/AirAware/com/`

Le projet inclut 20+ tests instrumentés répartis en 4 suites :
1. **Tests UI** : Navigation, affichage des éléments
2. **Tests de chargement de données** : Intégration API
3. **Tests de navigation** : Transitions entre écrans
4. **Tests fonctionnels** : Flux complets utilisateur

#### Exécuter les tests instrumentés
```bash
./gradlew connectedAndroidTest
```

### Configuration Hilt pour les tests

Les tests utilisent `@HiltAndroidTest` pour l'injection de dépendances :
```java
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @get:Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    // Tests...
}
```


## Ressources

- [OpenWeather API Documentation](https://openweathermap.org/api/air-pollution)
- [Hilt Documentation](https://developer.android.com/training/dependency-injection/hilt-android)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Retrofit Documentation](https://square.github.io/retrofit/)

---

**Note** : L'application utilise l'API OpenWeather qui nécessite une clé API. Assurez-vous de respecter les limites d'utilisation de l'API gratuite (60 appels/minute, 1 000 000 appels/mois).

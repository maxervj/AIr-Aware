# Guide des Tests UI - AIrAware

## Vue d'ensemble

Ce projet contient une suite complète de tests d'UI (tests fonctionnels) utilisant Espresso et Hilt pour tester l'application AIrAware.

## Types de tests créés

### 1. Tests UI de base (`MainActivityUITest.java`)

Tests fondamentaux pour vérifier le lancement et la stabilité de l'application.

**Tests inclus:**
- ✅ `testActivityLaunches()` - Vérifie que l'activité se lance correctement
- ✅ `testAppDoesNotCrashOnStartup()` - Vérifie l'absence de crash au démarrage
- ✅ `testViewModelInitialization()` - Vérifie l'initialisation du ViewModel

**Commande:**
```bash
./gradlew connectedAndroidTest --tests "AirAware.com.MainActivityUITest"
```

### 2. Tests de chargement des données (`AirQualityDataLoadingTest.java`)

Tests pour vérifier le chargement des données de qualité de l'air.

**Tests inclus:**
- ✅ `testMainViewIsDisplayed()` - Vérifie l'affichage de la vue principale
- ✅ `testDataLoadingIsAttempted()` - Vérifie la tentative de chargement des données
- ✅ `testActivityLifecycle()` - Vérifie la gestion du cycle de vie
- ✅ `testConfigurationChange()` - Vérifie la stabilité lors de la rotation d'écran

**Commande:**
```bash
./gradlew connectedAndroidTest --tests "AirAware.com.AirQualityDataLoadingTest"
```

### 3. Tests de navigation et interaction (`NavigationAndInteractionTest.java`)

Tests pour vérifier les interactions utilisateur et la navigation.

**Tests inclus:**
- ✅ `testInternetPermissionIsGranted()` - Vérifie les permissions Internet
- ✅ `testAppThemeIsApplied()` - Vérifie l'application du thème
- ✅ `testMainViewIsInteractive()` - Vérifie l'interactivité de la vue
- ✅ `testBackButtonHandling()` - Vérifie la gestion du bouton retour
- ✅ `testMultipleUserInteractions()` - Teste plusieurs interactions utilisateur
- ✅ `testMultipleConfigurationChanges()` - Teste plusieurs rotations d'écran
- ✅ `testFullActivityLifecycle()` - Teste le cycle de vie complet
- ✅ `testApplicationStabilityUnderStress()` - Teste la stabilité sous stress

**Commande:**
```bash
./gradlew connectedAndroidTest --tests "AirAware.com.NavigationAndInteractionTest"
```

### 4. Tests fonctionnels métier (`AirQualityFunctionalTest.java`)

Tests de bout en bout pour vérifier les fonctionnalités métier de l'application.

**Tests inclus:**
- ✅ `testCompleteDataLoadingFlow()` - Test du flux complet de chargement
- ✅ `testViewModelHandlesGPSCoordinates()` - Vérifie la gestion des coordonnées GPS
- ✅ `testLoadingStateManagement()` - Vérifie la gestion des états de chargement
- ✅ `testAirQualityDataValidation()` - Valide les données de qualité de l'air
- ✅ `testDataRefresh()` - Vérifie le rafraîchissement des données
- ✅ `testMultipleRequestsStability()` - Vérifie la stabilité avec plusieurs requêtes

**Commande:**
```bash
./gradlew connectedAndroidTest --tests "AirAware.com.AirQualityFunctionalTest"
```

## Exécution des tests

### Tous les tests

Pour exécuter tous les tests d'UI:

```bash
./gradlew connectedAndroidTest
```

### Tests spécifiques

Pour exécuter une classe de test spécifique:

```bash
./gradlew connectedAndroidTest --tests "AirAware.com.MainActivityUITest"
```

Pour exécuter un test spécifique:

```bash
./gradlew connectedAndroidTest --tests "AirAware.com.MainActivityUITest.testActivityLaunches"
```

### Depuis Android Studio

1. Ouvrez le fichier de test
2. Clic droit sur la classe ou méthode de test
3. Sélectionnez "Run [NomDuTest]"

## Prérequis

### Configuration requise

- Appareil Android ou émulateur configuré
- API Level 30 ou supérieur
- Connexion Internet active (pour les tests de chargement de données)
- Clé API OpenWeather configurée dans `AirQualityRepository.java`

### Dépendances de test

Les dépendances suivantes ont été ajoutées dans `build.gradle`:

```gradle
// Testing
testImplementation 'junit:junit:4.13.2'
testImplementation 'androidx.arch.core:core-testing:2.2.0'
testImplementation 'org.mockito:mockito-core:5.5.0'

// Android Testing
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
androidTestImplementation 'androidx.test.espresso:espresso-intents:3.5.1'
androidTestImplementation 'androidx.test:runner:1.5.2'
androidTestImplementation 'androidx.test:rules:1.5.0'

// Hilt Testing
androidTestImplementation 'com.google.dagger:hilt-android-testing:2.48'
androidTestAnnotationProcessor 'com.google.dagger:hilt-android-compiler:2.48'

// IdlingResource
implementation 'androidx.test.espresso:espresso-idling-resource:3.5.1'
```

## Structure des tests

```
app/src/androidTest/java/AirAware/com/
├── MainActivityUITest.java              # Tests UI de base
├── AirQualityDataLoadingTest.java       # Tests de chargement des données
├── NavigationAndInteractionTest.java    # Tests de navigation
└── AirQualityFunctionalTest.java        # Tests fonctionnels métier
```

## Framework utilisés

### Espresso
Framework de test d'UI officiel d'Android pour:
- Interactions avec les vues
- Vérifications d'affichage
- Actions utilisateur (clics, swipes, etc.)

### Hilt Testing
Framework d'injection de dépendances pour:
- Injection de dépendances dans les tests
- Remplacement de modules pour les tests
- Isolation des composants testés

### AndroidX Test
Bibliothèque de test pour:
- ActivityScenario pour contrôler le cycle de vie
- Règles de test
- Runners de test

## Bonnes pratiques appliquées

1. **Isolation des tests** - Chaque test est indépendant
2. **Nommage clair** - Les noms de méthodes décrivent ce qui est testé
3. **Annotations Hilt** - Utilisation de `@HiltAndroidTest` pour l'injection
4. **Timeouts appropriés** - Gestion des opérations asynchrones
5. **Assertions claires** - Messages d'erreur explicites
6. **Tests atomiques** - Chaque test vérifie une seule fonctionnalité

## Résolution des problèmes courants

### L'émulateur ne démarre pas
```bash
# Vérifier les émulateurs disponibles
emulator -list-avds

# Démarrer manuellement un émulateur
emulator -avd <nom_emulateur>
```

### Tests échouent avec "No connected devices"
```bash
# Vérifier les appareils connectés
adb devices

# Si vide, redémarrer adb
adb kill-server
adb start-server
```

### Erreur de timeout
- Augmenter le timeout dans les tests (actuellement 10 secondes)
- Vérifier la connexion Internet de l'émulateur
- Vérifier que la clé API OpenWeather est valide

### Tests échouent en raison de l'API
- Vérifier que la clé API est correcte dans `AirQualityRepository.java`
- Vérifier que l'appareil de test a accès à Internet
- Vérifier les quotas de l'API OpenWeather

## Rapports de tests

Les rapports de tests sont générés dans:
```
app/build/reports/androidTests/connected/
```

Pour ouvrir le rapport HTML:
```bash
open app/build/reports/androidTests/connected/index.html
```

## Coverage des tests

Pour générer un rapport de couverture de code:

```bash
./gradlew createDebugCoverageReport
```

Rapport disponible dans:
```
app/build/reports/coverage/androidTest/debug/
```

## Prochaines étapes possibles

1. **Tests de performance** - Mesurer les temps de chargement
2. **Tests d'accessibilité** - Vérifier l'accessibilité de l'UI
3. **Tests de bout en bout** - Scénarios utilisateur complets
4. **Tests visuels** - Screenshot testing
5. **Tests d'intégration** - Avec une API mockée complète

## Ressources

- [Documentation Espresso](https://developer.android.com/training/testing/espresso)
- [Hilt Testing](https://developer.android.com/training/dependency-injection/hilt-testing)
- [Android Testing Guide](https://developer.android.com/training/testing)
- [Best Practices](https://developer.android.com/training/testing/fundamentals)

## Statistiques des tests

- **Total de classes de test**: 4
- **Total de méthodes de test**: 20+
- **Couverture**: Tests UI pour MainActivity, ViewModel, Repository
- **Types de tests**: Unitaires, Intégration, Fonctionnels, UI

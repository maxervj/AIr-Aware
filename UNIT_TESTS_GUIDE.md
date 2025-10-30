# Guide des Tests Unitaires - AIrAware

## Vue d'ensemble

Ce document décrit les tests unitaires créés pour tester la logique de classification de la qualité de l'air avec différents niveaux de pollution.

## Tests créés

### 1. Tests pour villes très polluées (`HighlyPollutedCityTest.java`)

Tests pour des destinations avec une pollution extrême (gros pollueurs).

#### Villes testées :

**Delhi, Inde** (Pollution extrême)
- AQI : 5 (Very Poor)
- PM2.5 : 187.5 μg/m³ (Dangereux : >150)
- PM10 : 310.8 μg/m³
- NO2 : 128.3 μg/m³
- Classification : **Extrêmement mauvais**

**Beijing, Chine** (Pollution sévère)
- AQI : 5 (Very Poor)
- PM2.5 : 165.2 μg/m³
- PM10 : 278.5 μg/m³
- NO2 : 115.7 μg/m³
- Classification : **Très mauvais / Extrêmement mauvais**

**Tehran, Iran** (Pollution élevée)
- AQI : 4 (Poor)
- PM2.5 : 95.3 μg/m³
- PM10 : 156.8 μg/m³
- NO2 : 98.2 μg/m³
- Classification : **Mauvais**

#### Tests inclus (12 tests) :

1. ✅ `testDelhiIsExtremelyPolluted()` - Vérifie la classification "Extrêmement mauvais"
2. ✅ `testDelhiIsDangerous()` - Vérifie que Delhi est dangereuse
3. ✅ `testDelhiPM25Levels()` - Vérifie PM2.5 > 150 (dangereux)
4. ✅ `testDelhiPollutionScore()` - Vérifie score > 80/100
5. ✅ `testDelhiRecommendation()` - Vérifie recommandation "ALERTE"
6. ✅ `testDelhiColorCode()` - Vérifie couleur rouge/violet
7. ✅ `testBeijingIsVeryPolluted()` - Vérifie pollution de Beijing
8. ✅ `testTehranIsPolluted()` - Vérifie pollution de Tehran
9. ✅ `testComparePollutionLevels()` - Compare les scores
10. ✅ `testNO2LevelsInPollutedCities()` - Vérifie NO2 > 100
11. ✅ `testAQIIsAtMaximum()` - Vérifie AQI = 4 ou 5
12. ✅ `testNoCityIsHealthy()` - Vérifie qu'aucune ville n'est saine

**Commande d'exécution :**
```bash
./gradlew test --tests "AirAware.com.HighlyPollutedCityTest"
```

### 2. Tests pour villes à air pur (`CleanAirCityTest.java`)

Tests pour des destinations avec peu de pollution (air pur).

#### Villes testées :

**Reykjavik, Islande** (Air excellent)
- AQI : 1 (Good)
- PM2.5 : 3.2 μg/m³ (Excellent : <10)
- PM10 : 8.5 μg/m³ (Excellent : <20)
- NO2 : 8.7 μg/m³
- Classification : **Excellent**

**Wellington, Nouvelle-Zélande** (Air excellent)
- AQI : 1 (Good)
- PM2.5 : 4.8 μg/m³
- PM10 : 11.2 μg/m³
- NO2 : 12.3 μg/m³
- Classification : **Excellent**

**Zurich, Suisse** (Air bon)
- AQI : 2 (Fair)
- PM2.5 : 12.5 μg/m³
- PM10 : 22.8 μg/m³
- NO2 : 24.6 μg/m³
- Classification : **Bon**

#### Tests inclus (15 tests) :

1. ✅ `testReykjavikHasExcellentAir()` - Vérifie classification "Excellent"
2. ✅ `testReykjavikIsHealthy()` - Vérifie que Reykjavik est saine
3. ✅ `testReykjavikLowPM25()` - Vérifie PM2.5 < 10
4. ✅ `testReykjavikLowPollutionScore()` - Vérifie score < 20/100
5. ✅ `testReykjavikPositiveRecommendation()` - Vérifie recommandation positive
6. ✅ `testReykjavikGreenColorCode()` - Vérifie couleur verte
7. ✅ `testWellingtonHasExcellentAir()` - Vérifie air excellent de Wellington
8. ✅ `testZurichHasGoodAir()` - Vérifie bon air de Zurich
9. ✅ `testCompareLowPollutionScores()` - Compare les faibles scores
10. ✅ `testLowNO2Levels()` - Vérifie NO2 < 30
11. ✅ `testAQIIsAtMinimum()` - Vérifie AQI = 1 ou 2
12. ✅ `testAllCitiesAreHealthy()` - Vérifie que toutes sont saines
13. ✅ `testLowPM10Levels()` - Vérifie PM10 < 30
14. ✅ `testRecommendationsEncourageOutdoorActivities()` - Vérifie recommandations positives
15. ✅ `testComparisonWithPollutedCity()` - Compare avec ville polluée

**Commande d'exécution :**
```bash
./gradlew test --tests "AirAware.com.CleanAirCityTest"
```

## Classe utilitaire : AirQualityClassifier

### Niveaux de pollution

```java
public enum PollutionLevel {
    EXCELLENT,        // Air très pur
    GOOD,            // Qualité satisfaisante
    MODERATE,        // Qualité acceptable
    POOR,            // Effets sur certains groupes
    VERY_POOR,       // Alerte santé
    EXTREMELY_POOR   // Urgence sanitaire
}
```

### Méthodes disponibles

#### 1. `classifyPollution(AirQuality airQuality)`
Classifie le niveau de pollution selon l'AQI et les composants.

**Critères de classification :**
- **Excellent** : AQI=1, PM2.5<10, PM10<20
- **Bon** : AQI=1-2, PM2.5<25
- **Modéré** : AQI=2-3, PM2.5<50
- **Mauvais** : AQI=3-4, PM2.5<75
- **Très mauvais** : AQI=4-5, PM2.5<150
- **Extrêmement mauvais** : AQI=5, PM2.5>150

#### 2. `isDangerous(AirQuality airQuality)`
Retourne `true` si la qualité de l'air est dangereuse (Very Poor ou Extremely Poor).

#### 3. `isHealthy(AirQuality airQuality)`
Retourne `true` si la qualité de l'air est saine (Excellent ou Good).

#### 4. `getRecommendation(AirQuality airQuality)`
Retourne une recommandation personnalisée selon le niveau de pollution.

**Exemples de recommandations :**
- **Excellent** : "Conditions idéales pour toutes activités extérieures."
- **Très mauvais** : "Évitez les activités extérieures. Restez à l'intérieur si possible."
- **Extrêmement mauvais** : "ALERTE ! Restez à l'intérieur. Portez un masque si vous devez sortir."

#### 5. `calculatePollutionScore(AirQuality airQuality)`
Calcule un score de pollution de 0 à 100 basé sur :
- AQI (0-100 points)
- PM2.5 (0-40 points)
- PM10 (0-30 points)
- NO2 (0-20 points)
- O3 (0-10 points)

#### 6. `getColorCode(AirQuality airQuality)`
Retourne un code couleur hexadécimal pour l'UI :
- 🟢 Vert foncé (`#00E400`) : Excellent
- 🟢 Vert clair (`#92D050`) : Bon
- 🟡 Jaune (`#FFFF00`) : Modéré
- 🟠 Orange (`#FF7E00`) : Mauvais
- 🔴 Rouge (`#FF0000`) : Très mauvais
- 🟣 Violet (`#8F3F97`) : Extrêmement mauvais

## Comparaison des résultats

### Scores de pollution moyens :

| Ville | Score /100 | Classification | PM2.5 | AQI |
|-------|-----------|----------------|-------|-----|
| **Reykjavik** | ~10 | Excellent | 3.2 | 1 |
| **Wellington** | ~12 | Excellent | 4.8 | 1 |
| **Zurich** | ~30 | Bon | 12.5 | 2 |
| **Tehran** | ~70 | Mauvais | 95.3 | 4 |
| **Beijing** | ~95 | Très mauvais | 165.2 | 5 |
| **Delhi** | ~98 | Extrêmement mauvais | 187.5 | 5 |

### Différence entre villes propres et polluées :

- **PM2.5** : Reykjavik (3.2) vs Delhi (187.5) = **58x plus élevé**
- **PM10** : Reykjavik (8.5) vs Delhi (310.8) = **36x plus élevé**
- **NO2** : Reykjavik (8.7) vs Delhi (128.3) = **15x plus élevé**
- **Score** : Reykjavik (~10) vs Delhi (~98) = **10x plus élevé**

## Exécution des tests

### Tous les tests unitaires
```bash
./gradlew test
```

### Test spécifique - Villes polluées
```bash
./gradlew test --tests "AirAware.com.HighlyPollutedCityTest"
```

### Test spécifique - Villes propres
```bash
./gradlew test --tests "AirAware.com.CleanAirCityTest"

```

### Un test particulier
```bash
./gradlew test --tests "AirAware.com.HighlyPollutedCityTest.testDelhiIsExtremelyPolluted"
```

### Depuis Android Studio
1. Ouvrir le fichier de test
2. Clic droit sur la classe ou méthode
3. Sélectionner "Run [NomDuTest]"

## Assertions utilisées

Les tests utilisent les assertions JUnit suivantes :

- `assertEquals()` - Vérifie l'égalité de valeurs
- `assertTrue()` - Vérifie qu'une condition est vraie
- `assertFalse()` - Vérifie qu'une condition est fausse
- `assertNotNull()` - Vérifie qu'un objet n'est pas null

## Couverture des tests

### Fonctionnalités testées :

- ✅ Classification du niveau de pollution
- ✅ Validation des seuils PM2.5 et PM10
- ✅ Calcul du score de pollution global
- ✅ Génération de recommandations
- ✅ Attribution de codes couleur
- ✅ Détection des niveaux dangereux
- ✅ Détection des niveaux sains
- ✅ Comparaison entre différentes villes
- ✅ Validation des données AQI

### Statistiques :

- **Total de tests** : 27 tests unitaires
- **Villes testées** : 6 villes (3 polluées + 3 propres)
- **Assertions** : 100+ assertions
- **Couverture** : Toutes les méthodes de AirQualityClassifier

## Interprétation des résultats

### ✅ Test réussi
Le test valide le comportement attendu.

### ❌ Test échoué
Vérifier :
1. Les seuils de classification dans `AirQualityClassifier`
2. Les valeurs de test dans les données mockées
3. Les conditions dans les assertions

## Normes de qualité de l'air utilisées

### PM2.5 (Particules fines) :
- **0-10 μg/m³** : Excellent
- **10-25 μg/m³** : Bon
- **25-50 μg/m³** : Modéré
- **50-75 μg/m³** : Mauvais
- **75-150 μg/m³** : Très mauvais
- **>150 μg/m³** : Extrêmement mauvais

### PM10 (Particules grossières) :
- **0-20 μg/m³** : Excellent
- **20-50 μg/m³** : Bon
- **50-100 μg/m³** : Modéré
- **>100 μg/m³** : Mauvais

### NO2 (Dioxyde d'azote) :
- **0-40 μg/m³** : Faible
- **40-80 μg/m³** : Modéré
- **>80 μg/m³** : Élevé

## Utilisation dans l'application

Ces tests garantissent que :
1. La classification de la pollution est correcte
2. Les recommandations sont appropriées
3. Les alertes sont déclenchées au bon moment
4. L'interface utilisateur affiche les bonnes couleurs
5. Les utilisateurs reçoivent des informations fiables

## Prochaines étapes

1. Ajouter des tests pour d'autres polluants (SO2, CO, O3)
2. Tester les cas limites (valeurs nulles, négatives)
3. Ajouter des tests de performance
4. Créer des tests d'intégration avec le Repository
5. Implémenter des tests paramétrés pour tester plusieurs villes

## Ressources

- [OMS - Qualité de l'air](https://www.who.int/health-topics/air-pollution)
- [EPA - Air Quality Index](https://www.airnow.gov/aqi/aqi-basics/)
- [OpenWeather Air Pollution API](https://openweathermap.org/api/air-pollution)
- [JUnit 4 Documentation](https://junit.org/junit4/)

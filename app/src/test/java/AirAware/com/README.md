# Tests Unitaires - AIrAware

## 📋 Vue d'ensemble

**2 suites de tests** couvrant les aspects critiques de l'application :
1. **AQITest** - Tests des niveaux AQI et de la clé API
2. **VilleTest** - Tests des villes et coordonnées

---

## 🧪 Suite 1 : AQITest (33 tests)

### Description
Teste les niveaux AQI (Air Quality Index), la classification de pollution, et **vérifie la clé API OpenWeather**.

### Tests de la Clé API (2 tests) 🔑
- ✅ `testAPIKey_EstValide` - Vérifie que la clé API est valide (32 caractères alphanumérique)
- ✅ `testAPIKey_CorrespondAuRepository` - Vérifie la correspondance avec le Repository

**Clé API testée** : `619d368931beecb904e9f5410dc515d6`

### Tests de Conversion AQI → Status (5 tests)
- ✅ AQI 1 → "Good"
- ✅ AQI 2 → "Fair"
- ✅ AQI 3 → "Moderate"
- ✅ AQI 4 → "Poor"
- ✅ AQI 5 → "Very Poor"

### Tests de Classification (4 tests)
Vérifie que chaque AQI correspond au bon niveau de pollution :
- ✅ AQI 1 → EXCELLENT ou BON
- ✅ AQI 3 → MODERE
- ✅ AQI 4 → MAUVAIS
- ✅ AQI 5 → TRES_MAUVAIS ou EXTREMEMENT_MAUVAIS

### Tests des Couleurs (4 tests)
- ✅ AQI 1 → Vert (#00E400 ou #92D050)
- ✅ AQI 3 → Jaune (#FFFF00)
- ✅ AQI 4 → Orange (#FF7E00)
- ✅ AQI 5 → Rouge (#FF0000) ou Violet (#8F3F97)

### Tests des Emojis (4 tests)
- ✅ AQI 1 → 😊 ou 🙂
- ✅ AQI 3 → 😐
- ✅ AQI 4 → 😟
- ✅ AQI 5 → 😷 ou ☠️

### Tests de Sécurité (5 tests)
- ✅ AQI 1 et 2 sont sains
- ✅ AQI 3 n'est pas sain
- ✅ AQI 5 est dangereux
- ✅ AQI 1 n'est pas dangereux

### Tests PM2.5 et PM10 (4 tests)
- ✅ PM2.5 pour AQI 1 est bas (<25)
- ✅ PM2.5 pour AQI 5 est élevé (>100)
- ✅ PM10 pour AQI 1 est bas (<50)
- ✅ PM10 pour AQI 5 est élevé (>150)

### Tests de Recommandations (2 tests)
- ✅ AQI 1 recommande activités extérieures
- ✅ AQI 5 recommande d'éviter l'extérieur

### Tests de Cohérence (3 tests)
- ✅ Plus l'AQI augmente, plus c'est dangereux
- ✅ Tous les AQI ont un status
- ✅ Tous les AQI ont une classification

---

## 🌍 Suite 2 : VilleTest (23 tests)

### Description
Teste le modèle City, les 12 villes disponibles, et leurs coordonnées géographiques.

### Tests de la Liste (3 tests)
- ✅ Liste non nulle
- ✅ Liste non vide
- ✅ Contient exactement **12 villes**

### Tests des Villes Spécifiques (5 tests)

#### **Paris, France**
- Latitude : 48.8566°N
- Longitude : 2.3522°E

#### **New Delhi, Inde**
- Latitude : 28.6139°N
- Longitude : 77.2090°E

#### **Beijing, Chine**
- Latitude : 39.9042°N
- Longitude : 116.4074°E

#### **Reykjavik, Islande** (Air pur)
- Latitude : 64.1466°N
- Longitude : -21.9426°W

#### **Sydney, Australie** (Hémisphère sud)
- Latitude : -33.8688°S
- Longitude : 151.2093°E

### Tests de Validité des Coordonnées (3 tests)
- ✅ Latitude entre -90° et 90°
- ✅ Longitude entre -180° et 180°
- ✅ Pas de coordonnées (0, 0)

### Tests de Cohérence (3 tests)
- ✅ Noms de villes uniques
- ✅ Toutes les villes ont un nom
- ✅ toString() retourne le nom

### Tests de Diversité Géographique (5 tests)
- ✅ Villes dans l'hémisphère nord
- ✅ Villes dans l'hémisphère sud
- ✅ Villes à l'ouest de Greenwich
- ✅ Villes à l'est de Greenwich
- ✅ Couverture mondiale (>60° latitude, >150° longitude)

### Tests par Niveau de Pollution (3 tests)
- ✅ Villes pour air pur (Reykjavik)
- ✅ Villes pour air pollué (Dhaka, New Delhi, Beijing)
- ✅ Villes pour air modéré (Paris, Londres, Tokyo)

### Tests de Création (1 test)
- ✅ Création d'un objet City personnalisé

---

## 📊 Résumé

| Suite de Tests | Nombre de Tests | Couverture |
|----------------|-----------------|------------|
| **AQITest** | 33 | AQI, classification, couleurs, emojis, **clé API** |
| **VilleTest** | 23 | Villes, coordonnées, diversité géographique |
| **TOTAL** | **56 tests** | 🎉 |

---

## 🚀 Exécution des Tests

### Avec Android Studio
1. Clic droit sur `app/src/test/java/AirAware/com/`
2. Sélectionner **"Run Tests in 'com'"**

### Avec Gradle
```bash
# Tous les tests
./gradlew test

# Tests spécifiques
./gradlew test --tests AQITest
./gradlew test --tests VilleTest
```

---

## ✅ Résultats

```
╔═══════════════════════════════════════════════════╗
║     RÉSULTATS DES TESTS UNITAIRES - AIrAware     ║
╠═══════════════════════════════════════════════════╣
║                                                   ║
║  📊 AQITest        : 33 tests ✅                  ║
║  🌍 VilleTest      : 23 tests ✅                  ║
║                                                   ║
║  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━   ║
║  🎉 TOTAL          : 56 tests réussis            ║
║  ✅ Échecs         : 0                            ║
║  ✅ Erreurs        : 0                            ║
║  ⚡ Temps          : < 0.04s                      ║
║                                                   ║
╠═══════════════════════════════════════════════════╣
║  🔑 Clé API testée et validée                     ║
║  🌐 12 villes testées (Paris, New Delhi, etc.)   ║
║  📈 5 niveaux AQI testés (1 à 5)                  ║
║  🎨 Couleurs, emojis et recommandations OK        ║
╚═══════════════════════════════════════════════════╝
```

---

## 🔑 Clé API OpenWeather

**Clé API validée** : `619d368931beecb904e9f5410dc515d6`

La clé API est testée dans `AQITest` pour s'assurer qu'elle :
- Est non nulle
- A 32 caractères
- Est alphanumérique (hexadécimal)
- Correspond à celle utilisée dans `AirQualityRepository`

---

## 🌐 Liste des 12 Villes

1. **Beijing, Chine** - Pollution élevée
2. **Dhaka, Bangladesh** - Pollution extrême
3. **Londres, Royaume-Uni** - Pollution modérée
4. **Los Angeles, USA** - Pollution modérée
5. **Mexico City, Mexique** - Pollution mauvaise
6. **Mumbai, Inde** - Pollution très mauvaise
7. **New Delhi, Inde** - Pollution très mauvaise/extrême
8. **Paris, France** - Pollution modérée
9. **Reykjavik, Islande** - Air excellent
10. **Sydney, Australie** - Bonne qualité d'air
11. **Tokyo, Japon** - Pollution modérée
12. **Zurich, Suisse** - Bonne qualité d'air

---

## 📝 Fichiers de Tests

- **AQITest.java** - 33 tests sur les niveaux AQI et la clé API
- **VilleTest.java** - 23 tests sur les villes et coordonnées

---

**Créé le** : 2025-11-05
**Version** : 2.0 (Simplifiée)
**Statut** : ✅ Tous les tests passent

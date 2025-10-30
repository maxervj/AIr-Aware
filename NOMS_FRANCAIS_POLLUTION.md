# Noms Français pour les Degrés de Pollution

## Vue d'ensemble

Tous les noms de l'API pour qualifier les degrés de pollution ont été traduits en français pour une meilleure compréhension et cohérence avec l'application AIrAware.

## Changements effectués

### 1. Enum `NiveauPollution` (anciennement `PollutionLevel`)

Les valeurs de l'énumération ont été traduites en français :

| Ancien nom (anglais) | Nouveau nom (français) | Label | Description |
|---------------------|------------------------|-------|-------------|
| `EXCELLENT` | `EXCELLENT` | Excellent | Air très pur |
| `GOOD` | `BON` | Bon | Qualité de l'air satisfaisante |
| `MODERATE` | `MODERE` | Modéré | Qualité acceptable pour la plupart |
| `POOR` | `MAUVAIS` | Mauvais | Effets sur la santé pour certains groupes |
| `VERY_POOR` | `TRES_MAUVAIS` | Très mauvais | Alerte santé, tout le monde peut être affecté |
| `EXTREMELY_POOR` | `EXTREMEMENT_MAUVAIS` | Extrêmement mauvais | Urgence sanitaire, danger pour tous |

### 2. Méthodes traduites en français

#### Méthodes principales (en français)

| Ancienne méthode | Nouvelle méthode | Description |
|------------------|------------------|-------------|
| `classifyPollution()` | `classifierPollution()` | Classifie le niveau de pollution |
| `isDangerous()` | `estDangereux()` | Détermine si l'air est dangereux |
| `isHealthy()` | `estSain()` | Détermine si l'air est sain |
| `getRecommendation()` | `obtenirRecommandation()` | Retourne une recommandation |
| `calculatePollutionScore()` | `calculerScorePollution()` | Calcule le score de pollution (0-100) |
| `getColorCode()` | `obtenirCodeCouleur()` | Retourne le code couleur hexadécimal |

#### Méthodes de compatibilité (dépréciées)

Les anciennes méthodes en anglais sont **conservées** pour assurer la compatibilité avec le code existant, mais sont marquées comme `@Deprecated`. Elles redirigent vers les nouvelles méthodes françaises.

## Utilisation

### Exemple avec les nouveaux noms français

```java
// Classifier le niveau de pollution
NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQuality);

// Vérifier si c'est dangereux
boolean dangereux = AirQualityClassifier.estDangereux(airQuality);

// Vérifier si c'est sain
boolean sain = AirQualityClassifier.estSain(airQuality);

// Obtenir une recommandation
String recommandation = AirQualityClassifier.obtenirRecommandation(airQuality);

// Calculer le score
int score = AirQualityClassifier.calculerScorePollution(airQuality);

// Obtenir la couleur
String couleur = AirQualityClassifier.obtenirCodeCouleur(airQuality);

// Utiliser le niveau
switch (niveau) {
    case EXCELLENT:
        System.out.println("Air excellent !");
        break;
    case BON:
        System.out.println("Bonne qualité de l'air");
        break;
    case MODERE:
        System.out.println("Qualité modérée");
        break;
    case MAUVAIS:
        System.out.println("Mauvaise qualité");
        break;
    case TRES_MAUVAIS:
        System.out.println("Très mauvaise qualité");
        break;
    case EXTREMEMENT_MAUVAIS:
        System.out.println("Qualité extrêmement mauvaise");
        break;
}
```

### Compatibilité avec l'ancien code

```java
// Ces méthodes fonctionnent toujours mais sont dépréciées
NiveauPollution niveau = AirQualityClassifier.classifyPollution(airQuality); // ⚠️ Deprecated
boolean dangerous = AirQualityClassifier.isDangerous(airQuality);            // ⚠️ Deprecated
String recommendation = AirQualityClassifier.getRecommendation(airQuality);  // ⚠️ Deprecated
```

## Correspondance des degrés de pollution

### Critères de classification

| Degré | AQI | PM2.5 (μg/m³) | PM10 (μg/m³) | Code Couleur |
|-------|-----|---------------|--------------|--------------|
| **EXCELLENT** | 1 | < 10 | < 20 | 🟢 #00E400 |
| **BON** | 1-2 | < 25 | < 50 | 🟢 #92D050 |
| **MODERE** | 2-3 | < 50 | < 100 | 🟡 #FFFF00 |
| **MAUVAIS** | 3-4 | < 75 | < 150 | 🟠 #FF7E00 |
| **TRES_MAUVAIS** | 4-5 | < 150 | < 250 | 🔴 #FF0000 |
| **EXTREMEMENT_MAUVAIS** | 5 | > 150 | > 250 | 🟣 #8F3F97 |

### Recommandations associées

| Degré | Recommandation |
|-------|----------------|
| **EXCELLENT** | "Conditions idéales pour toutes activités extérieures." |
| **BON** | "Profitez de vos activités extérieures en toute sécurité." |
| **MODERE** | "Qualité acceptable. Personnes sensibles : limitez les efforts prolongés." |
| **MAUVAIS** | "Réduisez les activités physiques intenses à l'extérieur." |
| **TRES_MAUVAIS** | "Évitez les activités extérieures. Restez à l'intérieur si possible." |
| **EXTREMEMENT_MAUVAIS** | "ALERTE ! Restez à l'intérieur. Portez un masque si vous devez sortir." |

## Exemples concrets

### Paris - Bon jour

```java
AirQuality paris = createParisData(); // PM2.5: 12.5, AQI: 2

NiveauPollution niveau = AirQualityClassifier.classifierPollution(paris);
// Résultat: BON

String recommandation = AirQualityClassifier.obtenirRecommandation(paris);
// Résultat: "Profitez de vos activités extérieures en toute sécurité."

int score = AirQualityClassifier.calculerScorePollution(paris);
// Résultat: ~30/100

String couleur = AirQualityClassifier.obtenirCodeCouleur(paris);
// Résultat: "#92D050" (vert clair)
```

### Delhi - Jour de forte pollution

```java
AirQuality delhi = createDelhiData(); // PM2.5: 187.5, AQI: 5

NiveauPollution niveau = AirQualityClassifier.classifierPollution(delhi);
// Résultat: EXTREMEMENT_MAUVAIS

boolean dangereux = AirQualityClassifier.estDangereux(delhi);
// Résultat: true

String recommandation = AirQualityClassifier.obtenirRecommandation(delhi);
// Résultat: "ALERTE ! Restez à l'intérieur. Portez un masque si vous devez sortir."

int score = AirQualityClassifier.calculerScorePollution(delhi);
// Résultat: ~98/100

String couleur = AirQualityClassifier.obtenirCodeCouleur(delhi);
// Résultat: "#8F3F97" (violet)
```

## Migration du code existant

### Étape 1 : Mettre à jour les imports (si nécessaire)

Pas de changement nécessaire, le package reste le même.

### Étape 2 : Remplacer les noms anglais par les noms français

**Avant :**
```java
PollutionLevel level = AirQualityClassifier.classifyPollution(airQuality);
if (level == PollutionLevel.VERY_POOR) {
    showAlert();
}
```

**Après :**
```java
NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQuality);
if (niveau == NiveauPollution.TRES_MAUVAIS) {
    afficherAlerte();
}
```

### Étape 3 : Mettre à jour les switch statements

**Avant :**
```java
switch (level) {
    case EXCELLENT:
    case GOOD:
        return "Safe";
    case MODERATE:
        return "Moderate";
    case POOR:
    case VERY_POOR:
    case EXTREMELY_POOR:
        return "Danger";
}
```

**Après :**
```java
switch (niveau) {
    case EXCELLENT:
    case BON:
        return "Sûr";
    case MODERE:
        return "Modéré";
    case MAUVAIS:
    case TRES_MAUVAIS:
    case EXTREMEMENT_MAUVAIS:
        return "Danger";
}
```

## Tests unitaires

Les tests unitaires ont également été mis à jour pour utiliser les nouveaux noms français. Les anciens tests fonctionnent toujours grâce aux méthodes de compatibilité.

### Exemple de test

```java
@Test
public void testClassificationPollutionExcellente() {
    AirQuality airQuality = creerAirQualityExcellent();
    NiveauPollution niveau = AirQualityClassifier.classifierPollution(airQuality);

    assertEquals(NiveauPollution.EXCELLENT, niveau);
    assertTrue(AirQualityClassifier.estSain(airQuality));
    assertFalse(AirQualityClassifier.estDangereux(airQuality));
}
```

## Avantages de la francisation

1. **Cohérence** : L'application est en français, le code suit la même logique
2. **Compréhension** : Les développeurs francophones comprennent immédiatement
3. **Maintenance** : Plus facile à maintenir avec des noms explicites
4. **Documentation** : La documentation est alignée avec le code
5. **Utilisateurs** : Les messages correspondent au vocabulaire utilisé

## Compatibilité

✅ **Compatibilité ascendante maintenue** : Tout le code existant continue de fonctionner grâce aux méthodes dépréciées.

⚠️ **Avertissements de dépréciation** : Lors de la compilation, vous verrez des warnings pour encourager la migration vers les nouvelles méthodes.

📝 **Plan de migration** : Les méthodes dépréciées seront supprimées dans une version future majeure (v2.0).

## Ressources

- [OMS - Lignes directrices qualité de l'air](https://www.who.int/fr/news-room/feature-stories/detail/what-are-the-who-air-quality-guidelines)
- [Ministère de la Santé - Qualité de l'air](https://solidarites-sante.gouv.fr/sante-et-environnement/air-exterieur/)
- [ATMO France - Indice de qualité de l'air](https://www.atmo-france.org/article/lindice-atmo-nouveau-indice-de-qualite-de-lair)

## Glossaire

| Terme français | Terme anglais | Définition |
|----------------|---------------|------------|
| Niveau | Level | Degré ou palier de classification |
| Pollution | Pollution | Contamination de l'air |
| Classifier | Classify | Classer dans une catégorie |
| Dangereux | Dangerous | Qui présente un danger |
| Sain | Healthy | Qui est bon pour la santé |
| Recommandation | Recommendation | Conseil ou suggestion |
| Score | Score | Note ou évaluation chiffrée |
| Couleur | Color | Teinte associée au niveau |

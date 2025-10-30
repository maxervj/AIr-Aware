# Guide des Fragments et RecyclerView avec Images

## Vue d'ensemble

Ce guide explique comment fonctionne la navigation entre les fragments de l'application AIrAware, l'affichage des données de pollution dans un RecyclerView, et l'utilisation des images pour représenter visuellement les différents niveaux de qualité de l'air.

## Architecture des Fragments

### Structure de Navigation

L'application utilise maintenant une navigation par fragments avec un `BottomNavigationView` qui permet de basculer entre deux écrans principaux :

```
MainActivity
├── BottomNavigationView
│   ├── Liste (PollutionListFragment)
│   └── Niveaux (ImagesFragment)
└── FragmentContainerView
    └── Fragment actif
```

## 1. PollutionListFragment

### Description
Fragment qui affiche les données de pollution dans une liste avec un RecyclerView.

### Fonctionnalités
- **RecyclerView** : Affiche une liste scrollable des données de pollution
- **ProgressBar** : Indicateur de chargement pendant la récupération des données
- **Message vide** : Affiche un message quand aucune donnée n'est disponible
- **Observation LiveData** : Réagit automatiquement aux changements de données

### Composants UI

#### Layout (`fragment_pollution_list.xml`)
```xml
- TextView (Titre) : "Données de Pollution"
- ProgressBar : Indicateur de chargement
- RecyclerView : Liste des données
- TextView : Message "Aucune donnée disponible"
```

#### Item du RecyclerView (`item_pollution.xml`)
Chaque élément de la liste affiche :
- **Indicateur de couleur** : Barre verticale colorée selon le niveau de pollution
- **Localisation** : Nom de la ville ou coordonnées GPS
- **Badge AQI** : Indice de qualité de l'air (1-5)
- **Status** : Texte du niveau (Excellent, Bon, Modéré, etc.)
- **Polluants** :
  - PM2.5 (Particules fines)
  - PM10 (Particules grossières)
  - NO2 (Dioxyde d'azote)
  - O3 (Ozone)

### Code Important

#### Initialisation du RecyclerView
```java
private void setupRecyclerView() {
    adapter = new PollutionAdapter();
    binding.recyclerViewPollution.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.recyclerViewPollution.setAdapter(adapter);
}
```

#### Observation des données
```java
viewModel.getAirQualityData().observe(getViewLifecycleOwner(), airQualityList -> {
    if (airQualityList != null && !airQualityList.isEmpty()) {
        adapter.submitList(airQualityList);
        // Afficher le RecyclerView, masquer le message vide
    } else {
        // Masquer le RecyclerView, afficher le message vide
    }
});
```

## 2. PollutionAdapter

### Description
Adapter personnalisé pour le RecyclerView qui gère l'affichage des données de pollution.

### Fonctionnalités Clés

#### DiffUtil
Utilise `DiffUtil.ItemCallback` pour des mises à jour efficaces de la liste :
```java
- areItemsTheSame() : Compare par coordonnées et timestamp
- areContentsTheSame() : Compare les valeurs de pollution
```

#### Classification Automatique
```java
AirQualityClassifier.NiveauPollution niveau =
    AirQualityClassifier.classifierPollution(airQuality);
```

#### Couleurs Dynamiques

**Couleur de l'indicateur** : Basée sur le niveau de pollution
```java
String colorCode = AirQualityClassifier.obtenirCodeCouleur(niveau);
binding.viewColorIndicator.setBackgroundColor(Color.parseColor(colorCode));
```

**Couleur du badge AQI** : Basée sur l'indice (1-5)
```java
- AQI 1 : #00E400 (Vert foncé)
- AQI 2 : #92D050 (Vert clair)
- AQI 3 : #FFFF00 (Jaune)
- AQI 4 : #FF7E00 (Orange)
- AQI 5 : #FF0000 (Rouge)
```

## 3. ImagesFragment

### Description
Fragment qui affiche une galerie d'images représentant visuellement les différents niveaux de pollution atmosphérique.

### Fonctionnalités
- **Image principale** : Affiche le niveau de pollution actuel
- **Grille d'images** : Montre les 6 niveaux de pollution avec leurs couleurs
- **Mise en évidence** : Le niveau actuel a une élévation augmentée
- **Légende dynamique** : Affiche le niveau et l'AQI actuel

### Composants UI

#### Image Principale
- CardView avec image de 200dp de hauteur
- Légende superposée en bas avec fond semi-transparent
- Fond gradient coloré selon le niveau actuel

#### Grille de Niveaux (3 lignes × 2 colonnes)

**Ligne 1 :**
- Excellent (#00E400 - Vert foncé)
- Bon (#92D050 - Vert clair)

**Ligne 2 :**
- Modéré (#FFFF00 - Jaune)
- Mauvais (#FF7E00 - Orange)

**Ligne 3 :**
- Très Mauvais (#FF0000 - Rouge)
- Extrême (#8F3F97 - Violet)

### Code Important

#### Configuration des images de fond
```java
private void setupImages() {
    binding.imageViewExcellent.setBackgroundResource(R.drawable.bg_excellent);
    binding.imageViewGood.setBackgroundResource(R.drawable.bg_good);
    binding.imageViewModerate.setBackgroundResource(R.drawable.bg_moderate);
    binding.imageViewPoor.setBackgroundResource(R.drawable.bg_poor);
    binding.imageViewVeryPoor.setBackgroundResource(R.drawable.bg_very_poor);
    binding.imageViewExtreme.setBackgroundResource(R.drawable.bg_extreme);
}
```

#### Mise à jour de l'image principale
```java
private void updateMainImage(AirQuality airQuality) {
    AirQualityClassifier.NiveauPollution niveau =
        AirQualityClassifier.classifierPollution(airQuality);

    int backgroundResource = getBackgroundForNiveau(niveau);
    binding.imageViewMain.setBackgroundResource(backgroundResource);

    String caption = getNiveauText(niveau) + " - AQI: " + airQuality.getAqi();
    binding.textViewMainImageCaption.setText(caption);
}
```

#### Mise en évidence du niveau actuel
```java
private void highlightCurrentLevel(NiveauPollution niveau) {
    float normalElevation = 4f;
    float highlightedElevation = 12f;

    // Réinitialiser toutes les elevations à normale
    // Puis augmenter l'elevation du niveau actuel
}
```

## 4. Ressources Images

### Drawables Gradient

Tous les niveaux de pollution ont des drawables XML avec des gradients linéaires à 135° :

#### `bg_excellent.xml`
```xml
<gradient
    android:startColor="#00E400"
    android:endColor="#66FF66"
    android:angle="135" />
```

#### `bg_good.xml`
```xml
<gradient
    android:startColor="#92D050"
    android:endColor="#B8E986"
    android:angle="135" />
```

#### `bg_moderate.xml`
```xml
<gradient
    android:startColor="#FFFF00"
    android:endColor="#FFFF99"
    android:angle="135" />
```

#### `bg_poor.xml`
```xml
<gradient
    android:startColor="#FF7E00"
    android:endColor="#FFB366"
    android:angle="135" />
```

#### `bg_very_poor.xml`
```xml
<gradient
    android:startColor="#FF0000"
    android:endColor="#FF6666"
    android:angle="135" />
```

#### `bg_extreme.xml`
```xml
<gradient
    android:startColor="#8F3F97"
    android:endColor="#B87FBF"
    android:angle="135" />
```

### Icônes Vectorielles

#### `ic_air_quality.xml`
Icône représentant la qualité de l'air (utilisée pour le bouton Niveaux)
- Taille : 24dp × 24dp
- Couleur : Blanc (#FFFFFF)
- Style : Lignes de vent

#### `ic_list.xml`
Icône représentant une liste (utilisée pour le bouton Liste)
- Taille : 24dp × 24dp
- Couleur : Blanc (#FFFFFF)
- Style : Trois lignes avec puces

## 5. MainActivity avec Navigation

### Description
Activity principale qui gère la navigation entre les fragments via un BottomNavigationView.

### Fonctionnalités

#### ViewBinding
```java
private ActivityMainBinding binding;

@Override
protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
}
```

#### Configuration du BottomNavigationView
```java
private void setupBottomNavigation() {
    binding.bottomNavigation.setOnItemSelectedListener(item -> {
        Fragment selectedFragment = null;

        if (itemId == R.id.navigation_list) {
            selectedFragment = new PollutionListFragment();
        } else if (itemId == R.id.navigation_images) {
            selectedFragment = new ImagesFragment();
        }

        return loadFragment(selectedFragment);
    });
}
```

#### Chargement de Fragment
```java
private boolean loadFragment(Fragment fragment) {
    if (fragment != null) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit();
        return true;
    }
    return false;
}
```

#### Fragment Initial
Le fragment PollutionListFragment est chargé par défaut au démarrage :
```java
if (savedInstanceState == null) {
    loadFragment(new PollutionListFragment());
}
```

## 6. Flux de Données

### Architecture MVVM

```
1. MainActivity initialise le ViewModel
   ↓
2. ViewModel.loadAirQualityData(lat, lon, location)
   ↓
3. Repository récupère les données de l'API
   ↓
4. Repository convertit les données et met à jour LiveData
   ↓
5. Les Fragments observent les LiveData via getViewLifecycleOwner()
   ↓
6. UI se met à jour automatiquement
```

### Partage de Données entre Fragments

Les deux fragments utilisent le **même ViewModel** partagé au niveau de l'Activity :

```java
// Dans chaque fragment
@Override
public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // requireActivity() partage le ViewModel entre fragments
    viewModel = new ViewModelProvider(requireActivity()).get(AirQualityViewModel.class);
}
```

Avantages :
- Les deux fragments voient les mêmes données
- Pas de duplication d'appels API
- Changement de fragment = conservation des données

## 7. États UI

### PollutionListFragment

#### État de Chargement
```java
viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
    binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
});
```

#### État avec Données
```
ProgressBar : GONE
RecyclerView : VISIBLE
TextView Empty : GONE
```

#### État Vide
```
ProgressBar : GONE
RecyclerView : GONE
TextView Empty : VISIBLE
```

#### État d'Erreur
```java
viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
    if (errorMessage != null && !errorMessage.isEmpty()) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
});
```

### ImagesFragment

#### Observation Passive
Le fragment observe les données mais ne gère pas les états de chargement/erreur :
```java
viewModel.getAirQualityData().observe(getViewLifecycleOwner(), airQualityList -> {
    if (airQualityList != null && !airQualityList.isEmpty()) {
        updateMainImage(airQualityList.get(0));
    }
});
```

## 8. Utilisation

### Lancer l'Application

1. **Build et Run**
   ```bash
   # Dans Android Studio
   Build > Make Project
   Run > Run 'app'
   ```

2. **Au démarrage**
   - L'app charge automatiquement les données pour Paris
   - Le fragment Liste s'affiche par défaut
   - Les données se chargent dans le RecyclerView

3. **Navigation**
   - Appuyez sur l'icône **Liste** en bas pour voir les données détaillées
   - Appuyez sur l'icône **Niveaux** en bas pour voir la galerie d'images

### Changement de Localisation

Actuellement, l'app utilise Paris par défaut. Pour changer :

```java
// Dans MainActivity.java
private static final double DEFAULT_LATITUDE = 48.8566;  // Paris
private static final double DEFAULT_LONGITUDE = 2.3522;
private static final String DEFAULT_LOCATION = "Paris";
```

### Exemples de Coordonnées

```java
// New York
48.8566, 2.3522 → 40.7128, -74.0060

// Tokyo
48.8566, 2.3522 → 35.6762, 139.6503

// Delhi (Air très pollué)
48.8566, 2.3522 → 28.6139, 77.2090

// Reykjavik (Air excellent)
48.8566, 2.3522 → 64.1466, -21.9426
```

## 9. Personnalisation

### Modifier les Couleurs

Éditez les fichiers dans `res/drawable/` :
```xml
<!-- bg_excellent.xml -->
<gradient
    android:startColor="#VOTRE_COULEUR_DEBUT"
    android:endColor="#VOTRE_COULEUR_FIN"
    android:angle="135" />
```

### Ajouter des Polluants à l'Item

Dans `item_pollution.xml`, ajoutez de nouveaux TextView :
```xml
<TextView
    android:id="@+id/labelCO"
    android:text="CO:" />
<TextView
    android:id="@+id/textViewCO"
    android:text="250.5 μg/m³" />
```

Puis dans `PollutionAdapter.java` :
```java
binding.textViewCO.setText(String.format(Locale.getDefault(),
    "%.1f μg/m³", airQuality.getCo()));
```

### Changer les Icônes du Menu

Remplacez les fichiers dans `res/drawable/` :
- `ic_list.xml` : Icône du bouton Liste
- `ic_air_quality.xml` : Icône du bouton Niveaux

## 10. Tests

### Tester les Fragments

```java
@Test
public void testFragmentNavigation() {
    // Vérifier que PollutionListFragment se charge par défaut
    onView(withId(R.id.recyclerViewPollution)).check(matches(isDisplayed()));

    // Cliquer sur le bouton Images
    onView(withId(R.id.navigation_images)).perform(click());

    // Vérifier que ImagesFragment s'affiche
    onView(withId(R.id.imageViewMain)).check(matches(isDisplayed()));
}
```

### Tester le RecyclerView

```java
@Test
public void testRecyclerViewDisplaysData() {
    // Attendre le chargement des données
    onView(withId(R.id.recyclerViewPollution))
        .check(matches(isDisplayed()));

    // Vérifier qu'au moins un élément est affiché
    onView(withId(R.id.recyclerViewPollution))
        .perform(RecyclerViewActions.scrollToPosition(0));
}
```

## 11. Dépannage

### Le RecyclerView est vide

**Vérifications :**
1. Les données sont-elles chargées ? (Vérifier les logs)
2. L'adapter est-il configuré correctement ?
3. Le LayoutManager est-il défini ?
4. Le ViewModel est-il observé avec `getViewLifecycleOwner()` ?

### Les images ne s'affichent pas

**Vérifications :**
1. Les fichiers drawable existent-ils dans `res/drawable/` ?
2. Les noms de fichiers sont-ils corrects (snake_case) ?
3. Le XML est-il valide ?
4. Le projet est-il synchronisé avec Gradle ?

### Erreur de ViewBinding

**Solutions :**
1. Vérifier que `viewBinding true` est dans `build.gradle`
2. Clean et Rebuild le projet
3. Vérifier les imports des binding classes

### Navigation ne fonctionne pas

**Vérifications :**
1. Le menu `bottom_nav_menu.xml` existe-t-il ?
2. Les IDs dans le menu correspondent-ils au code ?
3. Le FragmentContainerView a-t-il le bon ID ?

## 12. Améliorations Futures

### Fonctionnalités Suggérées

1. **Pull-to-Refresh**
   ```java
   SwipeRefreshLayout pour rafraîchir les données
   ```

2. **Click sur Item**
   ```java
   Interface OnItemClickListener dans l'Adapter
   Afficher un dialog avec plus de détails
   ```

3. **Recherche de Localisation**
   ```java
   SearchView dans la toolbar
   Autocomplete avec l'API Google Places
   ```

4. **Favoris**
   ```java
   Sauvegarder des localisations favorites avec Room
   Afficher une liste de favoris
   ```

5. **Notifications**
   ```java
   Notifications push quand la pollution dépasse un seuil
   WorkManager pour les vérifications en arrière-plan
   ```

6. **Graphiques**
   ```java
   Utiliser MPAndroidChart
   Afficher des graphiques de tendances
   ```

7. **Mode Hors Ligne**
   ```java
   Cache avec Room Database
   Afficher les dernières données disponibles
   ```

## Résumé

L'application AIrAware utilise maintenant une architecture moderne avec :
- **2 Fragments** : Liste et Galerie d'images
- **RecyclerView** : Affichage efficace des données
- **Images Gradient** : Représentation visuelle des niveaux de pollution
- **Navigation** : BottomNavigationView pour basculer entre les vues
- **MVVM + Hilt** : Architecture propre et testable
- **ViewBinding** : Accès type-safe aux vues
- **LiveData** : Mise à jour réactive de l'UI

Les utilisateurs peuvent maintenant visualiser les données de pollution de deux manières complémentaires : une liste détaillée avec tous les polluants, et une galerie visuelle avec des couleurs représentant les niveaux de qualité de l'air.

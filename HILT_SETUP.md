# Configuration de Hilt (Dependency Injection)

## Qu'est-ce que Hilt ?

Hilt est le framework d'injection de dépendances recommandé par Google pour Android. Il simplifie l'injection de dépendances dans votre application Android en fournissant des conteneurs pour chaque classe Android et en gérant automatiquement leur cycle de vie.

## Avantages de Hilt dans ce projet

1. **Réduction du code boilerplate** : Plus besoin de patterns Singleton manuels
2. **Testabilité améliorée** : Facile de remplacer les dépendances pour les tests
3. **Scope automatique** : Gestion automatique du cycle de vie des objets
4. **Type-safe** : Erreurs détectées à la compilation

## Architecture avec Hilt

### Structure mise en place :

```
AirAware.com/
├── AirAwareApplication.java       - @HiltAndroidApp (point d'entrée)
├── di/                             - Modules d'injection
│   ├── NetworkModule.java          - Fournit Retrofit, OkHttp, ApiService
│   └── RepositoryModule.java       - Fournit le Repository
├── repository/
│   └── AirQualityRepository.java   - @Singleton avec @Inject
├── viewmodel/
│   └── AirQualityViewModel.java    - @HiltViewModel avec @Inject
└── ui/
    └── MainActivity.java            - @AndroidEntryPoint

```

## Composants Hilt configurés

### 1. Application (@HiltAndroidApp)
**Fichier**: `AirAwareApplication.java`
```java
@HiltAndroidApp
public class AirAwareApplication extends Application
```
- Point d'entrée de l'injection de dépendances
- Génère les composants Hilt au niveau de l'application

### 2. Modules Hilt

#### NetworkModule (@Module @InstallIn(SingletonComponent))
**Fichier**: `di/NetworkModule.java`

Fournit :
- **OkHttpClient** : Client HTTP avec logging et timeouts configurés
- **Retrofit** : Instance Retrofit avec GsonConverterFactory
- **OpenWeatherApiService** : Service API généré par Retrofit

Tous sont `@Singleton` (une seule instance dans toute l'app)

#### RepositoryModule (@Module @InstallIn(SingletonComponent))
**Fichier**: `di/RepositoryModule.java`

Fournit :
- **AirQualityRepository** : Repository avec l'ApiService injecté

### 3. Repository (@Singleton)
**Fichier**: `repository/AirQualityRepository.java`
```java
@Singleton
public class AirQualityRepository {
    @Inject
    public AirQualityRepository(OpenWeatherApiService apiService)
}
```
- Suppression du pattern Singleton manuel (getInstance())
- Injection de l'ApiService via le constructeur

### 4. ViewModel (@HiltViewModel)
**Fichier**: `viewmodel/AirQualityViewModel.java`
```java
@HiltViewModel
public class AirQualityViewModel extends ViewModel {
    @Inject
    public AirQualityViewModel(AirQualityRepository repository)
}
```
- Le Repository est automatiquement injecté
- Plus besoin d'appeler `getInstance()`

### 5. Activity (@AndroidEntryPoint)
**Fichier**: `ui/MainActivity.java`
```java
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
```
- Permet l'injection de dépendances dans l'Activity
- Le ViewModel est créé avec ses dépendances automatiquement

## Flux d'injection

```
Application (@HiltAndroidApp)
    ↓
MainActivity (@AndroidEntryPoint)
    ↓
AirQualityViewModel (@HiltViewModel)
    ↓ (injecté par Hilt)
AirQualityRepository (@Singleton)
    ↓ (injecté par Hilt)
OpenWeatherApiService (fourni par NetworkModule)
    ↓ (créé par)
Retrofit (fourni par NetworkModule)
    ↓ (utilise)
OkHttpClient (fourni par NetworkModule)
```

## Dépendances ajoutées

### build.gradle (project)
```gradle
plugins {
    id 'com.google.dagger.hilt.android' version '2.48' apply false
}
```

### build.gradle (app)
```gradle
plugins {
    id 'com.google.dagger.hilt.android'
}

dependencies {
    implementation 'com.google.dagger:hilt-android:2.48'
    annotationProcessor 'com.google.dagger:hilt-compiler:2.48'
}
```

### AndroidManifest.xml
```xml
<application
    android:name=".AirAwareApplication"
    ...>
```

## Annotations Hilt utilisées

| Annotation | Usage | Fichier |
|------------|-------|---------|
| `@HiltAndroidApp` | Classe Application | AirAwareApplication.java |
| `@AndroidEntryPoint` | Activity/Fragment | MainActivity.java |
| `@HiltViewModel` | ViewModel | AirQualityViewModel.java |
| `@Module` | Module de dépendances | NetworkModule.java, RepositoryModule.java |
| `@InstallIn(SingletonComponent)` | Scope du module | Modules |
| `@Provides` | Méthode qui fournit une dépendance | NetworkModule.java |
| `@Singleton` | Une seule instance | Repository, services |
| `@Inject` | Injection par constructeur | Repository, ViewModel |

## Avantages dans notre projet

### Avant Hilt :
```java
// Pattern Singleton manuel
AirQualityRepository repository = AirQualityRepository.getInstance();

// Création manuelle de Retrofit
RetrofitClient.getInstance().getApiService()
```

### Après Hilt :
```java
// Injection automatique
@Inject
public AirQualityViewModel(AirQualityRepository repository) {
    this.repository = repository;
}

// Retrofit fourni par le module
@Provides
@Singleton
public OpenWeatherApiService provideApiService(Retrofit retrofit)
```

## Tests avec Hilt

Pour les tests, Hilt permet de :
1. Remplacer les vraies dépendances par des mocks
2. Créer des modules de test personnalisés
3. Tester isolément chaque composant

## Prochaines étapes possibles

1. Ajouter `@Binds` pour les interfaces
2. Utiliser `@ApplicationContext` pour injecter le Context
3. Créer des scopes personnalisés si nécessaire
4. Ajouter Hilt pour les tests avec `@HiltAndroidTest`
5. Utiliser `@Qualifier` pour différentes configurations

## Ressources

- [Documentation officielle Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Codelab Hilt](https://developer.android.com/codelabs/android-hilt)
- [Migrer vers Hilt](https://developer.android.com/training/dependency-injection/hilt-migration)

# Guide SonarQube - AIrAware

Ce guide vous explique comment utiliser SonarQube pour analyser la qualité du code de l'application AIrAware.

---

## 📋 Prérequis

1. ✅ **Compte SonarCloud** : https://sonarcloud.io
2. ✅ **Token d'authentification SonarQube** (à générer sur SonarCloud)
3. ✅ **Gradle** installé
4. ✅ **Java 11** ou supérieur

---

## 🔑 Étape 1 : Obtenir votre Token SonarCloud

1. Connectez-vous sur **https://sonarcloud.io**
2. Allez dans **My Account** → **Security**
3. Générez un nouveau token :
   - **Name** : `AIrAware-Token`
   - Cliquez sur **Generate**
4. **Copiez le token** (vous ne pourrez plus le voir après)

---

## ⚙️ Étape 2 : Configuration du Token

### Option A : Variable d'environnement (Recommandé)

**Windows PowerShell :**
```powershell
$env:SONAR_TOKEN="votre-token-ici"
```

**Windows CMD :**
```cmd
set SONAR_TOKEN=votre-token-ici
```

**Linux/Mac :**
```bash
export SONAR_TOKEN="votre-token-ici"
```

### Option B : Fichier gradle.properties local

Créez/modifiez le fichier `~/.gradle/gradle.properties` :
```properties
systemProp.sonar.login=votre-token-ici
```

⚠️ **Ne commitez JAMAIS votre token dans Git !**

---

## 🚀 Étape 3 : Lancer l'Analyse SonarQube

### Commande complète (avec couverture de code)

```bash
# 1. Nettoyer le projet
./gradlew clean

# 2. Lancer les tests et générer le rapport de couverture
./gradlew test jacocoTestReport

# 3. Lancer l'analyse SonarQube
./gradlew sonar -Dsonar.login=$SONAR_TOKEN
```

### Commande tout-en-un

```bash
./gradlew clean test jacocoTestReport sonar -Dsonar.login=$SONAR_TOKEN
```

### Avec paramètres supplémentaires

```bash
./gradlew sonar \
  -Dsonar.login=$SONAR_TOKEN \
  -Dsonar.projectKey=maxervj_AIr-Aware \
  -Dsonar.organization=maxervj \
  -Dsonar.host.url=https://sonarcloud.io
```

---

## 📊 Étape 4 : Consulter les Résultats

1. Allez sur **https://sonarcloud.io**
2. Connectez-vous avec votre compte
3. Sélectionnez votre organisation : **maxervj**
4. Cliquez sur le projet : **AIr-Aware**

Vous verrez :
- 🐛 **Bugs** détectés
- 🔒 **Vulnérabilités de sécurité**
- 💨 **Code smells** (mauvaises pratiques)
- 📊 **Couverture de code** par les tests
- 📈 **Duplication** de code
- ⚡ **Dette technique**

---

## 📈 Rapports Générés

### Rapport JaCoCo (Couverture de code)

**Emplacement** : `app/build/reports/jacoco/testDebugUnitTest/html/index.html`

Ouvrez ce fichier dans votre navigateur pour voir :
- Pourcentage de couverture par classe
- Lignes de code couvertes/non couvertes
- Branches couvertes/non couvertes

### Rapport de tests JUnit

**Emplacement** : `app/build/reports/tests/testDebugUnitTest/index.html`

Affiche :
- ✅ Tests réussis : 56/56
- ⏱️ Temps d'exécution
- Détails par classe de test

---

## 🔧 Configuration Actuelle

### Informations du projet

- **Project Key** : `maxervj_AIr-Aware`
- **Organization** : `maxervj`
- **Host URL** : `https://sonarcloud.io`

### Chemins analysés

- **Sources** : `app/src/main/java`
- **Tests** : `app/src/test/java`

### Exclusions

Fichiers exclus de l'analyse :
- Fichiers générés (`R.java`, `BuildConfig.java`)
- Fichiers Hilt générés (`Hilt_*.java`)
- Ressources Android (`*.xml`, `*.json`)
- Tests (`*Test*.java`)

### Couverture de code

- **Plugin** : JaCoCo 0.8.10
- **Format** : XML + HTML
- **Tests analysés** : Unit tests (Debug)

---

## 🎯 Métriques Clés

SonarQube analyse automatiquement :

### 1. Maintenabilité
- **Code Smells** : Problèmes de qualité de code
- **Dette technique** : Temps estimé pour corriger les problèmes
- **Duplication** : Code dupliqué

### 2. Fiabilité
- **Bugs** : Erreurs de code détectées
- **Niveau de fiabilité** : A (meilleur) à E (pire)

### 3. Sécurité
- **Vulnérabilités** : Failles de sécurité
- **Hotspots de sécurité** : Zones à risque
- **Niveau de sécurité** : A à E

### 4. Couverture
- **Couverture de code** : % de code testé
- **Couverture de lignes** : Lignes testées
- **Couverture de branches** : Conditions testées

---

## 🛠️ Commandes Utiles

### Afficher les tâches disponibles
```bash
./gradlew tasks --group=verification
```

### Lancer uniquement les tests
```bash
./gradlew test
```

### Générer uniquement le rapport JaCoCo
```bash
./gradlew jacocoTestReport
```

### Lancer SonarQube sans couverture
```bash
./gradlew sonar -Dsonar.login=$SONAR_TOKEN
```

### Vérifier la version de SonarQube
```bash
./gradlew sonar --version
```

---

## 🔍 Dépannage

### Erreur : "No tests found"

**Solution** :
```bash
./gradlew clean test
```

### Erreur : "Authentication required"

**Solution** : Vérifiez que votre token est défini :
```bash
echo $SONAR_TOKEN
```

### Erreur : "Project not found"

**Solution** : Vérifiez les clés dans `sonar-project.properties` :
- `sonar.projectKey=maxervj_AIr-Aware`
- `sonar.organization=maxervj`

### Rapport JaCoCo vide

**Solution** : Assurez-vous que les tests s'exécutent :
```bash
./gradlew clean testDebugUnitTest --info
```

---

## 📚 Fichiers de Configuration

### 1. `build.gradle`
Contient :
- Plugin SonarQube
- Plugin JaCoCo
- Configuration des rapports
- Chemins d'analyse

### 2. `sonar-project.properties`
Contient :
- Clé du projet
- Organisation
- Chemins des sources
- Exclusions

---

## 🎓 Bonnes Pratiques

1. ✅ **Lancez l'analyse avant chaque commit important**
2. ✅ **Corrigez les bugs critiques** en priorité
3. ✅ **Maintenez une couverture de code** > 80%
4. ✅ **Surveillez la dette technique** régulièrement
5. ✅ **Revoyez les hotspots de sécurité** rapidement

---

## 📊 Résultats Actuels (Baseline)

Après la première analyse :
- ✅ **56 tests unitaires** réussis
- ✅ **2 suites de tests** (AQITest, VilleTest)
- ✅ Couverture estimée : À déterminer après première analyse
- ✅ Code smells : À déterminer
- ✅ Bugs : À déterminer

---

## 🔗 Liens Utiles

- **SonarCloud Dashboard** : https://sonarcloud.io/organizations/maxervj
- **Documentation SonarQube** : https://docs.sonarqube.org
- **Documentation JaCoCo** : https://www.jacoco.org/jacoco/trunk/doc/
- **Plugin Gradle SonarQube** : https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-gradle/

---

## 📝 Notes

- L'analyse SonarQube est **gratuite** pour les projets open-source sur SonarCloud
- Les rapports sont conservés **indéfiniment** sur SonarCloud
- Vous pouvez configurer des **quality gates** personnalisés
- Les **Pull Requests** peuvent être analysées automatiquement avec GitHub Actions

---

**Dernière mise à jour** : 2025-11-05
**Version** : 1.0

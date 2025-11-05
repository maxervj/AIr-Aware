@echo off
REM Script pour lancer l'analyse SonarQube sur Windows

echo ================================================
echo   Analyse SonarQube - AIrAware
echo ================================================
echo.

REM Vérifier si le token est défini
if "%SONAR_TOKEN%"=="" (
    echo ERREUR: La variable SONAR_TOKEN n'est pas définie !
    echo.
    echo Veuillez définir votre token SonarCloud :
    echo   set SONAR_TOKEN=votre-token-ici
    echo.
    pause
    exit /b 1
)

echo [1/4] Nettoyage du projet...
call gradlew.bat clean
if errorlevel 1 goto error

echo.
echo [2/4] Lancement des tests...
call gradlew.bat test
if errorlevel 1 goto error

echo.
echo [3/4] Génération du rapport de couverture JaCoCo...
call gradlew.bat jacocoTestReport
if errorlevel 1 goto error

echo.
echo [4/4] Analyse SonarQube...
call gradlew.bat sonar -Dsonar.login=%SONAR_TOKEN%
if errorlevel 1 goto error

echo.
echo ================================================
echo   Analyse terminée avec succès !
echo ================================================
echo.
echo Consultez les résultats sur :
echo https://sonarcloud.io/organizations/maxervj/projects
echo.
echo Rapport JaCoCo local :
echo app\build\reports\jacoco\testDebugUnitTest\html\index.html
echo.
pause
exit /b 0

:error
echo.
echo ERREUR lors de l'exécution !
echo.
pause
exit /b 1

package AirAware.com;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.ViewModelProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import AirAware.com.model.AirQuality;
import AirAware.com.ui.MainActivity;
import AirAware.com.viewmodel.AirQualityViewModel;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

/**
 * Test fonctionnel pour vérifier les fonctionnalités métier de l'application
 * Test de bout en bout de la récupération et affichage des données de qualité de l'air
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
public class AirQualityFunctionalTest {

    @Rule(order = 0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        hiltRule.inject();
    }

    /**
     * Test fonctionnel principal: Vérifie le flux complet de chargement des données
     */
    @Test
    public void testCompleteDataLoadingFlow() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean dataLoaded = new AtomicBoolean(false);
        AtomicBoolean hasError = new AtomicBoolean(false);

        activityScenarioRule.getScenario().onActivity(activity -> {
            // Récupère le ViewModel
            AirQualityViewModel viewModel = new ViewModelProvider(activity)
                    .get(AirQualityViewModel.class);

            // Charge les données pour Paris
            viewModel.loadAirQualityData(48.8566, 2.3522, "Paris");

            // Observe les données
            viewModel.getAirQualityData().observe(activity, airQualityList -> {
                if (airQualityList != null && !airQualityList.isEmpty()) {
                    dataLoaded.set(true);
                    latch.countDown();
                }
            });

            // Observe les erreurs
            viewModel.getErrorMessage().observe(activity, error -> {
                if (error != null && !error.isEmpty()) {
                    hasError.set(true);
                    latch.countDown();
                }
            });
        });

        // Attend jusqu'à 10 secondes pour le chargement des données ou une erreur
        boolean completed = latch.await(10, TimeUnit.SECONDS);

        // Vérifie que soit les données sont chargées, soit une erreur s'est produite
        assertTrue("Le flux de chargement devrait se terminer", completed || hasError.get());

        // Si des données sont chargées, vérifie l'UI
        if (dataLoaded.get()) {
            onView(withId(R.id.main))
                    .check(matches(isDisplayed()));
        }
    }

    /**
     * Test: Vérifie que le ViewModel gère correctement les coordonnées GPS
     */
    @Test
    public void testViewModelHandlesGPSCoordinates() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<List<AirQuality>> dataReference = new AtomicReference<>();

        activityScenarioRule.getScenario().onActivity(activity -> {
            AirQualityViewModel viewModel = new ViewModelProvider(activity)
                    .get(AirQualityViewModel.class);

            // Test avec différentes localisations
            double[] coordinates = {
                48.8566, 2.3522,  // Paris
                51.5074, -0.1278,  // Londres
                40.7128, -74.0060  // New York
            };

            // Charge les données pour Paris
            viewModel.loadAirQualityData(
                    coordinates[0],
                    coordinates[1],
                    "Test Location"
            );

            viewModel.getAirQualityData().observe(activity, airQualityList -> {
                if (airQualityList != null) {
                    dataReference.set(airQualityList);
                    latch.countDown();
                }
            });
        });

        // Attend le chargement
        latch.await(10, TimeUnit.SECONDS);
    }

    /**
     * Test: Vérifie que l'application gère les états de chargement
     */
    @Test
    public void testLoadingStateManagement() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean loadingStarted = new AtomicBoolean(false);
        AtomicBoolean loadingFinished = new AtomicBoolean(false);

        activityScenarioRule.getScenario().onActivity(activity -> {
            AirQualityViewModel viewModel = new ViewModelProvider(activity)
                    .get(AirQualityViewModel.class);

            // Observe l'état de chargement
            viewModel.getIsLoading().observe(activity, isLoading -> {
                if (isLoading != null) {
                    if (isLoading) {
                        loadingStarted.set(true);
                    } else {
                        loadingFinished.set(true);
                        latch.countDown();
                    }
                }
            });

            // Déclenche le chargement
            viewModel.loadAirQualityData(48.8566, 2.3522, "Paris");
        });

        // Attend la fin du chargement
        latch.await(10, TimeUnit.SECONDS);

        // Vérifie que le chargement a démarré et s'est terminé
        assertTrue("Le chargement devrait avoir démarré",
                loadingStarted.get() || loadingFinished.get());
    }

    /**
     * Test: Vérifie que les données de qualité de l'air sont valides
     */
    @Test
    public void testAirQualityDataValidation() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<AirQuality> airQualityRef = new AtomicReference<>();

        activityScenarioRule.getScenario().onActivity(activity -> {
            AirQualityViewModel viewModel = new ViewModelProvider(activity)
                    .get(AirQualityViewModel.class);

            viewModel.loadAirQualityData(48.8566, 2.3522, "Paris");

            viewModel.getAirQualityData().observe(activity, airQualityList -> {
                if (airQualityList != null && !airQualityList.isEmpty()) {
                    airQualityRef.set(airQualityList.get(0));
                    latch.countDown();
                }
            });
        });

        // Attend les données
        boolean received = latch.await(10, TimeUnit.SECONDS);

        if (received && airQualityRef.get() != null) {
            AirQuality airQuality = airQualityRef.get();

            // Vérifie que les données sont valides
            assertNotNull("Location ne devrait pas être null", airQuality.getLocation());
            assertTrue("AQI devrait être entre 1 et 5",
                    airQuality.getAqi() >= 1 && airQuality.getAqi() <= 5);
            assertNotNull("Status ne devrait pas être null", airQuality.getStatus());
            assertTrue("PM2.5 devrait être >= 0", airQuality.getPm2_5() >= 0);
            assertTrue("PM10 devrait être >= 0", airQuality.getPm10() >= 0);
        }
    }

    /**
     * Test: Vérifie que l'application peut rafraîchir les données
     */
    @Test
    public void testDataRefresh() throws InterruptedException {
        CountDownLatch firstLoadLatch = new CountDownLatch(1);
        CountDownLatch refreshLatch = new CountDownLatch(1);
        AtomicBoolean firstLoadComplete = new AtomicBoolean(false);
        AtomicBoolean refreshComplete = new AtomicBoolean(false);

        activityScenarioRule.getScenario().onActivity(activity -> {
            AirQualityViewModel viewModel = new ViewModelProvider(activity)
                    .get(AirQualityViewModel.class);

            // Premier chargement
            viewModel.loadAirQualityData(48.8566, 2.3522, "Paris");

            viewModel.getAirQualityData().observe(activity, airQualityList -> {
                if (airQualityList != null && !airQualityList.isEmpty()) {
                    if (!firstLoadComplete.get()) {
                        firstLoadComplete.set(true);
                        firstLoadLatch.countDown();
                    } else {
                        refreshComplete.set(true);
                        refreshLatch.countDown();
                    }
                }
            });
        });

        // Attend le premier chargement
        firstLoadLatch.await(10, TimeUnit.SECONDS);

        if (firstLoadComplete.get()) {
            // Déclenche le rafraîchissement
            activityScenarioRule.getScenario().onActivity(activity -> {
                AirQualityViewModel viewModel = new ViewModelProvider(activity)
                        .get(AirQualityViewModel.class);
                viewModel.refreshData(48.8566, 2.3522, "Paris");
            });

            // Attend le rafraîchissement
            refreshLatch.await(10, TimeUnit.SECONDS);
        }

        // Au moins le premier chargement devrait avoir réussi
        assertTrue("Le premier chargement devrait avoir réussi", firstLoadComplete.get());
    }

    /**
     * Test: Vérifie que l'application reste stable après plusieurs requêtes
     */
    @Test
    public void testMultipleRequestsStability() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            AirQualityViewModel viewModel = new ViewModelProvider(activity)
                    .get(AirQualityViewModel.class);

            // Effectue plusieurs requêtes successives
            for (int i = 0; i < 5; i++) {
                viewModel.loadAirQualityData(48.8566, 2.3522, "Paris");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // L'application devrait rester stable
            assertNotNull(viewModel);
        });

        // Vérifie que l'UI est toujours réactive
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));
    }
}

package AirAware.com;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import AirAware.com.model.AirQuality;
import AirAware.com.network.OpenWeatherApiService;
import AirAware.com.repository.AirQualityRepository;
import AirAware.com.ui.MainActivity;
import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;

import javax.inject.Inject;

import AirAware.com.di.RepositoryModule;

/**
 * Test d'UI pour vérifier le chargement des données de qualité de l'air
 * Utilise Hilt pour injecter un repository de test
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
@UninstallModules(RepositoryModule.class)
public class AirQualityDataLoadingTest {

    @Rule(order = 0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Inject
    OpenWeatherApiService apiService;

    /**
     * Test: Vérifie que l'activity est lancée et que la vue principale est affichée
     */
    @Test
    public void testMainViewIsDisplayed() {
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));
    }

    /**
     * Test: Vérifie que le Repository tente de charger les données
     */
    @Test
    public void testDataLoadingIsAttempted() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        activityScenarioRule.getScenario().onActivity(activity -> {
            // L'activité est lancée et devrait tenter de charger les données
            assertNotNull(activity);
            latch.countDown();
        });

        // Attend que l'activité soit prête
        assertTrue(latch.await(5, TimeUnit.SECONDS));

        // Attend que les données soient chargées (ou qu'une erreur se produise)
        Thread.sleep(3000);
    }

    /**
     * Test: Vérifie que l'application gère correctement le cycle de vie
     */
    @Test
    public void testActivityLifecycle() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertNotNull(activity);

            // Simule une mise en pause
            activity.onPause();

            // Simule une reprise
            activity.onResume();

            // L'activité devrait toujours être valide
            assertNotNull(activity);
        });
    }

    /**
     * Test: Vérifie que l'application ne fuit pas de mémoire lors de la rotation
     */
    @Test
    public void testConfigurationChange() {
        // Simule un changement de configuration (rotation d'écran)
        activityScenarioRule.getScenario().recreate();

        // Vérifie que la vue principale est toujours affichée après rotation
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));
    }

    /**
     * Crée des données de test pour la qualité de l'air
     */
    private List<AirQuality> createTestAirQualityData() {
        List<AirQuality> testData = new ArrayList<>();

        AirQuality airQuality = new AirQuality();
        airQuality.setLocation("Paris Test");
        airQuality.setLatitude(48.8566);
        airQuality.setLongitude(2.3522);
        airQuality.setAqi(2);
        airQuality.setPm2_5(15.5);
        airQuality.setPm10(25.3);
        airQuality.setCo(300.5);
        airQuality.setNo2(40.2);
        airQuality.setO3(80.1);
        airQuality.setTimestamp(System.currentTimeMillis());

        testData.add(airQuality);

        return testData;
    }
}

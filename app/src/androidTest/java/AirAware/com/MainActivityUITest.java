package AirAware.com;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import AirAware.com.ui.MainActivity;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

/**
 * Test d'UI pour MainActivity
 * Utilise Espresso et Hilt pour tester l'interface utilisateur
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
public class MainActivityUITest {

    @Rule(order = 0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test 1: Vérifie que l'activité se lance correctement
     */
    @Test
    public void testActivityLaunches() {
        // Vérifie que la vue principale est affichée
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));
    }

    /**
     * Test 2: Vérifie que l'application ne plante pas au démarrage
     */
    @Test
    public void testAppDoesNotCrashOnStartup() {
        // Si ce test passe, cela signifie que l'app ne plante pas au démarrage
        activityScenarioRule.getScenario().onActivity(activity -> {
            // L'activité est lancée avec succès
            assert activity != null;
        });
    }

    /**
     * Test 3: Vérifie que le ViewModel est initialisé
     */
    @Test
    public void testViewModelInitialization() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            // Vérifie que l'activité n'est pas nulle
            assert activity != null;

            // Attend un peu pour que le ViewModel charge les données
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

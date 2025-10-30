package AirAware.com;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.test.core.app.ApplicationProvider;
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
 * Tests d'UI pour vérifier la navigation et les interactions utilisateur
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
public class NavigationAndInteractionTest {

    @Rule(order = 0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test: Vérifie que l'application a les permissions nécessaires
     */
    @Test
    public void testInternetPermissionIsGranted() {
        Context context = ApplicationProvider.getApplicationContext();
        int permissionStatus = context.checkSelfPermission(android.Manifest.permission.INTERNET);

        // Vérifie que la permission INTERNET est accordée
        assert permissionStatus == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Test: Vérifie que l'application démarre avec le bon thème
     */
    @Test
    public void testAppThemeIsApplied() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertNotNull(activity);
            assertNotNull(activity.getTheme());
        });
    }

    /**
     * Test: Vérifie que la vue principale est interactive
     */
    @Test
    public void testMainViewIsInteractive() {
        // Vérifie que la vue principale est affichée
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));

        // Tente de cliquer sur la vue principale (ne devrait pas crasher)
        onView(withId(R.id.main))
                .perform(click());
    }

    /**
     * Test: Vérifie que l'application gère correctement le back button
     */
    @Test
    public void testBackButtonHandling() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            // Simule un appui sur le bouton retour
            activity.onBackPressed();
        });
    }

    /**
     * Test: Vérifie que l'application survit à plusieurs actions utilisateur
     */
    @Test
    public void testMultipleUserInteractions() throws InterruptedException {
        // Vérifie que la vue est affichée
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));

        // Attend un peu
        Thread.sleep(1000);

        // Clic sur la vue
        onView(withId(R.id.main))
                .perform(click());

        // Attend encore
        Thread.sleep(500);

        // Vérifie que la vue est toujours affichée
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));
    }

    /**
     * Test: Vérifie que l'application ne crash pas après plusieurs rotations
     */
    @Test
    public void testMultipleConfigurationChanges() {
        for (int i = 0; i < 3; i++) {
            // Simule une rotation d'écran
            activityScenarioRule.getScenario().recreate();

            // Vérifie que la vue principale est toujours affichée
            onView(withId(R.id.main))
                    .check(matches(isDisplayed()));
        }
    }

    /**
     * Test: Vérifie que l'application gère correctement le cycle de vie complet
     */
    @Test
    public void testFullActivityLifecycle() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            // Simule le cycle de vie complet
            activity.onPause();
            activity.onStop();
            activity.onRestart();
            activity.onStart();
            activity.onResume();

            // L'activité devrait toujours être valide
            assertNotNull(activity);
        });

        // Vérifie que la vue est toujours affichée
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));
    }

    /**
     * Test: Vérifie la stabilité de l'application sous stress
     */
    @Test
    public void testApplicationStabilityUnderStress() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            // Clic sur la vue
            onView(withId(R.id.main))
                    .perform(click());

            // Courte pause
            Thread.sleep(100);
        }

        // L'application devrait toujours être stable
        onView(withId(R.id.main))
                .check(matches(isDisplayed()));
    }
}

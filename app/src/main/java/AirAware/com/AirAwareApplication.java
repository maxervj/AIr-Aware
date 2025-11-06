package AirAware.com;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

/**
 * Classe Application pour l'initialisation de Hilt
 */
@HiltAndroidApp
public class    AirAwareApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialisation de l'application
    }
}

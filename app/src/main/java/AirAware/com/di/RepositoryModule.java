package AirAware.com.di;

import AirAware.com.network.OpenWeatherApiService;
import AirAware.com.repository.AirQualityRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

import javax.inject.Singleton;

/**
 * Module Hilt pour fournir les repositories
 */
@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    /**
     * Fournit une instance du Repository
     */
    @Provides
    @Singleton
    public AirQualityRepository provideAirQualityRepository(OpenWeatherApiService apiService) {
        return new AirQualityRepository(apiService);
    }
}

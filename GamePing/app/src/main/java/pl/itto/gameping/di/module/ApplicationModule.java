package pl.itto.gameping.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pl.itto.gameping.data.DataManager;
import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.data.local.ILocalDataHelper;
import pl.itto.gameping.data.local.LocalDataHelper;
import pl.itto.gameping.di.ApplicationContext;

/**
 * Created by PL_itto on 11/21/2017.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    IDataManager provideDataManager(DataManager dataManager) {
        return dataManager;
    }

    @Provides
    ILocalDataHelper provideLocalHelper(LocalDataHelper localDataHelper) {
        return localDataHelper;
    }
}

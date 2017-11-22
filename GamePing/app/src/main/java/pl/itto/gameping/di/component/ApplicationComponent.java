package pl.itto.gameping.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import pl.itto.gameping.MainApp;
import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.di.ApplicationContext;
import pl.itto.gameping.di.module.ApplicationModule;

/**
 * Created by PL_itto on 11/21/2017.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainApp mainApp);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    IDataManager getDataManager();
}

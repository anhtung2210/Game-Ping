package pl.itto.gameping;

import android.app.Application;

import javax.inject.Inject;

import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.di.component.ApplicationComponent;
import pl.itto.gameping.di.component.DaggerApplicationComponent;
import pl.itto.gameping.di.module.ApplicationModule;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class MainApp extends Application {
    @Inject
    IDataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        getComponent().inject(this);

    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this)).build();
        }
        return mApplicationComponent;
    }
}

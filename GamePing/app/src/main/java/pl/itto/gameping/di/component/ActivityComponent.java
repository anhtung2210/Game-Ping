package pl.itto.gameping.di.component;

import dagger.Component;
import pl.itto.gameping.di.PerActivity;
import pl.itto.gameping.di.module.ActivityModule;
import pl.itto.gameping.ui.gameselect.view.GameSelectActivity;

/**
 * Created by PL_itto on 11/21/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(GameSelectActivity activity);
}

package pl.itto.gameping.di.component;

import dagger.Component;
import pl.itto.gameping.di.PerActivity;
import pl.itto.gameping.di.module.FragmentModule;
import pl.itto.gameping.ui.areaselect.view.AreaPingFragment;
import pl.itto.gameping.ui.gameselect.view.GameSelectFragment;
import pl.itto.gameping.ui.serverping.view.ServerPingFragment;

/**
 * Created by PL_itto on 11/22/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(GameSelectFragment fragment);

    void inject(AreaPingFragment fragment);

    void inject(ServerPingFragment fragment);
}

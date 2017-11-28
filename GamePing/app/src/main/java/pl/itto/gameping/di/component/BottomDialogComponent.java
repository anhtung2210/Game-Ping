package pl.itto.gameping.di.component;


import dagger.Component;
import pl.itto.gameping.di.PerActivity;
import pl.itto.gameping.di.module.BottomDialogModule;
import pl.itto.gameping.ui.gameselect.customserver.view.CustomServerDialogFragment;

/**
 * Created by PL_itto on 10/5/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = BottomDialogModule.class)
public interface BottomDialogComponent {
    void inject(CustomServerDialogFragment fragment);
}

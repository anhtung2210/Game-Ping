package pl.itto.gameping.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialogFragment;


import dagger.Module;
import dagger.Provides;
import pl.itto.gameping.base.BaseBottomDialogFragment;
import pl.itto.gameping.di.ActivityContext;

/**
 * Created by PL_itto on 10/5/2017.
 */
@Module
public class BottomDialogModule {
    BaseBottomDialogFragment fragment;

    public BottomDialogModule(BaseBottomDialogFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return fragment.getContext();
    }

    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }

    @Provides
    BottomSheetDialogFragment provideFragment() {
        return fragment;
    }

}

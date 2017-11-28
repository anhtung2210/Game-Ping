package pl.itto.gameping.di.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pl.itto.gameping.base.BaseFragment;
import pl.itto.gameping.di.ActivityContext;
import pl.itto.gameping.ui.areaselect.IAreaPingContract;
import pl.itto.gameping.ui.areaselect.IAreaPingContract.IAreaPingView;
import pl.itto.gameping.ui.areaselect.presenter.AreaPingPresenter;
import pl.itto.gameping.ui.gameselect.IGameSelectContract.IGameSelectPresenter;
import pl.itto.gameping.ui.gameselect.IGameSelectContract.IGameSelectVIew;
import pl.itto.gameping.ui.gameselect.presenter.GameSelectPresenter;
import pl.itto.gameping.ui.serverping.IServerPingContract;
import pl.itto.gameping.ui.serverping.presenter.ServerPingPresenter;

/**
 * Created by PL_itto on 11/22/2017.
 */
@Module
public class FragmentModule {
    private BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @ActivityContext
    public Context provideContext() {
        return mFragment.getContext();
    }

    @Provides
    Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    IGameSelectPresenter<IGameSelectVIew> provideMainPresenter(GameSelectPresenter<IGameSelectVIew> presenter) {
        return presenter;
    }

    @Provides
    IAreaPingContract.IAreaPingPresenter<IAreaPingView> provideAreaPingPresenter(AreaPingPresenter<IAreaPingView> presenter) {
        return presenter;
    }

    @Provides
    IServerPingContract.IServerPingPresenter<IServerPingContract.IServerPingView> provideServerPingPresenter(ServerPingPresenter<IServerPingContract.IServerPingView> presenter) {
        return presenter;
    }
}

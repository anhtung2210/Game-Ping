package pl.itto.gameping.ui.serverping.presenter;

import pl.itto.gameping.base.BasePresenter;
import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.ui.serverping.IServerPingContract.IServerPingPresenter;
import pl.itto.gameping.ui.serverping.IServerPingContract.IServerPingView;

/**
 * Created by PL_itto on 11/29/2017.
 */

public class ServerPingPresenter<V extends IServerPingView> extends BasePresenter<V> implements IServerPingPresenter<V> {
    private static final String TAG = "PL_itto." + ServerPingPresenter.class.getSimpleName();

    public ServerPingPresenter(IDataManager dataManager) {
        super(dataManager);
    }
}

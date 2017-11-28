package pl.itto.gameping.ui.serverping;

import pl.itto.gameping.base.IBasePresenter;
import pl.itto.gameping.base.IBaseView;

/**
 * Created by PL_itto on 11/27/2017.
 */

public interface IServerPingContract {
    interface IServerPingView extends IBaseView {

    }

    interface IServerPingPresenter<V extends IServerPingView> extends IBasePresenter<V> {

    }
}

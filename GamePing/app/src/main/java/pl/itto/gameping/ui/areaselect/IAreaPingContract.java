package pl.itto.gameping.ui.areaselect;

import pl.itto.gameping.base.IBasePresenter;
import pl.itto.gameping.base.IBaseView;

/**
 * Created by PL_itto on 11/22/2017.
 */

public interface IAreaPingContract {
    interface IAreaPingView extends IBaseView {
        
    }

    interface IAreaPingPresenter<V extends IAreaPingView> extends IBasePresenter<V> {

    }
}

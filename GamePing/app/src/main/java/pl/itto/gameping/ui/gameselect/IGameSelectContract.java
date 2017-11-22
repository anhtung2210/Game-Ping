package pl.itto.gameping.ui.gameselect;

import pl.itto.gameping.base.IBasePresenter;
import pl.itto.gameping.base.IBaseView;

/**
 * Created by PL_itto on 11/22/2017.
 */

public interface IGameSelectContract {
    interface IGameSelectVIew extends IBaseView {

    }

    interface IGameSelectPresenter<V extends IGameSelectVIew> extends IBasePresenter<V> {

    }
}

package pl.itto.gameping.ui.gameselect.customserver;

import pl.itto.gameping.base.IBasePresenter;
import pl.itto.gameping.base.IBaseView;

/**
 * Created by PL_itto on 11/23/2017.
 */

public interface ICustomGameContract {
    interface ICustomGameView extends IBaseView {

    }

    interface ICustomGamePresenter<V extends ICustomGameView> extends IBasePresenter<V> {
    }
}

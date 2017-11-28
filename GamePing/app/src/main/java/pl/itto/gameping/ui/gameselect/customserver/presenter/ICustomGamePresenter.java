package pl.itto.gameping.ui.gameselect.customserver.presenter;

import pl.itto.gameping.base.BasePresenter;
import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.ui.gameselect.customserver.ICustomGameContract;

/**
 * Created by PL_itto on 11/23/2017.
 */

public class ICustomGamePresenter<V extends ICustomGameContract.ICustomGameView> extends BasePresenter<V>
        implements ICustomGameContract.ICustomGamePresenter<V> {
    public ICustomGamePresenter(IDataManager dataManager) {
        super(dataManager);
    }
}

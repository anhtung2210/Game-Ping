package pl.itto.gameping.ui.gameselect.presenter;

import javax.inject.Inject;

import pl.itto.gameping.base.BasePresenter;
import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.ui.gameselect.IGameSelectContract;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class GameSelectPresenter<V extends IGameSelectContract.IGameSelectVIew> extends BasePresenter<V>
        implements IGameSelectContract.IGameSelectPresenter<V> {

    @Inject
    public GameSelectPresenter(IDataManager dataManager) {
        super(dataManager);
    }


}

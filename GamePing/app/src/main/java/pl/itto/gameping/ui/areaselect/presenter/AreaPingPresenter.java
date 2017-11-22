package pl.itto.gameping.ui.areaselect.presenter;

import javax.inject.Inject;

import pl.itto.gameping.base.BasePresenter;
import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.ui.areaselect.IAreaPingContract;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class AreaPingPresenter<V extends IAreaPingContract.IAreaPingView> extends BasePresenter<V>
        implements IAreaPingContract.IAreaPingPresenter<V> {

    private static final String TAG = "PL_itto.AreaPingPresenter";

    @Inject
    public AreaPingPresenter(IDataManager dataManager) {
        super(dataManager);
    }
}

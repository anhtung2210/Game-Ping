package pl.itto.gameping.base;

import javax.inject.Inject;

import pl.itto.gameping.data.IDataManager;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    private static final String TAG = "PL_itto.BasePresenter";

    private final IDataManager mDataManager;
    private V mView;

    @Inject
    public BasePresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void onAttach(V view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    public V getMvpView() {
        return mView;
    }

    public boolean isViewAttached() {
        return mView == null;

    }
}

package pl.itto.gameping.ui.gameselect.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import pl.itto.gameping.base.BasePresenter;
import pl.itto.gameping.base.IActionCallback;
import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.di.ApplicationContext;
import pl.itto.gameping.ui.gameselect.IGameSelectContract;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class GameSelectPresenter<V extends IGameSelectContract.IGameSelectVIew> extends BasePresenter<V>
        implements IGameSelectContract.IGameSelectPresenter<V> {
    private static final String TAG = "PL_itto.GameSelectPresenter";

    @Inject
    public GameSelectPresenter(IDataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void loadGame(Context context) {
        mDataManager.loadGameList(mGameListCallback, context);
    }

    @Override
    public void saveGameList(@NonNull List<GameItem> itemList) {
        mDataManager.saveGameList(itemList, mSaveGameListCallback);
    }


    IActionCallback<List<GameItem>> mGameListCallback = new IActionCallback<List<GameItem>>() {
        @Override
        public void onSuccess(@Nullable List<GameItem> result) {
            Log.d(TAG, "load Game List onSuccess: " + result);
            if (mView != null && result != null) {
                mView.updateGameList(result);
            }
        }

        @Override
        public void onFailed(@Nullable List<GameItem> result) {
            Log.e(TAG, "loadGameListFailed: ");
        }
    };

    IActionCallback mSaveGameListCallback = new IActionCallback() {
        @Override
        public void onSuccess(@Nullable Object result) {
            Log.d(TAG, "save game list successfully");
        }

        @Override
        public void onFailed(@Nullable Object result) {
            Log.d(TAG, "save game list failed");
        }
    };
}

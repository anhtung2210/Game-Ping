package pl.itto.gameping.data;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import pl.itto.gameping.base.IActionCallback;
import pl.itto.gameping.data.local.ILocalDataHelper;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.di.ApplicationContext;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class DataManager implements IDataManager {
    private static final String TAG = "PL_itto.DataManager";
    ILocalDataHelper mILocalDataHelper;
    @ApplicationContext
    Context mContext;

    @Inject
    public DataManager(@ApplicationContext Context context, ILocalDataHelper localDataHelper) {
        mContext = context;
        mILocalDataHelper = localDataHelper;
    }

    @Override
    public void loadDefaultGame(IActionCallback callback, Context context) {
        mILocalDataHelper.loadDefaultGame(callback, context);
    }

    @Override
    public void loadGameList(IActionCallback callback, Context context) {
        mILocalDataHelper.loadGameList(callback, context);
    }

    @Override
    public String getGamePath() {
        return mILocalDataHelper.getGamePath();
    }

    @Override
    public void saveGameList(@NonNull List<GameItem> gameList, IActionCallback callback) {
        mILocalDataHelper.saveGameList(gameList, callback);
    }
}

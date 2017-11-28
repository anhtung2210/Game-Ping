package pl.itto.gameping.data.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.itto.gameping.base.IActionCallback;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.di.ApplicationContext;
import pl.itto.gameping.utils.AppUtils;

/**
 * Created by PL_itto on 11/21/2017.
 */
@Singleton
public class LocalDataHelper implements ILocalDataHelper {
    private static final String TAG = "PL_itto.LocalDataHelper";
    private static final String GAME_SELECT_NAME = "game_select.json";
    @ApplicationContext
    Context mContext;
    private static LocalDataHelper sLocalDataHelper;
    private final String mGamePath;

    @Inject
    public LocalDataHelper(@ApplicationContext Context context) {
        mContext = context;
        mGamePath = mContext.getFilesDir().getAbsolutePath() + "/" + GAME_SELECT_NAME;
        Log.i(TAG, "GamePath: " + mGamePath);
        sLocalDataHelper = this;
    }


    public static LocalDataHelper getInstance(@ApplicationContext Context context) {
        if (sLocalDataHelper == null) {
            sLocalDataHelper = new LocalDataHelper(context);
        }
        return sLocalDataHelper;
    }

    /**
     * Load GameList From default Json in Assets folder
     *
     * @param callback
     * @param context
     */
    @Override
    public void loadDefaultGame(IActionCallback callback, Context context) {
        List<GameItem> list = AppUtils.loadDefaultGame(context);
        if (callback != null)
            if (list != null) {
                callback.onSuccess(list);
            } else {
                callback.onFailed(null);
            }

    }

    /**
     * load Game List From json file which created in App's FileDir
     *
     * @param callback
     * @param context
     */
    @Override
    public void loadGameList(IActionCallback callback, Context context) {
        Log.d(TAG, "loadGameList: " + mGamePath);
        File file = new File(mGamePath);
        if (!file.exists()) {
            Log.d(TAG, "game list file doesn't exist, load from default workspace!");
            // if gameList not exist, load from default fame list
            loadDefaultGame(callback, context);
        } else {
            List<GameItem> result = AppUtils.loadGamePath(context, mGamePath);
            if (result == null || result.size() == 0) {
                loadDefaultGame(callback, context);
            } else {
                if (callback != null)
                    callback.onSuccess(result);
            }
        }
    }

    @Override
    public String getGamePath() {
        return mGamePath;
    }

    @Override
    public void saveGameList(@NonNull List<GameItem> gameList, IActionCallback callback) {
        if (mGamePath != null) {
            boolean done = AppUtils.saveGameList(mGamePath, gameList);
            if (done)
                callback.onSuccess(null);
            else
                callback.onFailed(null);
        }
    }


}

package pl.itto.gameping.data.local;

import android.content.Context;

import javax.inject.Inject;

import pl.itto.gameping.di.ApplicationContext;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class LocalDataHelper implements ILocalDataHelper {
    @ApplicationContext
    Context mContext;
    private static LocalDataHelper sLocalDataHelper;

    @Inject
    public LocalDataHelper(@ApplicationContext Context context) {
        mContext = context;
        sLocalDataHelper = this;
    }

    public static LocalDataHelper getInstance(@ApplicationContext Context context) {
        if (sLocalDataHelper == null) {
            sLocalDataHelper = new LocalDataHelper(context);
        }
        return sLocalDataHelper;
    }
}

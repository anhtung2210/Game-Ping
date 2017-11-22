package pl.itto.gameping.data;

import android.content.Context;

import javax.inject.Inject;

import pl.itto.gameping.data.local.ILocalDataHelper;
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
}

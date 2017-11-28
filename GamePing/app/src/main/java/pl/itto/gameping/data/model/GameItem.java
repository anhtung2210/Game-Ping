package pl.itto.gameping.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class GameItem implements Serializable {
    String mTitle = "";
    List<ServerItem> mServerList;
    boolean mDefault;
    int mDefaultId = -1;

    public int getDefaultId() {
        return mDefaultId;
    }

    public void setDefaultId(int defaultId) {
        mDefaultId = defaultId;
    }

    public boolean isDefault() {
        return mDefault;
    }

    public void setDefault(boolean aDefault) {
        mDefault = aDefault;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<ServerItem> getServerList() {
        return mServerList;
    }

    public void setServerList(List<ServerItem> serverList) {
        mServerList = serverList;
    }

    @Override
    public String toString() {
        String res = "";
        res += "title: " + mTitle + " ";
        if (isDefault()) {
            res += "default: " + true + " defaultId: " + getDefaultId() + " ";
        } else {
            res += "default: " + false;
        }
        return res;
    }

    public void setCustomHost(@NonNull String title, @NonNull String address) {
        mServerList = new ArrayList<>();
        mServerList.add(new ServerItem(title, new String[]{address}));
        mTitle = title;
        setDefault(false);
    }
}

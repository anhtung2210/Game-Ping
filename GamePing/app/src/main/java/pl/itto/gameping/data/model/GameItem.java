package pl.itto.gameping.data.model;

import android.support.annotation.StringRes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class GameItem implements Serializable{
    String mTitle;
    List<ServerItem> mServerList;
    boolean mDefault;
    @StringRes
    int mIconRes;

    public int getIconRes() {
        return mIconRes;
    }

    public void setIconRes(int iconRes) {
        mIconRes = iconRes;
    }

    public boolean isDefault() {
        return mDefault;
    }

    public void setDefault(boolean aDefault) {
        mDefault = aDefault;
    }

    public GameItem(String title) {
        mTitle = title;
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
}

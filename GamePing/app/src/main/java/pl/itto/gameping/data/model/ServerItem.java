package pl.itto.gameping.data.model;

import java.io.Serializable;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class ServerItem implements Serializable{
    String mTitle;
    String[] mHosts;

    public ServerItem(String title, String[] hosts) {
        mTitle = title;
        mHosts = hosts;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String[] getHosts() {
        return mHosts;
    }

    public void setHosts(String[] hosts) {
        mHosts = hosts;
    }
}

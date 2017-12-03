package pl.itto.gameping.data.model;

import java.io.Serializable;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class ServerItem implements Serializable{
    String mTitle;
    String[] mHosts;
    String mPing;



    String mPacketLoss;



    Boolean isChecked;
    public ServerItem(){

    }
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


    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getmPing() {
        return mPing;
    }

    public void setmPing(String mPing) {
        this.mPing = mPing;
    }

    public String getmPacketLoss() {
        return mPacketLoss;
    }

    public void setmPacketLoss(String mPacketLoss) {
        this.mPacketLoss = mPacketLoss;
    }

}

package pl.itto.gameping.ui.areaselect;


import android.os.Handler;

import java.util.List;

import pl.itto.gameping.base.IBasePresenter;
import pl.itto.gameping.base.IBaseView;
import pl.itto.gameping.data.model.ServerItem;

/**
 * Created by PL_itto on 11/22/2017.
 */

public interface IAreaPingContract {
    interface IAreaPingView extends IBaseView {

        public void updatePingItem(int i, String value);
        public void updatePacketLossItem(int i , String value);

        public void pingServer(int i, List<ServerItem> mlist);

    }

    interface IAreaPingPresenter<V extends IAreaPingView> extends IBasePresenter<V> {

        void  pingToServer(String s, int n,List<ServerItem> mlist);



    }
}

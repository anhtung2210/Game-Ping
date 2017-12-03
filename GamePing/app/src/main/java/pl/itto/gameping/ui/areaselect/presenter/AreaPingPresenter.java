package pl.itto.gameping.ui.areaselect.presenter;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.itto.gameping.base.BasePresenter;
import pl.itto.gameping.data.IDataManager;
import pl.itto.gameping.data.model.ServerItem;
import pl.itto.gameping.ui.areaselect.IAreaPingContract;
import pl.itto.gameping.ui.areaselect.view.AreaPingActivity;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class AreaPingPresenter<V extends IAreaPingContract.IAreaPingView> extends BasePresenter<V>
        implements IAreaPingContract.IAreaPingPresenter<V> {

    private static final String TAG = "PL_itto.AreaPingPresenter";

    @Inject
    public AreaPingPresenter(IDataManager dataManager) {
        super(dataManager);
    }


    void ping(final List<ServerItem> list, int pos) {

        if (list.get(pos).getChecked()== true) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process mIpAddrProcess = runtime.exec("/system/bin/ping " + " -c 2 " + list.get(pos).getHosts()[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(mIpAddrProcess.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Log.d(TAG, "line : " + line);

                        if (line.contains("transmitted")) {
                            Log.d("tung.lt", "transmitted" + line);
                            String a = line.substring(0, line.indexOf("%"));
                            final String b = a.substring(a.lastIndexOf(" "));
                            Log.d("tung.lt", "packetloss" + b + "%");

                            getMvpView().updatePacketLossItem(pos, b);

                        } else if (line.contains("rtt")) {
                            Log.d("tung.lt", " line rtt : " + line);
                            String a = line.substring(0, line.lastIndexOf("/"));
                            String b = a.substring(0, a.lastIndexOf("/"));
                            final String c = b.substring(b.lastIndexOf("/") + 1, b.length());
                            Log.d("tung.lt", "c" + c);
                            final String d = c.substring(0,c.indexOf("."));
                            getMvpView().updatePingItem(pos, d);


                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "mIpAddrProcess" + e.toString());
                }


                Log.d(TAG, "size presenter after" + list.size());

                //getMvpView().pingServer(n + 1,list);
                if (pos < list.size() - 1) {
                    ping(list, pos + 1);
                }

                int mExitValue = mIpAddrProcess.waitFor();
                Log.i(TAG, " mExitValue " + mExitValue);
            } catch (InterruptedException ignore) {
                ignore.printStackTrace();
                Log.i(TAG, " Exception:" + ignore);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, " Exception:" + e);
            }

        }else {
            if (pos <list.size() -1 )
            ping(list, pos + 1);
        }


    }

    @Override
    public void pingToServer(final String s, final int n, final List<ServerItem> mlist) {
        Log.d(TAG, "size presenter before" + mlist.size());
        final List<ServerItem> list = new ArrayList<>();
        list.addAll(mlist);
        Log.i(TAG, "executeCommand");

        new Thread(new Runnable() {
            @Override
            public void run() {
//                Runtime runtime = Runtime.getRuntime();
//                try {
//                    Process mIpAddrProcess = runtime.exec("/system/bin/ping " + " -c 5 " + s);
//                    InputStreamReader inputStreamReader = new InputStreamReader(mIpAddrProcess.getInputStream());
//                    BufferedReader reader = new BufferedReader(inputStreamReader);
//                    try {
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            Log.d(TAG, "line : " + line);
//
//                            if (line.contains("transmitted")) {
//                                Log.d("tung.lt", "transmitted" + line);
//                                String a = line.substring(0, line.indexOf("%"));
//                                final String b = a.substring(a.lastIndexOf(" "));
//                                Log.d("tung.lt", "packetloss" + b + "%");
//
//                                getMvpView().updatePacketLossItem(n, b);
//
//
//                            } else if (line.contains("rtt")) {
//                                Log.d("tung.lt", " line rtt : " + line);
//                                String a = line.substring(0, line.lastIndexOf("/"));
//                                String b = a.substring(0, a.lastIndexOf("/"));
//                                final String c = b.substring(b.lastIndexOf("/") + 1, b.length());
//                                Log.d("tung.lt", "c" + c);
//
//                                getMvpView().updatePingItem(n, c);
//                            }
//
//
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Log.e(TAG, "mIpAddrProcess" + e.toString());
//                    }
//
//
//                    Log.d(TAG, "size presenter after" + list.size());
//
//                    getMvpView().pingServer(n + 1, list);
//
//
//                    int mExitValue = mIpAddrProcess.waitFor();
//                    Log.i(TAG, " mExitValue " + mExitValue);
//                } catch (InterruptedException ignore) {
//                    ignore.printStackTrace();
//                    Log.i(TAG, " Exception:" + ignore);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.i(TAG, " Exception:" + e);
//                }
                ping(list,0);
            }
        }).start();

    }
}

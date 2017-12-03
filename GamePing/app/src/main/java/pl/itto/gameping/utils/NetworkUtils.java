package pl.itto.gameping.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.itto.gameping.base.IActionCallback;

/**
 * Created by PL_itto on 11/21/2017.
 */

public final class NetworkUtils {
    private static final String TAG = "PL_itto.NetworkUtils";

    private NetworkUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void pingServer(final String address, final IActionCallback<String> callback) {
        Log.d(TAG, "pingServer: " + address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process process = runtime.exec("/system/bin/ping " + address);
                    InputStreamReader isr = new InputStreamReader(process.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    String result;
                    while ((result = reader.readLine()) != null) {
                        if (callback != null) {
                            callback.onSuccess(result);
                        } else {
                            if (process != null)
                                process.destroy();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (callback != null)
                        callback.onFailed(null);
                }
            }
        }).start();
    }

}

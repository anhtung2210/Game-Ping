package pl.itto.gameping.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pl.itto.gameping.R;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.data.model.ServerItem;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class AppUtils {
    private static final String TAG = "PL_itto.AppUtils";

    public static void generateDefaultJson() {
        Log.i(TAG, "generateDefaultJson: ");
        List<GameItem> list = new ArrayList<>();
        String titles[] = {"PUBG", "League of Legend", "DOTA2", "Fornite", "OverWatch", "CS:GO"};
        int res[] = {R.drawable.img_pubg, R.drawable.img_lol, R.drawable.img_dota, R.drawable.img_fornite, R.drawable.img_overwatch, R.drawable.img_csgo};
        for (int i = 0; i < 6; i++) {
            GameItem item = new GameItem(titles[i]);
            item.setIconRes(res[i]);
            item.setDefault(true);
            List<ServerItem> serverItems = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                String[] host = {"google.com", "facebook.com"};
                ServerItem serverItem = new ServerItem("Asia", host);
                serverItems.add(serverItem);
            }
            item.setServerList(serverItems);
            list.add(item);
        }
        Gson gson = new Gson();
        Log.i(TAG, gson.toJson(list));
    }


    /**
     * Load default Game Item from Assest
     * @param context
     * @return
     */
    public static List<GameItem> loadDefaultGame(Context context) {
        Gson gson = new Gson();
        List<GameItem> items = null;
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open("default_game.json"));
            items = gson.fromJson(isr, new TypeToken<List<GameItem>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}

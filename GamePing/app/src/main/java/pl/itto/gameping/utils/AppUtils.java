package pl.itto.gameping.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.data.model.ServerItem;
import pl.itto.gameping.di.ApplicationContext;
/**
 * Created by PL_itto on 11/21/2017.
 */

public class AppUtils {
    private static final String TAG = "PL_itto.AppUtils";

    public static void generateDefaultJson() {
        Log.i(TAG, "generateDefaultJson: ");
        List<GameItem> list = new ArrayList<>();
        String titles[] = {"PUBG", "League of Legend", "DOTA2", "Fornite", "OverWatch", "CS:GO"};
        for (int i = 0; i < AppConstants.GameSelect.DEFAULT_GAME_COUNT; i++) {
            GameItem item = new GameItem();
            item.setTitle(titles[i]);
            item.setDefaultId(i);
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
     *
     * @param context
     * @return
     */
    public static List<GameItem> loadDefaultGame(@ApplicationContext Context context) {
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

    /**
     * Load default Game Item from Assest
     *
     * @param context
     * @return
     */
    public static List<GameItem> loadGamePath(Context context, @NonNull String path) {
        Gson gson = new Gson();
        List<GameItem> items = null;
        try {
            FileReader fileReader = new FileReader(path);
            items = gson.fromJson(fileReader, new TypeToken<List<GameItem>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }


    public static boolean saveGameList(@NonNull String savePath, @NonNull List<GameItem> item) {
        Log.d(TAG, "saveGameList: " + savePath + " " + item.size());
        Gson gson = new GsonBuilder().create();
        try {
            FileWriter writer = new FileWriter(savePath);
            String json = gson.toJson(item);
            writer.write(json);
            writer.close();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "saveGameLe.printStackTrace();istFailed: \n" + e.toString());
            return false;
        }
    }
}

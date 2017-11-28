package pl.itto.gameping.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import pl.itto.gameping.base.IActionCallback;
import pl.itto.gameping.data.model.GameItem;

/**
 * Created by PL_itto on 11/21/2017.
 */

public interface ILocalDataHelper {
    void loadDefaultGame(IActionCallback callback, Context context);

    void loadGameList(IActionCallback callback, Context context);

    String getGamePath();

    void saveGameList(@NonNull List<GameItem> gameList, IActionCallback callback);
}

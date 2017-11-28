package pl.itto.gameping.ui.gameselect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import pl.itto.gameping.base.IBasePresenter;
import pl.itto.gameping.base.IBaseView;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.di.ApplicationContext;

/**
 * Created by PL_itto on 11/22/2017.
 */

public interface IGameSelectContract {
    interface IGameSelectVIew extends IBaseView {
        void updateGameList(@NonNull List<GameItem> data);

        void openNewServerDialog();

        void openEditServerDialog(int pos, @NonNull String title, @NonNull String address);

        void addCustom(@NonNull GameItem item);

        void editCustom(int pos, @NonNull GameItem item);

        void openAreaSelect(@NonNull GameItem item);

        void openServerPing(@Nullable String title, @NonNull String address);

        void openOption();

    }

    interface IGameSelectPresenter<V extends IGameSelectVIew> extends IBasePresenter<V> {
        void loadGame(@ApplicationContext Context context);

        void saveGameList(@NonNull List<GameItem> itemList);
    }
}

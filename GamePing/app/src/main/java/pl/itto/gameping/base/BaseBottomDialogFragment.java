package pl.itto.gameping.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.widget.Toast;

import pl.itto.gameping.MainApp;
import pl.itto.gameping.di.component.BottomDialogComponent;
import pl.itto.gameping.di.module.BottomDialogModule;
import pl.itto.gameping.di.component.DaggerBottomDialogComponent;

/**
 * Created by PL_itto on 10/5/2017.
 */

public class BaseBottomDialogFragment extends BottomSheetDialogFragment implements IBaseView{
    BottomDialogComponent mComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public BottomDialogComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerBottomDialogComponent.builder()
                    .bottomDialogModule(new BottomDialogModule(this))
                    .applicationComponent(((MainApp) getActivity().getApplication()).getComponent())
                    .build();


        }
        return mComponent;
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(getContext(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public void showMessage(@NonNull String msg) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }
}

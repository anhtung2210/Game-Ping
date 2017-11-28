package pl.itto.gameping.ui.gameselect.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import pl.itto.gameping.base.BaseActivity;

public class GameSelectActivity extends BaseActivity {
    private static final String TAG = "PL_itto.GameSelectActivity";

    @Override
    public Fragment getFragment() {
        return new GameSelectFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppUtils.generateDefaultJson();
        requestTransparentStatusBar();

    }


}

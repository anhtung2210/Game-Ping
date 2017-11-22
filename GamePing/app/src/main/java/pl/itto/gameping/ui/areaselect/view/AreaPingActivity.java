package pl.itto.gameping.ui.areaselect.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.itto.gameping.base.BaseActivity;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.utils.AppConstants;

public class AreaPingActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return AreaPingFragment.newInstance((GameItem) getIntent().getSerializableExtra(AppConstants.AreaSelect.EXTRA_GAME_ITEM));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

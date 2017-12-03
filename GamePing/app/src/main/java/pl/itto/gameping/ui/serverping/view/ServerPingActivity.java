package pl.itto.gameping.ui.serverping.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.itto.gameping.base.BaseActivity;
import pl.itto.gameping.utils.AppConstants;

public class ServerPingActivity extends BaseActivity {

    @NonNull
    @Override
    public Fragment getFragment() {
        return ServerPingFragment.newInstance(getIntent().getStringExtra(AppConstants.ServerPing.EXTRA_TITLE),getIntent().getStringExtra(AppConstants.ServerPing.EXTRA_ADDRESS));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

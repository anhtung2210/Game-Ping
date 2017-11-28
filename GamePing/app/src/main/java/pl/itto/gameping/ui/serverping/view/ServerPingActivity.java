package pl.itto.gameping.ui.serverping.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.itto.gameping.base.BaseActivity;

public class ServerPingActivity extends BaseActivity {

    @NonNull
    @Override
    public Fragment getFragment() {
        return new ServerPingFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

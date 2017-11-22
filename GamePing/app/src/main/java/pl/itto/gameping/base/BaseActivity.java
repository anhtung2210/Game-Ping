package pl.itto.gameping.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.Unbinder;
import pl.itto.gameping.MainApp;
import pl.itto.gameping.R;
import pl.itto.gameping.di.component.ActivityComponent;
import pl.itto.gameping.di.component.DaggerActivityComponent;
import pl.itto.gameping.di.module.ActivityModule;
import pl.itto.gameping.utils.NetworkUtils;

/**
 * Created by PL_itto on 11/21/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private static final String TAG = "PL_itto.BaseActivity";

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    public int getContentRes() {
        return R.layout.activity_main;
    }

    public abstract Fragment getFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentRes());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = getFragment();
            fm.beginTransaction().add(R.id.frame_container, fragment).commit();
        }
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@NonNull String msg) {
        if (msg != null)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(((MainApp) getApplication()).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}

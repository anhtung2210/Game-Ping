package pl.itto.gameping.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import butterknife.Unbinder;
import pl.itto.gameping.MainApp;
import pl.itto.gameping.di.component.DaggerFragmentComponent;
import pl.itto.gameping.di.component.FragmentComponent;
import pl.itto.gameping.di.module.FragmentModule;
import pl.itto.gameping.utils.NetworkUtils;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class BaseFragment extends Fragment implements IBaseView {
    private BaseActivity mActivity;
    private Unbinder mUnBinder;
    private FragmentComponent mComponent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void showMessage(int resId) {
        if (mActivity != null)
            mActivity.showMessage(resId);
    }

    @Override
    public void showMessage(String msg) {
        if (mActivity != null)
            mActivity.showMessage(msg);
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getContext().getApplicationContext());
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

    public FragmentComponent getFragmentComponent() {
        if (mComponent == null) {
            mComponent = DaggerFragmentComponent.builder().fragmentModule(new FragmentModule(this))
                    .applicationComponent(((MainApp) getActivity().getApplication()).getComponent())
                    .build();
        }
        return mComponent;
    }

}

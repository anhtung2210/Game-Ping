package pl.itto.gameping.ui.areaselect.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import pl.itto.gameping.R;
import pl.itto.gameping.base.BaseFragment;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.ui.areaselect.IAreaPingContract.IAreaPingPresenter;
import pl.itto.gameping.ui.areaselect.IAreaPingContract.IAreaPingView;
import pl.itto.gameping.utils.AppConstants;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class AreaPingFragment extends BaseFragment implements IAreaPingView {
    private static final String TAG = "PL_itto.AreaPingFragment";
    @Inject
    IAreaPingPresenter<IAreaPingView> mPresenter;

    public static AreaPingFragment newInstance(GameItem item) {
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.AreaSelect.EXTRA_GAME_ITEM, item);
        AreaPingFragment fragment = new AreaPingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_area_ping, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}

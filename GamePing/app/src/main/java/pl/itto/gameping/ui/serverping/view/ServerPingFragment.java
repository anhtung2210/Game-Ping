package pl.itto.gameping.ui.serverping.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.itto.gameping.R;
import pl.itto.gameping.base.BaseFragment;
import pl.itto.gameping.ui.serverping.IServerPingContract;

/**
 * Created by PL_itto on 11/28/2017.
 */

public class ServerPingFragment extends BaseFragment implements IServerPingContract.IServerPingView {
    @BindView(R.id.line_chart)
    LineChart mPingChart;
    @BindView(R.id.ping_detail_edit)
    EditText mPingEdit;
    @BindView(R.id.ping_clear_btn)
    Button mClearPing;

    @Inject
    IServerPingContract.IServerPingPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_server_ping, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    private void setup() {
        /** Setup for Chart View */
        // No Description Text
        mPingChart.getDescription().setEnabled(false);
        // disable touch gesture
        mPingChart.setTouchEnabled(false);
        // enable dragging and scaling
        mPingChart.setDragEnabled(true);
        mPingChart.setScaleEnabled(true);
        // disable PinchZoom
        mPingChart.setPinchZoom(false);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}

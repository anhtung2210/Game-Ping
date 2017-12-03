package pl.itto.gameping.ui.serverping.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.itto.gameping.R;
import pl.itto.gameping.base.BaseFragment;
import pl.itto.gameping.base.IActionCallback;
import pl.itto.gameping.ui.serverping.IServerPingContract;
import pl.itto.gameping.ui.serverping.IServerPingContract.IServerPingPresenter;
import pl.itto.gameping.utils.AppConstants;
import pl.itto.gameping.utils.AppUtils;
import pl.itto.gameping.utils.NetworkUtils;

/**
 * Created by PL_itto on 11/28/2017.
 */

public class ServerPingFragment extends BaseFragment implements IServerPingContract.IServerPingView {
    private static final String TAG = "PL_itto." + ServerPingFragment.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.line_chart)
    LineChart mPingChart;
    @BindView(R.id.ping_detail_edit)
    EditText mPingEdit;
    @BindView(R.id.ping_clear_btn)
    Button mClearPing;
    int i = 0;
    private String mTitle = null;
    private String mAddress = null;
    private Process mPingProcess = null;
    private IActionCallback<String> mPingCallback = null;
    @Inject
    IServerPingPresenter<IServerPingContract.IServerPingView> mPresenter;

    public static ServerPingFragment newInstance(String title, String address) {
        ServerPingFragment fragment = new ServerPingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ServerPing.EXTRA_TITLE, title);
        bundle.putString(AppConstants.ServerPing.EXTRA_ADDRESS, address);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
        mTitle = getArguments().getString(AppConstants.ServerPing.EXTRA_TITLE, null);
        mAddress = getArguments().getString(AppConstants.ServerPing.EXTRA_ADDRESS, null);
        i = 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_server_ping, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        setupActionBar();
        setup();
        return view;
    }

    private void setupActionBar() {
        getParentActivity().setSupportActionBar(mToolbar);
        getParentActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getParentActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (mTitle != null) {
            getParentActivity().getSupportActionBar().setTitle(mTitle);
        }
    }

    private void setup() {
        /** Setup for Chart View */
        // No Description Text
        mPingChart.getDescription().setEnabled(false);
        // disable touch gesture
        mPingChart.setTouchEnabled(true);
        // enable dragging and scaling
        mPingChart.setDragEnabled(true);
        mPingChart.setScaleEnabled(true);
        mPingChart.setDrawGridBackground(false);
        // disable PinchZoom
        mPingChart.setPinchZoom(true);
        mPingChart.setVisibleXRangeMaximum(10);
        mPingChart.getAxisRight().setEnabled(false);
        mPingChart.getLegend().setEnabled(true);
        mPingChart.getLegend().setTextColor(getResources().getColor(R.color.colorWhite));

        XAxis xAxis = mPingChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);
        xAxis.setTextColor(getResources().getColor(R.color.ping_detail_text_color1));

        YAxis yAxis = mPingChart.getAxisLeft();
        yAxis.setAxisMaximum(400f);
        yAxis.setAxisMinimum(0f);
        yAxis.setTextColor(getResources().getColor(R.color.ping_detail_text_color1));
        mPingChart.invalidate();

        /** Setup for Ping */
        mPingCallback = new IActionCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Activity activity = getActivity();
                if (activity != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addPingValue(result);
                        }
                    });
                }
            }

            @Override
            public void onFailed(String result) {

            }
        };
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pingServer();
    }

    void pingServer() {
        if (mAddress != null)
            NetworkUtils.pingServer(mAddress, mPingCallback);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        mPingCallback = null;
        super.onDestroy();
    }

    @Override
    public void addPingValue(String value) {
        i++;
        Float ping = AppUtils.getPingValue(value);
        if (ping == null)
            return;
        LineDataSet set;
        if (mPingChart.getData() != null && mPingChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) mPingChart.getData().getDataSetByIndex(0);
            set.addEntry(new Entry(i, ping));
            mPingChart.getData().notifyDataChanged();
            mPingChart.notifyDataSetChanged();
        } else {
            List<Entry> list = new ArrayList<>();
            list.add(new Entry(i, ping));
            set = new LineDataSet(list, getString(R.string.ping_legend));
            set.setLineWidth(5f);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set); // add the datasets
            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            data.setValueTextColor(getResources().getColor(R.color.colorWhite));
            // set data
            mPingChart.setData(data);
            mPingChart.notifyDataSetChanged();
        }
        mPingChart.setVisibleXRangeMaximum(10);
        mPingChart.setVisibleXRangeMinimum(10);
        mPingChart.moveViewToX(mPingChart.getData().getEntryCount());
        mPingEdit.append(value + "\n\n");
        mPingEdit.setSelection(mPingEdit.getText().length());
    }

    @OnClick(R.id.ping_clear_btn)
    void clearPing() {
        mPingChart.getData().clearValues();
        mPingChart.notifyDataSetChanged();
        mPingEdit.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getParentActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

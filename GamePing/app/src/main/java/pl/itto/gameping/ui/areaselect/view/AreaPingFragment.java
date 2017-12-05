package pl.itto.gameping.ui.areaselect.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.itto.gameping.R;
import pl.itto.gameping.base.BaseFragment;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.data.model.ServerItem;
import pl.itto.gameping.ui.areaselect.IAreaPingContract.IAreaPingPresenter;
import pl.itto.gameping.ui.areaselect.IAreaPingContract.IAreaPingView;
import pl.itto.gameping.utils.AppConstants;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class AreaPingFragment extends BaseFragment implements IAreaPingView {
    private static final String TAG = "PL_itto." + AreaPingFragment.class.getSimpleName();

    private static final Integer GAME_PUBG = 1;
    private static final Integer GAME_DOTA2 = 2;
    private static final Integer GAME_LOL = 3;
    private static final Integer GAME_OW = 4;
    private static final Integer GAME_CSGO = 5;
    private static final Integer GAME_FN = 6;

    @Inject
    IAreaPingPresenter<IAreaPingView> mPresenter;

    @BindView(R.id.area_index_imageview)
    ImageView index_image;

    @BindView(R.id.server_recucycler)
    RecyclerView mServerList;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


//
//    @BindView(R.id.custom_fab)
//    CustomFloatingActionButton mFloatingButton;


    @BindView(R.id.button_ping)
    Button pingBtn;
    private ServerAdapter mServerAdapter;
    private GameItem mGameItem;
    final List<ServerItem> mServerItemList = new ArrayList<>();
    public Handler mHandler;

    private int listSize;


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
        setHasOptionsMenu(true);
        mGameItem = (GameItem) getArguments().getSerializable(AppConstants.AreaSelect.EXTRA_GAME_ITEM);
        mPresenter.onAttach(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getParentActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_area_ping, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        setUp();

        mHandler = new Handler();
        return view;
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }


    public void setUp() {

        setUpActionBar();


        ServerItem item = new ServerItem();
        item.setTitle("Asia");
        item.setChecked(false);
        item.setHosts(new String[]{"google.com"});
        mServerItemList.add(item);

        item = new ServerItem();
        item.setTitle("Aus");
        item.setChecked(true);
        item.setHosts(new String[]{"facebook.com"});
        mServerItemList.add(item);

        item = new ServerItem();
        item.setTitle("Aus");
        item.setChecked(false);
        item.setHosts(new String[]{"youtube.com"});
        mServerItemList.add(item);


        item = new ServerItem();
        item.setTitle("VN");
        item.setChecked(false);
        item.setHosts(new String[]{"youtube.com"});
        mServerItemList.add(item);

        listSize = mServerItemList.size();

        mServerList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mServerAdapter = new ServerAdapter(mServerItemList);
        mServerList.setAdapter(mServerAdapter);

//        mFloatingButton.setFabColor(Color.BLUE);
//        mFloatingButton.setOnFabClickListener(new OnFabClickListener() {
//            @Override
//            public void onFabClick(View v) {
//                //Your action here...
//                Log.d("tung.lt", "click Ping " + " size : " + listSize);
//                // AppUtils.pingToServer(getContext(), "google.com");
//
//
//                pingServer(0, mServerItemList);
//
//
//            }
//        });


        pingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pingServer(0, mServerItemList);
            }
        });

    }

    private void setUpActionBar() {
        if(mGameItem!=null){
            mToolbar.setTitle(mGameItem.getTitle());
        }else{
            Log.e(TAG, "data error" );
        }
        getParentActivity().setSupportActionBar(mToolbar);
        getParentActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getParentActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    synchronized public void updatePingItem(final int i, final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "updatePingItem: " + mServerItemList.size());

                mServerItemList.get(i).setmPing(value);
                mServerAdapter.replacedata(mServerItemList);
                Log.i(TAG, "After updatePingItem: " + mServerItemList.size());
            }
        });

    }

    @Override
    synchronized public void updatePacketLossItem(final int i, final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mServerItemList.get(i).setmPacketLoss(value);
                mServerAdapter.replacedata(mServerItemList);
            }
        });

    }

    @Override
    public void pingServer(int i, List<ServerItem> mlist) {
        Log.d(TAG, "ping server" + i + "mServerItemList " + mlist.size());


        mPresenter.pingToServer(mlist.get(i).getHosts()[0], i, mlist);

    }


    class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {

        List<ServerItem> mList;

        public static final int TYPE_HEADER = 0;
        public static final int TYPE_AREA = 1;
        public static final int TYPE_FOOTER = 2;

        CheckBox checkHeader;


        public void replacedata(List<ServerItem> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();

        }

        public ServerAdapter(List<ServerItem> list) {

            mList = new ArrayList<>();
            mList.addAll(list);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) return TYPE_HEADER;
            else if (position >= 1 && position <= mList.size() + 1) return TYPE_AREA;
            else if (position > mList.size() + 1) return TYPE_FOOTER;
            else return Integer.parseInt(null);

        }

        @Override
        public ServerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Log.d(TAG, "onCreateViewHolder " + viewType);

            if (viewType == TYPE_AREA) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.ping_server_item, parent, false);
                return new ServerAdapter.ViewHolder(view, viewType);
            } else if (viewType == TYPE_HEADER) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.ping_header_item, parent, false);
                return new ServerAdapter.ViewHolder(view, viewType);

            } else {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.ping_footer_item, parent, false);
                return new ServerAdapter.ViewHolder(view, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ServerAdapter.ViewHolder holder, int position) {

            if (getItemViewType(position) == TYPE_AREA)
                holder.bindItem(position);
            else if (getItemViewType(position) == TYPE_HEADER) {
                holder.bindHeader(position);


            }
        }

        @Override
        public int getItemCount() {
            return mList.size() + 1;
        }


        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.checkBox)
            CheckBox checkBox;
            @BindView(R.id.tv_ping)
            TextView tv_ping;

            @BindView(R.id.tv_packetloss)
            TextView tv_loss;


            public ViewHolder(View itemView, int viewType) {
                super(itemView);
                if (viewType == TYPE_AREA)
                    ButterKnife.bind(this, itemView);
                else if (viewType == TYPE_HEADER) {
                    checkHeader = (CheckBox) itemView.findViewById(R.id.checkBoxHeader);


                }
            }


            void bindItem(int pos) {
                Log.i(TAG, "bindItem: " + pos + " " + getItemViewType());
                if (getItemViewType() == TYPE_AREA)
                    pos -= 1;


                ServerItem item = mServerItemList.get(pos);
                checkBox.setText(item.getTitle());
                checkBox.setChecked(item.getChecked());
                if (item.getmPing() != null) {
                    if (Long.parseLong(item.getmPing()) > 70 && Long.parseLong(item.getmPing()) < 100) {
                        tv_ping.setTextColor(Color.YELLOW);
                    } else if (Long.parseLong(item.getmPing()) <= 70) {
                        tv_ping.setTextColor(Color.GREEN);
                    } else {
                        tv_ping.setTextColor(Color.RED);
                    }

                }
                tv_ping.setText(item.getmPing());
                tv_loss.setText(item.getmPacketLoss());
            }

            void bindHeader(int pos) {
//                checkHeader.setText("Select all");

                if (checkHeader.isChecked() == true) {
                    checkHeader.setText("Uncheck all");
                } else {
                    checkHeader.setText("Select all");
                }


                checkHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (checkHeader.isChecked() == false) {
                            //checkHeader.setText("Uncheck all");
                            Log.d("tung.lt", "Click Header check");
                            for (int i = 0; i < mServerItemList.size(); i++) {
                                mServerItemList.get(i).setChecked(false);
                                notifyDataSetChanged();
                            }
                        } else {
                            // checkHeader.setText("Select all");
                            Log.d("tung.lt", "Click Header uncheck");
                            for (int i = 0; i < mServerItemList.size(); i++) {
                                mServerItemList.get(i).setChecked(true);
                                notifyDataSetChanged();
                            }
                        }


                    }
                });
            }

            @Override
            public void onClick(View view) {

            }


            @OnClick(R.id.checkBox)
            void clickCheckbox() {
                if (mServerItemList.get(getAdapterPosition() - 1).getChecked() == false) {
                    mList.get(getAdapterPosition() - 1).setChecked(true);
                    mServerItemList.get(getAdapterPosition() - 1).setChecked(true);
                } else {
                    mServerItemList.get(getAdapterPosition() - 1).setChecked(false);
                    mList.get(getAdapterPosition() - 1).setChecked(false);
                }
            }
//
//            @OnClick(R.id.checkBoxHeader)
//            void clickCheckHeader(){
//
//
//                Log.d("tung.lt", "Click Header");
//
//            }


        }

    }
}

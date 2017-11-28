package pl.itto.gameping.ui.gameselect.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import pl.itto.gameping.R;
import pl.itto.gameping.base.BaseFragment;
import pl.itto.gameping.base.IActionCallback;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.ui.areaselect.view.AreaPingActivity;
import pl.itto.gameping.ui.gameselect.IGameSelectContract;
import pl.itto.gameping.ui.gameselect.IGameSelectContract.IGameSelectPresenter;
import pl.itto.gameping.ui.gameselect.customserver.view.CustomServerDialogFragment;
import pl.itto.gameping.ui.serverping.view.ServerPingActivity;
import pl.itto.gameping.ui.widget.GriddSpacingItemDecoration;
import pl.itto.gameping.utils.AppConstants;
import pl.itto.gameping.utils.UiUtils;

import static pl.itto.gameping.utils.AppConstants.AreaSelect.EXTRA_GAME_ITEM;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class GameSelectFragment extends BaseFragment implements IGameSelectContract.IGameSelectVIew {
    private static final String TAG = "PL_itto." + GameSelectFragment.class.getSimpleName();
    @BindView(R.id.main_fab_new)
    FloatingActionButton mFabNew;
    @BindView(R.id.game_title)
    TextView mTitle;
    @BindView(R.id.game_list)
    RecyclerView mGameList;
    @BindView(R.id.main_option)
    ImageButton mOptionBtn;
    GameListAdapter mAdapter;
    @Inject
    IGameSelectPresenter<IGameSelectContract.IGameSelectVIew> mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_game_select, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        setup();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        mPresenter.loadGame(getContext());
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    private void setup() {
        Typeface type = Typeface.createFromAsset(getContext().getAssets(), "fonts/Panton-BlackCaps.otf");
        mTitle.setTypeface(type);
        mAdapter = new GameListAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        mGameList.setLayoutManager(layoutManager);
        mGameList.addItemDecoration(new GriddSpacingItemDecoration(2, 30, true, 0));
        mGameList.setAdapter(mAdapter);
    }

    @Override
    public void updateGameList(@NonNull List<GameItem> data) {
        Log.d(TAG, "updateGameList: " + data.size());
        if (mAdapter != null)
            mAdapter.replaceData(data);
    }

    @OnClick(R.id.main_fab_new)
    @Override
    public void openNewServerDialog() {
        CustomServerDialogFragment fragment = CustomServerDialogFragment.newInstance(mCreateCustomCallback, AppConstants.GameSelect.CustomServer.MODE_CREATE);
        fragment.show(getFragmentManager(), null);
    }

    @Override
    public void openEditServerDialog(int pos, @NonNull String title, @NonNull String address) {
        CustomServerDialogFragment fragment = CustomServerDialogFragment.newInstance(new EditCustomCallback(pos), AppConstants.GameSelect.CustomServer.MODE_EDIT, title, address);
        fragment.show(getFragmentManager(), null);
    }

    @Override
    public void addCustom(@NonNull GameItem item) {
        if (mAdapter != null) {
            mAdapter.addItem(item);
            mPresenter.saveGameList(mAdapter.getGameList());
        }
    }

    @Override
    public void editCustom(int pos, @NonNull GameItem item) {
        if (mAdapter != null) {
            mAdapter.editItem(pos, item);
            mPresenter.saveGameList(mAdapter.getGameList());
        }
    }

    @Override
    public void openAreaSelect(@NonNull GameItem item) {
        Intent i = new Intent(getContext(), AreaPingActivity.class);
        i.putExtra(EXTRA_GAME_ITEM, item);
        startActivity(i);
    }

    @Override
    public void openServerPing(@NonNull String title, @NonNull String address) {
        Intent i = new Intent(getContext(), ServerPingActivity.class);
        i.putExtra(AppConstants.ServerPing.EXTRA_TITLE, title);
        i.putExtra(AppConstants.ServerPing.EXTRA_ADDRESS, address);
        startActivity(i);
    }

    @OnClick(R.id.main_option)
    @Override
    public void openOption() {
        PopupMenu popupMenu = new PopupMenu(getContext(), mOptionBtn);
        popupMenu.inflate(R.menu.game_item_option);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        popupMenu.show();
    }

    IActionCallback<GameItem> mCreateCustomCallback = new IActionCallback<GameItem>() {
        @Override
        public void onSuccess(@Nullable GameItem result) {
            if (result != null)
                addCustom(result);
        }

        @Override
        public void onFailed(@Nullable GameItem result) {
            Log.e(TAG, "Create Custom Server Failed!");
        }
    };

    class EditCustomCallback implements IActionCallback<GameItem> {
        int mPos = -1;

        public EditCustomCallback(int pos) {
            mPos = pos;
        }

        @Override
        public void onSuccess(@Nullable GameItem result) {
            if (result != null && mPos != -1) {
                editCustom(mPos, result);
            }
        }

        @Override
        public void onFailed(@Nullable GameItem result) {
            Log.e(TAG, "Edit Custom Server Failed!");
        }
    }

    class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.Holder> {
        List<GameItem> mGameItems;

        public GameListAdapter() {
            mGameItems = new ArrayList<>();
        }

        public void replaceData(@NonNull List<GameItem> list) {
            mGameItems = list;
            notifyDataSetChanged();
        }

        public void addItem(GameItem item) {
            if (mGameItems != null) {
                mGameItems.add(item);
                notifyDataSetChanged();
            }
        }

        public List<GameItem> getGameList() {
            return mGameItems;
        }

        public void editItem(int pos, GameItem item) {
            if (pos < mGameItems.size()) {
                mGameItems.set(pos, item);
                notifyDataSetChanged();
            }
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.main_game_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            GameItem item = mGameItems.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return mGameItems.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.icon)
            ImageButton mIcon;
            @BindView(R.id.title)
            TextView mTitle;
            @BindView(R.id.option)
            ImageButton mOption;
            @BindView(R.id.game_item_root)
            RelativeLayout mParentLayout;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIcon.setClipToOutline(true);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIcon.setClipToOutline(true);
                }
            }

            public void bindItem(GameItem item) {
                if (item.isDefault()) {
                    mOption.setVisibility(View.GONE);
                    int id = item.getDefaultId();
                    if (id < AppConstants.GameSelect.DEFAULT_GAME_COUNT) {
//                    Glide.with(getContext()).load(AppConstants.GameSelect.DEFAULT_GAME_THUMBS[id]).into(mIcon);
                        UiUtils.loadImageRes(getContext(), AppConstants.GameSelect.DEFAULT_GAME_THUMBS[id], mIcon, true);
                    }
                } else {
                    mOption.setVisibility(View.VISIBLE);
                    UiUtils.loadImageRes(getContext(), R.mipmap.ic_launcher, mIcon, true);
                }
                mTitle.setText(item.getTitle());
            }

            @OnClick(R.id.option)
            void openItemOption() {
                PopupMenu popupMenu = new PopupMenu(getContext(), mOption);
                popupMenu.inflate(R.menu.game_item_option);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_option:
                                GameItem gameItem = mGameItems.get(getAdapterPosition());
                                String title = gameItem.getTitle();
                                String address = gameItem.getServerList().get(0).getHosts()[0];
                                openEditServerDialog(getAdapterPosition(), title, address);
                                break;
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }

            @OnClick(R.id.game_item_root)
            void onItemClick() {
                Log.d(TAG, "onGameItemClick: " + getAdapterPosition());
                GameItem item = mGameItems.get(getAdapterPosition());
                if (item != null) {
                    Log.d(TAG, item.toString());
                    if (item.isDefault()) {
//                        openAreaSelect(item);
                    } else {
                        String title = item.getTitle();
                        String address = item.getServerList().get(0).getHosts()[0];
                        openServerPing(title, address);
                    }
                }
            }

        }
    }


}

package pl.itto.gameping.ui.gameselect.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.itto.gameping.R;
import pl.itto.gameping.base.BaseFragment;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.ui.gameselect.IGameSelectContract;
import pl.itto.gameping.ui.gameselect.IGameSelectContract.IGameSelectPresenter;
import pl.itto.gameping.utils.AppUtils;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class GameSelectFragment extends BaseFragment implements IGameSelectContract.IGameSelectVIew {
    private static final String TAG = "PL_itto.GameSelectFragment";

    @BindView(R.id.game_list)
    RecyclerView mGameList;

    GameListAdapter mAdapter;
    @Inject
    IGameSelectPresenter<IGameSelectContract.IGameSelectVIew> mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
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
        List<GameItem> gameItems = AppUtils.loadDefaultGame(getContext());
        if (gameItems != null && mAdapter != null) {
            mAdapter.replaceData(gameItems);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    private void setup() {
        mAdapter = new GameListAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        mGameList.setLayoutManager(layoutManager);
        mGameList.setAdapter(mAdapter);
    }

    class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.Holder> {
        List<GameItem> mGameItems;

        public GameListAdapter() {
            mGameItems = new ArrayList<>();
        }

        public void replaceData(List<GameItem> list) {
            mGameItems = list;
            notifyDataSetChanged();
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

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIcon.setClipToOutline(true);
                }
            }

            public void bindItem(GameItem item) {
                Glide.with(getContext()).load(item.getIconRes()).into(mIcon);
                mTitle.setText(item.getTitle());
            }
        }
    }
}

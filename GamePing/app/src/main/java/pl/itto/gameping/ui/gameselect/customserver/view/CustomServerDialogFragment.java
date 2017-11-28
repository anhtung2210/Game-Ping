package pl.itto.gameping.ui.gameselect.customserver.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.itto.gameping.R;
import pl.itto.gameping.base.BaseBottomDialogFragment;
import pl.itto.gameping.base.IActionCallback;
import pl.itto.gameping.data.model.GameItem;
import pl.itto.gameping.ui.gameselect.customserver.ICustomGameContract;
import pl.itto.gameping.utils.AppConstants;
import pl.itto.gameping.utils.AppConstants.GameSelect.CustomServer;

/**
 * Created by PL_itto on 11/23/2017.
 */

public class CustomServerDialogFragment extends BaseBottomDialogFragment implements ICustomGameContract.ICustomGameView {
    private static final String TAG = "PL_itto.CustomServerDialogFragment";
    IActionCallback mCustomServerCallback;
    @BindView(R.id.title_edit)
    EditText mTitleEdit;
    @BindView(R.id.address_edit)
    EditText mAddressEdit;
    @BindView(R.id.create_server_btn)
    Button mDoneBtn;

    private int mMode = -1;
    private String mTitle = null, mAddress = null;

    public static CustomServerDialogFragment newInstance(@Nullable IActionCallback callback, int mode) {
        Bundle args = new Bundle();
        args.putInt(CustomServer.EXTRA_MODE, mode);
        CustomServerDialogFragment fragment = new CustomServerDialogFragment();
        fragment.mCustomServerCallback = callback;
        fragment.setArguments(args);
        return fragment;
    }

    public static CustomServerDialogFragment newInstance(@Nullable IActionCallback callback, int mode, @NonNull String title, @NonNull String address) {
        Bundle args = new Bundle();
        args.putInt(CustomServer.EXTRA_MODE, mode);
        args.putString(CustomServer.EXTRA_TITLE, title);
        args.putString(CustomServer.EXTRA_ADDRESS, address);
        CustomServerDialogFragment fragment = new CustomServerDialogFragment();
        fragment.mCustomServerCallback = callback;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        Bundle arg = getArguments();
        mMode = arg.getInt(CustomServer.EXTRA_MODE, -1);
        if (mMode == CustomServer.MODE_EDIT) {
            mTitle = arg.getString(CustomServer.EXTRA_TITLE, null);
            mAddress = arg.getString(CustomServer.EXTRA_ADDRESS, null);
            if (mTitle == null || mAddress == null) {
                showToast(R.string.main_cus_info_miss);
                dismiss();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.frag_dialog_new_game, null);
        dialog.setContentView(contentView);
        View parent = (View) contentView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        contentView.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetCallback);
        }
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        parent.setLayoutParams(layoutParams);
        initView(contentView);
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);

        if (mMode == CustomServer.MODE_EDIT) {
            autoFillData();
        }
    }

    private void autoFillData(){
        mTitleEdit.setText(mTitle);
        mAddressEdit.setText(mAddress);
    }

    @OnClick({R.id.create_server_btn, R.id.action_btn})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_server_btn:
                processDone();
                break;
            case R.id.action_btn:
                dismiss();
                break;
        }
    }

    private void processDone() {
        String address = mAddressEdit.getText().toString();
        if (address.length() == 0) {
            showToast(R.string.main_cus_address_miss);
            return;
        }
        String title = mTitleEdit.getText().toString();
        if (title.length() == 0)
            title = address;
        GameItem gameItem = new GameItem();
        gameItem.setCustomHost(title, address);
        mCustomServerCallback.onSuccess(gameItem);
        dismiss();
//        switch (mMode) {
//            case CustomServer.MODE_CREATE:
//
//                break;
//            case CustomServer.MODE_EDIT:
//                break;
//        }
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

}

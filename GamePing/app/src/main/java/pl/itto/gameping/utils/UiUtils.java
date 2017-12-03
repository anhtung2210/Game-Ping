package pl.itto.gameping.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import pl.itto.gameping.R;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class UiUtils {
    public static void loadImageRes(Context context, @StringRes int resId, final View view, boolean rounded) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.centerCrop();
        if (rounded) {
            requestOptions.apply(RequestOptions.bitmapTransform(new RoundedCorners(context.getResources().getDimensionPixelSize(R.dimen.main_item_round_radius))));
        }
//        Glide.with(context).asBitmap().load(resId).apply(requestOptions).into(view);
        Glide.with(context).asBitmap().load(resId).apply(requestOptions).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                Drawable drawable = new BitmapDrawable(resource);
                if (view != null)
                    view.setBackground(drawable);
            }
        });
    }
}

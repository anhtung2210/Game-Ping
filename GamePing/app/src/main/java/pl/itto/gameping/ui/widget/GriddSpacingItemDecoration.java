package pl.itto.gameping.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by PL_itto on 11/23/2017.
 */

public class GriddSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpanCount;
    private int mSpacing;
    private boolean mIncludeEdge;
    private int mHeaderNum;

    public GriddSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, int headerNum) {
        mSpanCount = spanCount;
        mSpacing = spacing;
        mIncludeEdge = includeEdge;
        mHeaderNum = headerNum;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view) - mHeaderNum;
        if (position >= 0) {
            int column = position % mSpanCount;
            if (mIncludeEdge) {
                outRect.left = mSpacing - column * mSpacing / mSpanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * mSpacing / mSpanCount;// (column + 1) * ((1f / spanCount) * spacing)\
                if (position < mSpanCount) {  //top edge
                    outRect.top = mSpacing;
                }

                outRect.bottom = mSpacing; // item bottom
            } else {
                outRect.left = column * mSpacing / mSpanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = mSpacing - (column + 1) * mSpacing / mSpanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= mSpanCount) {
                    outRect.top = mSpacing;//item top
                }
            }
        } else {
            outRect.left = 0;
            outRect.top = 0;
            outRect.right = 0;
            outRect.bottom = 0;
        }
    }
}

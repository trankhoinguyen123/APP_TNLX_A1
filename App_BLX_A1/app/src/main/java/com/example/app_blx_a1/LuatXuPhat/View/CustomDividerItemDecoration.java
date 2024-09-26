package com.example.app_blx_a1.LuatXuPhat.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable divider;
    private final int verticalMargin;

    public CustomDividerItemDecoration(Context context, int resId, int verticalMargin) {
        divider = ContextCompat.getDrawable(context, resId);
        this.verticalMargin = verticalMargin;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin + verticalMargin;
            int bottom = top + (divider != null ? divider.getIntrinsicHeight() : 0);

            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (divider == null) {
            return;
        }
        outRect.set(0, 0, 0, divider.getIntrinsicHeight() + (verticalMargin * 2));
    }
}

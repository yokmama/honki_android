package com.yokmama.learn10.chapter07.lesson33;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yokmama on 15/05/07.
 */
public abstract class ItemClickListener implements RecyclerView.OnItemTouchListener{
    private final GestureDetectorCompat mGestureDetector;

    public ItemClickListener(RecyclerView recyclerView){
        mGestureDetector = new ItemClickGestureDetector(recyclerView.getContext(),
                new ItemClickGestureListener(recyclerView));
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (!isAttachedToWindow(rv) || !hasAdapter(rv)) {
            return false;
        }

        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    abstract boolean performItemClick(RecyclerView parent, View view, int position, long id);

    abstract boolean performItemLongClick(RecyclerView parent, View view, int position, long id);

    private boolean isAttachedToWindow(RecyclerView hostView) {
        if (Build.VERSION.SDK_INT >= 19) {
            return hostView.isAttachedToWindow();
        } else {
            return (hostView.getHandler() != null);
        }
    }

    private boolean hasAdapter(RecyclerView hostView) {
        return (hostView.getAdapter() != null);
    }

    private class ItemClickGestureDetector extends GestureDetectorCompat {
        private final ItemClickGestureListener mGestureListener;

        public ItemClickGestureDetector(Context context, ItemClickGestureListener listener) {
            super(context, listener);
            mGestureListener = listener;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final boolean handled = super.onTouchEvent(event);

            final int action = event.getAction() & MotionEventCompat.ACTION_MASK;
            if (action == MotionEvent.ACTION_UP) {
                mGestureListener.dispatchSingleTapUpIfNeeded(event);
            }

            return handled;
        }
    }

    private class ItemClickGestureListener extends GestureDetector.SimpleOnGestureListener {
        private final RecyclerView mRecyclerView;
        private View mTargetChild;
        public ItemClickGestureListener(RecyclerView recyclerView) {
            mRecyclerView = recyclerView;
        }

        public void dispatchSingleTapUpIfNeeded(MotionEvent event) {
            // When the long press hook is called but the long press listener
            // returns false, the target child will be left around to be
            // handled later. In this case, we should still treat the gesture
            // as potential item click.
            if (mTargetChild != null) {
                onSingleTapUp(event);
            }
        }

        @Override
        public boolean onDown(MotionEvent event) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            mTargetChild = mRecyclerView.findChildViewUnder(x, y);
            return (mTargetChild != null);
        }

        @Override
        public void onShowPress(MotionEvent event) {
            if (mTargetChild != null) {
                mTargetChild.setPressed(true);
            }
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            boolean handled = false;

            if (mTargetChild != null) {
                mTargetChild.setPressed(false);

                final int position = mRecyclerView.getChildPosition(mTargetChild);
                final long id = mRecyclerView.getAdapter().getItemId(position);
                handled = performItemClick(mRecyclerView, mTargetChild, position, id);

                mTargetChild = null;
            }

            return handled;
        }

        @Override
        public boolean onScroll(MotionEvent event, MotionEvent event2, float v, float v2) {
            if (mTargetChild != null) {
                mTargetChild.setPressed(false);
                mTargetChild = null;

                return true;
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            if (mTargetChild == null) {
                return;
            }

            final int position = mRecyclerView.getChildPosition(mTargetChild);
            final long id = mRecyclerView.getAdapter().getItemId(position);
            final boolean handled = performItemLongClick(mRecyclerView, mTargetChild, position, id);

            if (handled) {
                mTargetChild.setPressed(false);
                mTargetChild = null;
            }
        }
    }
}

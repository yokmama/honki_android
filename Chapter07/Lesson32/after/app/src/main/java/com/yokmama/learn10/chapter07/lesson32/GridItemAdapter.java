package com.yokmama.learn10.chapter07.lesson32;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * GridItemを表示するアダプター.
 */
public class GridItemAdapter extends RecyclerView.Adapter<GridItemAdapter.ViewHolder> {

    private static final String TAG = GridItemAdapter.class.getSimpleName();

    private LayoutInflater mLayoutInflater;

    private RecyclerView mRecyclerView;

    private List<Integer> mItems;

    private OnItemClickListener mListener;

    public GridItemAdapter(Context context, List<Integer> items) {
        super();
        mItems = items;
        mLayoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final View v = mLayoutInflater.inflate(R.layout.item_grid_row, viewGroup, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView != null && mListener != null) {
                    int position = mRecyclerView.getChildAdapterPosition(view);
                    mListener.onItemClick(GridItemAdapter.this, v, position);
                }
            }
        });
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //画像をセット
        viewHolder.imagePicture.setImageResource(getItem(i));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    public int getItem(int position) {
        return mItems.get(position);
    }

    /**
     * ViewHolder.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagePicture;

        public ViewHolder(View v) {
            super(v);
            imagePicture = (ImageView) v.findViewById(R.id.imagePicture);
        }
    }

    /**
     * クリックリスナー.
     */
    public static interface OnItemClickListener {

        public void onItemClick(GridItemAdapter adapter, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
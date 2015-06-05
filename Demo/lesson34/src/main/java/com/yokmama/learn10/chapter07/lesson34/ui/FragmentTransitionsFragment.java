package com.yokmama.learn10.chapter07.lesson34.ui;

import com.yokmama.learn10.chapter07.lesson34.R;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kayo on 15/04/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class FragmentTransitionsFragment extends Fragment {
    private static final String EXTRA_PAGE = "extra.PAGE";
    private static final String EXTRA_TRANSITIONS_TYPE_VIEW_ID = "extra.TRANSITIONS_TYPE_VIEW_ID";

    /** 背景のカラー一覧 */
    private static int[] MD_COLORS = new int[] {
            R.color.md_purple_800,
            R.color.md_purple_500,
            R.color.md_pink_700,
    };

    public static FragmentTransitionsFragment newInstance(int page, int viewId) {
        FragmentTransitionsFragment f = new FragmentTransitionsFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_PAGE, page);
        args.putInt(EXTRA_TRANSITIONS_TYPE_VIEW_ID, viewId);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_transitions, container, false);
        TextView textView = (TextView) v.findViewById(R.id.text);

        // 現在のページ数でテキストと背景を設定
        int page = getPage();
        v.setBackgroundResource(MD_COLORS[page % MD_COLORS.length]);
        textView.setText(String.valueOf(page));

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Transitionsを生成
        TransitionInflater ti = TransitionInflater.from(getActivity());

        // 指定されたタイプの Transitions を設定
        int viewId = getArguments().getInt(EXTRA_TRANSITIONS_TYPE_VIEW_ID);
        if (viewId == R.id.btn_add_fragment_explode) {
            setEnterTransition(ti.inflateTransition(android.R.transition.explode));
            setExitTransition(ti.inflateTransition(android.R.transition.explode));
        } else if (viewId == R.id.btn_add_fragment_fade) {
            setEnterTransition(ti.inflateTransition(android.R.transition.fade));
            setExitTransition(ti.inflateTransition(android.R.transition.fade));
        } else if (viewId == R.id.btn_add_fragment_slide) {
            setEnterTransition(ti.inflateTransition(android.R.transition.slide_left));
            setExitTransition(ti.inflateTransition(android.R.transition.slide_right));
        } else {
            // "NONE"押下時。この場合何もセットしない
        }
    }

    public int getPage() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return getArguments().getInt(EXTRA_PAGE);
        }
        return 0;
    }
}

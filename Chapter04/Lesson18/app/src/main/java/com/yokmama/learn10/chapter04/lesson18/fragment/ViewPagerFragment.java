package com.yokmama.learn10.chapter04.lesson18.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.lesson18.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);

        //ViewPagerのインスタンスを取得
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        //ViewPagerにAdapterをセット
        viewPager.setAdapter(new MyFragmentAdapter(getFragmentManager()));

        //リスナーをセット
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                // ページのスワイプ中に呼ばれる
                // position : スクロール中のページ。
                // positionOffset : ドラッグ量（0〜1）
                // positionOffsetPixels : ドラッグされたピクセル数
            }

            @Override
            public void onPageSelected(int position) {
                // 移動先のページが確定された後に呼ばれる。
                // position : 移動先のページ
                Toast.makeText(getActivity(), "selected " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // ページのスクロール状態が通知される。
                // state : ページスクロール状態
                // ViewPager.SCROLL_STATE_IDLE 初期状態。ページスクロール完了後に通知
                // ViewPager.SCROLL_STATE_DRAGGING ドラッグ開始時に通知
                // ViewPager.SCROLL_STATE_SETTLING ドラッグ終了時に通知
            }
        });

        return rootView;
    }

    public class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new ListViewFragment();
            } else if (position == 1) {
                return new GridViewFragment();
            } else {
                return new ScrollViewFragment();
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "List";
            } else if (position == 1) {
                return "Grid";
            } else {
                return "Scroll";
            }
        }
    }

    /**
     * PagerAdapterの実装例
     * 本サンプルプログラムでは使用していません。
     */
    public class MyPagerAdapter extends PagerAdapter {

        private LayoutInflater mLayoutInflaternflater = null;

        private Object mCurrentObject;

        public MyPagerAdapter(Context c) {
            super();
            mLayoutInflaternflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentObject = object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View layout = mLayoutInflaternflater.inflate(R.layout.view_pager_item, null);
            switch (position) {
                case 0:
                    layout.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    layout.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    layout.setBackgroundColor(Color.BLUE);
                    break;
            }
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

}

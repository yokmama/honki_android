package com.yokmama.learn10.chapter04.lesson18.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yokmama.learn10.chapter04.lesson18.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyFragmentAdapter(getFragmentManager()));

        return rootView;
    }


    public class MyFragmentAdapter extends FragmentStatePagerAdapter {

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

}

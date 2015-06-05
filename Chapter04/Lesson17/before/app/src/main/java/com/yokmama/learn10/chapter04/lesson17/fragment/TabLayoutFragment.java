package com.yokmama.learn10.chapter04.lesson17.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.lesson17.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabLayoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        TabLayout tabLayout3 = (TabLayout) rootView.findViewById(R.id.tabLayout);
        //タブを追加
        tabLayout3.addTab(tabLayout3.newTab().setText("タブ１"));
        tabLayout3.addTab(tabLayout3.newTab().setText("タブ２"));
        tabLayout3.addTab(tabLayout3.newTab().setText("タブ３"));

        //タブ選択時の処理
        tabLayout3.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //タブが選択された
                Toast.makeText(getActivity(), tab.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //タブが未選択になった
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //タブが再選択された
            }
        });

        return rootView;
    }


}

package com.yokmama.learn10.chapter04.lesson18.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.lesson18.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabHostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_host, container, false);

        //TabHostの初期化
        FragmentTabHost host = (FragmentTabHost) rootView.findViewById(R.id.tabHost);
        host.setup(getActivity(), getFragmentManager(), android.R.id.tabcontent);

        //ListViewFragmentのTabを追加
        TabHost.TabSpec tabSpec1 = host.newTabSpec("List").setIndicator("List");
        host.addTab(tabSpec1, ListViewFragment.class, null);

        //GridViewFragmentのTabを追加
        TabHost.TabSpec tabSpec2 = host.newTabSpec("Grid").setIndicator("Grid");
        host.addTab(tabSpec2, GridViewFragment.class, null);

        //ScrollViewFragmentのTabを追加
        TabHost.TabSpec tabSpec3 = host.newTabSpec("Scroll").setIndicator("Scroll");
        host.addTab(tabSpec3, ScrollViewFragment.class, null);

        //リスナーをセット
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //切り替えたTabのIDを表示
                Toast.makeText(getActivity(), "selected " + tabId, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


}

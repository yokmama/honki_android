package com.yokmama.learn10.chapter04.lesson18.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yokmama.learn10.chapter04.lesson18.R;
import com.yokmama.learn10.chapter04.lesson18.fragment.GridViewFragment;
import com.yokmama.learn10.chapter04.lesson18.fragment.ListViewFragment;
import com.yokmama.learn10.chapter04.lesson18.fragment.ScrollViewFragment;

public class NavigationDrawerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);

        //NavigationViewの項目に対してクリック処理
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_listview) {
                    replaceFragment(new ListViewFragment());
                    drawerLayout.closeDrawers();
                    return true;
                } else if (id == R.id.action_gridview) {
                    replaceFragment(new GridViewFragment());
                    drawerLayout.closeDrawers();
                    return true;
                } else if (id == R.id.action_scrollview) {
                    replaceFragment(new ScrollViewFragment());
                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });

        if (getSupportFragmentManager().findFragmentById(R.id.container) == null) {
            replaceFragment(new PlaceholderFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }


    public static class PlaceholderFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.placeholder, container, false);
            return rootView;
        }
    }
}

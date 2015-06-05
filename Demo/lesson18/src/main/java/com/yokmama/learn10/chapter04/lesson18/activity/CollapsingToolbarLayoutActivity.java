package com.yokmama.learn10.chapter04.lesson18.activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yokmama.learn10.chapter04.lesson18.R;

public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar_layout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarLayout);
        //タイトル文字を設定
        collapsingToolbarLayout.setTitle(getString(R.string.title_activity_collapsing_toolbar_layout));
        //閉じたときのタイトル文字色を設定
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        //開いたときのタイトル文字色を設定
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
    }
}

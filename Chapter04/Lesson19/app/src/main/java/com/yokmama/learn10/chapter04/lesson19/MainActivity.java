package com.yokmama.learn10.chapter04.lesson19;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private LinearLayout mContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContents = (LinearLayout)findViewById(R.id.contents);

        findViewById(R.id.addButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        View view = getLayoutInflater().inflate(R.layout.content, mContents, false);

        mContents.addView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}

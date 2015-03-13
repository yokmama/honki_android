package com.yokmama.learn10.chapter05.lesson21;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button1){
            Intent intent = new Intent(this, MyService.class);
            intent.setAction("show");
            startService(intent);
        }else if(v.getId() == R.id.button2){
            Intent intent = new Intent(this, MyService.class);
            intent.setAction("hide");
            startService(intent);
        }
    }
}

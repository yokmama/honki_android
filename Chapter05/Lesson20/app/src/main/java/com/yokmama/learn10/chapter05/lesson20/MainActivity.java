package com.yokmama.learn10.chapter05.lesson20;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView)findViewById(R.id.textView);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == Activity.RESULT_OK){
                String text = data.getStringExtra("text");
                mTextView.setText(text);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button1){
            Intent intent = new Intent(this, SubActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.button2){
            Intent intent = new Intent(this, SubActivity.class);
            startActivityForResult(intent, 100);
        }
    }
}

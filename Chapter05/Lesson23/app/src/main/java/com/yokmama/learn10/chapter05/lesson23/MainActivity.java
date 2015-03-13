package com.yokmama.learn10.chapter05.lesson23;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButton = (Button)findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);

                updateButtonText();
            }
        });

        updateButtonText();
    }

    private void updateButtonText(){
        MyApplication myApplication = (MyApplication)getApplication();
        mButton.setText("count="+myApplication.getCount());
    }
}

package com.yokmama.learn10.chapter07.lesson32;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;


public class PaletteActivity extends AppCompatActivity {

    private static final String TAG = PaletteActivity.class.getSimpleName();

    public static final String KEY_IMAGE = "key-image";

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_palette);

        //ツールバーの初期化
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Toolbar上に戻る矢印を追加
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //画像リソースを遷移元のActivityから取得(-1は空を意味する)
        int resId = -1;
        if (getIntent() != null) {
            resId = getIntent().getIntExtra(KEY_IMAGE, -1);
        }

        //画像リソースが確認できなかったら終了
        if (resId == -1) {
            finish();
        }

        //画像をセット
        ImageView imageView = (ImageView) findViewById(R.id.imagePicture);
        imageView.setImageResource(resId);

        //画像のパレットを解析
        //TODO:レッスンではここにプログラムを追加
    }
}
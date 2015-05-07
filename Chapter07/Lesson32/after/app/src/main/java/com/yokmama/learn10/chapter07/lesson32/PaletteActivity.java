package com.yokmama.learn10.chapter07.lesson32;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


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
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Mutedな色情報を取得してToolbarにセット
                Palette.Swatch muted = palette.getMutedSwatch();
                if (muted != null) {
                    mToolbar.setBackgroundColor(muted.getRgb());
                    TextView tvTitle = (TextView) findViewById(R.id.textTitle);
                    tvTitle.setTextColor(muted.getTitleTextColor());
                }

                //カラーブロックをセット
                setPalletBlock(palette.getLightVibrantColor(Color.TRANSPARENT), R.id.viewPalette1);
                setPalletBlock(palette.getVibrantColor(Color.TRANSPARENT), R.id.viewPalette2);
                setPalletBlock(palette.getDarkVibrantColor(Color.TRANSPARENT), R.id.viewPalette3);
                setPalletBlock(palette.getLightMutedColor(Color.TRANSPARENT), R.id.viewPalette4);
                setPalletBlock(palette.getMutedColor(Color.TRANSPARENT), R.id.viewPalette5);
                setPalletBlock(palette.getDarkMutedColor(Color.TRANSPARENT), R.id.viewPalette6);
            }
        });

    }


    /**
     * 指定したViewにカラーをセット(色がない場合は非表示).
     *
     * @param color  カラー
     * @param viewId ビューID
     */
    private void setPalletBlock(int color, int viewId) {
        View view = findViewById(viewId);
        if (color == Color.TRANSPARENT) {
            view.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(color);
        }
    }
}
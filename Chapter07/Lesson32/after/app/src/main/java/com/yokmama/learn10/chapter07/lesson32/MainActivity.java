package com.yokmama.learn10.chapter07.lesson32;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //画像リソース
    private static final int[] catImages = {R.raw.cat1, R.raw.cat2, R.raw.cat3, R.raw.cat4,
            R.raw.cat5, R.raw.cat6, R.raw.cat7, R.raw.cat8, R.raw.cat9, R.raw.cat10, R.raw.cat11,
            R.raw.cat12, R.raw.cat13, R.raw.cat14, R.raw.cat15, R.raw.cat16, R.raw.cat17};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //GridViewにセットする画像リストを生成
        List<Integer> itemList = new ArrayList<>();
        for (int i = 0; i < catImages.length; i++) {
            itemList.add(catImages[i]);
        }

        //GirdViewに画像をセット
        GridView gridView = (GridView) findViewById(R.id.gridView);
        GridItemAdapter adapter = new GridItemAdapter(MainActivity.this, itemList);
        gridView.setAdapter(adapter);

        //GridViewにクリックリスナーをセット
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //クリックされた画像のリソースIDを取得
        int resId = (int) adapterView.getAdapter().getItem(i);

        //パレット解析を行うアクティビティに移動
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, view, getString(R.string.se_image));
        Intent intent = new Intent(MainActivity.this, PaletteActivity.class);
        intent.putExtra(PaletteActivity.KEY_IMAGE, resId);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
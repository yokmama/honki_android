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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lesson32_activity_main);

        //GridViewにセットする画像リストを生成
        List<Integer> itemList = getImageList();

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
        //画像のトランジッション処理のためキー名を設定（Lollipop以降のみ動作）
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, view, getString(R.string.se_image));
        Intent intent = new Intent(MainActivity.this, PaletteActivity.class);
        intent.putExtra(PaletteActivity.KEY_IMAGE, resId);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    private List<Integer> getImageList(){
        List<Integer> imageList = new ArrayList<>();
        //21個の画像をScrollViewのItemとして追加
        for (int i = 1; i <= 21; i++) {
            String resName = "dog" + i;
            //文字列からリソースのIDを取得
            int imageId = getResources().getIdentifier(resName, "drawable", getPackageName());
            imageList.add(imageId);
        }
        return imageList;
    }
}
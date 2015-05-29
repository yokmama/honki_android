package com.yokmama.learn10.chapter07.lesson32;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GridItemAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //ToolbarをActionBarとしてセット
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //GridViewにセットする画像リストを生成
        List<Integer> itemList = createImageList();

        //GirdViewに画像をセット
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        GridItemAdapter adapter = new GridItemAdapter(this, itemList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(GridItemAdapter adapter, View view, int position) {
        //クリックされた画像のリソースIDを取得
        int resId = (int) adapter.getItem(position);

        //パレット解析を行うアクティビティに移動
        //画像のトランジッション処理のためキー名を設定（Lollipop以降のみ動作）
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, view, getString(R.string.se_image));
        Intent intent = new Intent(MainActivity.this, PaletteActivity.class);
        intent.putExtra(PaletteActivity.KEY_IMAGE, resId);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    /**
     * イメージリストを作成
     */
    private List<Integer> createImageList() {
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
package com.yokmama.learn10.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private View mFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_main);

        mListView = (ListView)findViewById(android.R.id.list);
        mListView.setOnItemClickListener(this);

        mFooter = getLayoutInflater().inflate(R.layout.lesson_footer, mListView, false);
        mListView.addFooterView(mFooter);


        MyBaseAdapter adapter = new MyBaseAdapter(this, createList());
        mListView.setAdapter(adapter);
    }

    private List<LessonItem> createList(){
        List<LessonItem> list = new ArrayList<>();

        list.add(new LessonItem(R.string.titleLesson16, R.string.descLesson16, R.drawable.widget_sample_320));
        list.add(new LessonItem(R.string.titleLesson17, R.string.descLesson17, R.drawable.layout_sample_320));
        list.add(new LessonItem(R.string.titleLesson18, R.string.descLesson18, R.drawable.container_sample_320));
        list.add(new LessonItem(R.string.titleLesson26, R.string.descLesson26, R.drawable.memo_sample_320));
        list.add(new LessonItem(R.string.titleLesson27, R.string.descLesson27, R.drawable.dentaku_sample_320));
        list.add(new LessonItem(R.string.titleLesson28, R.string.descLesson28, R.drawable.todo_sample_320));
        list.add(new LessonItem(R.string.titleLesson29, R.string.descLesson29, R.drawable.screen_sample_320));
        list.add(new LessonItem(R.string.titleLesson31, R.string.descLesson31, R.drawable.toolbar_sample_320));
        list.add(new LessonItem(R.string.titleLesson32, R.string.descLesson32, R.drawable.color_sample_320));
        list.add(new LessonItem(R.string.titleLesson33, R.string.descLesson33, R.drawable.recycler_sample_320));
        list.add(new LessonItem(R.string.titleLesson34, R.string.descLesson34, R.drawable.animation_sample_320));
        list.add(new LessonItem(R.string.titleLesson35, R.string.descLesson35, R.drawable.database_sample_320));
        list.add(new LessonItem(R.string.titleLesson37, R.string.descLesson37, R.drawable.iromihon_sample_320));
        list.add(new LessonItem(R.string.titleLesson41, R.string.descLesson41, R.drawable.game_sample_320));

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter04.lesson16.ItemListActivity.class);
            startActivity(intent);
        } else if (i == 1) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter04.lesson17.ItemListActivity.class);
            startActivity(intent);
        } else if (i == 2) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter04.lesson18.ItemListActivity.class);
            startActivity(intent);
        } else if (i == 3) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter06.lesson26.MainActivity.class);
            startActivity(intent);
        } else if (i == 4) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter06.lesson27.MainActivity.class);
            startActivity(intent);
        } else if (i == 5) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter06.lesson28.MainActivity.class);
            startActivity(intent);
        } else if (i == 6) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter06.lesson29.MainActivity.class);
            startActivity(intent);
        } else if (i == 7) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter07.lesson31.MainActivity.class);
            startActivity(intent);
        } else if (i == 8) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter07.lesson32.MainActivity.class);
            startActivity(intent);
        } else if (i == 9) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter07.lesson33.MainActivity.class);
            startActivity(intent);
        } else if (i == 10) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter07.lesson34.MainActivity.class);
            startActivity(intent);
        } else if (i == 11) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter08.lesson35.MainActivity.class);
            startActivity(intent);
        } else if (i == 12) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter08.lesson37.MainActivity.class);
            startActivity(intent);
        } else if (i == 13) {
            Intent intent = new Intent(MainActivity.this, com.yokmama.learn10.chapter09.lesson41.android.AndroidLauncher.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, PromotionActivity.class);
            startActivity(intent);
        }

    }

    private class LessonItem {
        int lessonTitle;
        int lessonDesc;
        int lessonImage;

        LessonItem(int a, int b, int c){
            lessonTitle = a;
            lessonDesc = b;
            lessonImage = c;
        }

    }

    private class MyBaseAdapter extends BaseAdapter{
        private List<LessonItem> mItems;
        private LayoutInflater mLayoutInflater;

        MyBaseAdapter(Context context, List<LessonItem> items){
            mItems = items;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int i) {
            return mItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = mLayoutInflater.inflate(R.layout.lesson_item, viewGroup, false);
            }

            LessonItem lessonItem = (LessonItem)getItem(i);

            TextView textTitle = (TextView)view.findViewById(R.id.textTitle);
            TextView textDescription = (TextView)view.findViewById(R.id.textDescription);
            ImageView imageLesson = (ImageView)view.findViewById(R.id.imageLesson);

            textTitle.setText(lessonItem.lessonTitle);
            textDescription.setText(lessonItem.lessonDesc);
            imageLesson.setImageResource(lessonItem.lessonImage);

            return view;
        }
    }
}

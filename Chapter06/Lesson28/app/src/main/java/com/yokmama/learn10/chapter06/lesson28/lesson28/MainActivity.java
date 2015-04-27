package com.yokmama.learn10.chapter06.lesson28.lesson28;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private List<Todo> mTodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ダミーデータ作成
        mTodoList = Todo.addDummyItem();

        //TODOリスト一覧を表示
        showTodoList();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            //フォーム画面を開いている場合は画面を閉じる
            getFragmentManager().popBackStack();
        } else {
            //リスト画面の場合は通常のバックキー処理(アプリを終了)
            super.onBackPressed();
        }
    }

    /**
     * TODOリスト一覧を表示
     */
    public void showTodoList() {
        String tag = TodoListFragment.TAG;
        getFragmentManager().beginTransaction().replace(R.id.container,
                TodoListFragment.newInstance(), tag).addToBackStack(tag).commit();
    }

    /**
     * TODOフォーム画面を表示
     *
     * @param item TODOリストデータ
     */
    public void showTodoForm(Todo item) {
        String tag = TodoFormFragment.TAG;
        TodoFormFragment fragment;
        if (item == null) {
            fragment = TodoFormFragment.newInstance();
        } else {
            fragment = TodoFormFragment.newInstance(item.getColorLabel(),
                    item.getValue(), item.getCreatedTime());
        }
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, tag)
                .addToBackStack(tag).commit();
    }

    public List<Todo> getTodoList() {
        return mTodoList;
    }
}


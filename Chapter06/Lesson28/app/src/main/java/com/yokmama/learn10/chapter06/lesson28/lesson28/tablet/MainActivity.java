package com.yokmama.learn10.chapter06.lesson28.lesson28.tablet;

import com.yokmama.learn10.chapter06.lesson28.lesson28.R;
import com.yokmama.learn10.chapter06.lesson28.lesson28.Todo;
import com.yokmama.learn10.chapter06.lesson28.lesson28.TodoListFragment;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private List<Todo> mTodoList;

    private boolean mIsTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ダミーデータ作成
        mTodoList = Todo.addDummyItem();

        //TODOリスト一覧を表示
        showTodoList();

        //タブレットレイアウトなら右側にフォーム画面を表示
        FrameLayout container2 = (FrameLayout) findViewById(R.id.container2);
        if (container2 != null) {
            mIsTablet = true;
            showTodoForm(mTodoList.get(0));
        }
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
        int layoutId = R.id.container;
        //タブレットの場合はcontainer2に表示
        if (mIsTablet) {
            layoutId = R.id.container2;
        }
        getFragmentManager().beginTransaction().replace(layoutId,
                fragment, tag).addToBackStack(tag).commit();
    }

    public List<Todo> getTodoList() {
        return mTodoList;
    }

    /**
     * タブレットか判定.
     * @return
     */
    public boolean isTablet() {
        return mIsTablet;
    }
}


package com.yokmama.learn10.chapter06.lesson28.lesson28;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final int MENU_ID_DELETE = 1;

    public static final String ACTION_CREATE_TODO = "action-create_todo";

    private TodoListAdapter mAdapter;

    private List<Todo> mTodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListViewを初期化
        mTodoList = Todo.addDummyItem();
        ListView listView = (ListView) findViewById(R.id.todoList);
        mAdapter = new TodoListAdapter(this, mTodoList);
        listView.setOnItemClickListener(this);
        listView.setAdapter(mAdapter);
        registerForContextMenu(listView);

        //追加ボタンのクリック処理
        findViewById(R.id.createTodo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTodo();
            }
        });

        //新規TODOリストを検知するブロードキャストレシーバを登録
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mAddTodoReceiver, new IntentFilter(ACTION_CREATE_TODO));
    }

    @Override
    protected void onDestroy() {
        //新規TODOリストを検知するブロードキャストレシーバを解除
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mAddTodoReceiver);
        super.onDestroy();
    }

    /**
     * 作成したTodoリストを追加する検知するブロードキャストレシーバ.
     */
    BroadcastReceiver mAddTodoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Todoデータを作成
            int color = intent.getIntExtra(TodoActivity.KEY_COLORLABEL, Todo.ColorLabel.NONE);
            String value = intent.getStringExtra(TodoActivity.KEY_VALUE);
            long createdTime = intent.getLongExtra(TodoActivity.KEY_CREATEDTIME, 0);
            Todo newItem = new Todo(color, value, createdTime);

            //作成時間を既に存在するデータか確認
            int updateIndex = -1;
            for (int i = 0; i < mAdapter.getCount(); i++) {
                Todo item = mAdapter.getItem(i);
                if (item.getCreatedTime() == newItem.getCreatedTime()) {
                    updateIndex = i;
                }
            }
            if (updateIndex == -1) {
                //既存データがなければ新規Todoとして追加
                mTodoList.add(newItem);
            } else {
                mTodoList.remove(updateIndex);
                mTodoList.add(updateIndex, newItem);
            }
            mAdapter.notifyDataSetChanged();

        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
            ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        //ListViewのコンテキストメニューを作成
        if (view.getId() == R.id.todoList) {
            menu.setHeaderTitle("選択アイテム");
            menu.add(0, MENU_ID_DELETE, 0, "削除");
        }
    }

    //コンテキストメニュークリック時のリスナ
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        int itemId = item.getItemId();
        if (itemId == MENU_ID_DELETE) {
            //削除
            removeTodo(info.position);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        editTodo(mAdapter.getItem(position));
    }

    /**
     * Todoリストを作成.
     */
    private void createTodo() {
        startActivity(new Intent(this, TodoActivity.class));
    }

    /**
     * Todoリストを編集.
     */
    private void editTodo(Todo item) {
        Intent intent = new Intent(this, TodoActivity.class);
        intent.putExtra(TodoActivity.KEY_COLORLABEL, item.getColorLabel());
        intent.putExtra(TodoActivity.KEY_VALUE, item.getValue());
        intent.putExtra(TodoActivity.KEY_CREATEDTIME, item.getCreatedTime());
        startActivity(intent);
    }

    /**
     * Todoリストを削除.
     */
    private void removeTodo(int position) {
        Todo removeItem = mAdapter.getItem(position);
        mAdapter.remove(removeItem);
        mAdapter.notifyDataSetChanged();
    }
}

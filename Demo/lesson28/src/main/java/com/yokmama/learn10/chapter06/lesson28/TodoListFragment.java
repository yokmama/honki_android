package com.yokmama.learn10.chapter06.lesson28;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class TodoListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG = TodoListFragment.class.getSimpleName();

    public static final String ACTION_CREATE_TODO = "action-create_todo";

    private static final int MENU_ID_DELETE = 1;

    private TodoListAdapter mAdapter;

    private List<Todo> mTodoList;


    public static TodoListFragment newInstance() {
        return new TodoListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        //ダミーデータを作成してAdapterにセット
        mTodoList = ((MainActivity) getActivity()).getTodoList();
        mAdapter = new TodoListAdapter(getActivity(), mTodoList);

        //ListViewを初期化
        ListView listView = (ListView) rootView.findViewById(R.id.todo_list);
        listView.setOnItemClickListener(this);
        listView.setAdapter(mAdapter);

        //ListViewにコンテキストメニューを設定
        registerForContextMenu(listView);

        //新規作成Buttonの設定
        rootView.findViewById(R.id.create_todo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODOリストを追加
                ((MainActivity) getActivity()).showTodoForm(null);
            }
        });

        //BroadcastReceiverを登録
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mAddTodoReceiver, new IntentFilter(ACTION_CREATE_TODO));

        return rootView;
    }

    @Override
    public void onDestroy() {
        //BroadcastReceiverを解除
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mAddTodoReceiver);
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //クリックしたアイテムを表示
        ((MainActivity) getActivity()).showTodoForm(mAdapter.getItem(position));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
            ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        //ListViewのコンテキストメニューを作成
        if (view.getId() == R.id.todo_list) {
            menu.setHeaderTitle("選択アイテム");
            menu.add(0, MENU_ID_DELETE, 0, "削除");
        }
    }

    //コンテキストメニュークリック時のリスナー
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        int itemId = item.getItemId();
        if (itemId == MENU_ID_DELETE) {
            //アイテムを削除
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Todoリストの作成・変更を検知するBroadcastReceiver.
     */
    BroadcastReceiver mAddTodoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Todoデータを作成
            int color = intent.getIntExtra(TodoFormFragment.ARGS_COLORLABEL, Todo.ColorLabel.NONE);
            String value = intent.getStringExtra(TodoFormFragment.ARGS_VALUE);
            long createdTime = intent.getLongExtra(TodoFormFragment.ARGS_CREATEDTIME, 0);
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
                //既存データがあれば上書き
                mTodoList.remove(updateIndex);
                mTodoList.add(updateIndex, newItem);
            }

            //TODOリストを更新
            mAdapter.notifyDataSetChanged();

        }
    };
}
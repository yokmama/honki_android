package com.yokmama.learn10.chapter04.lesson18.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.lesson18.R;
import com.yokmama.learn10.chapter04.lesson18.tools.ListItem;
import com.yokmama.learn10.chapter04.lesson18.tools.SampleDataGenerator;
import com.yokmama.learn10.chapter04.lesson18.tools.SampleListAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ListItem>>{
    private ListView mListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);

        mListView = (ListView)rootView.findViewById(R.id.listView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem item = (ListItem)mListView.getAdapter().getItem(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("Selected " + item.getName());
                dialog.setPositiveButton(android.R.string.ok, null);
                dialog.show();
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDestroy() {
        getLoaderManager().destroyLoader(0);
        super.onDestroy();
    }

    @Override
    public Loader<List<ListItem>> onCreateLoader(int i, Bundle bundle) {
        return new SampleDataGenerator(getActivity(), SampleDataGenerator.DataType.Text);
    }

    @Override
    public void onLoadFinished(Loader<List<ListItem>> stringLoader, List<ListItem> items) {

        SampleListAdapter adapter = new SampleListAdapter(getActivity(), items);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<ListItem>> stringLoader) {

    }
}

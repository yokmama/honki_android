package com.yokmama.learn10.chapter07.lesson32.lesson32;

import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.GridView;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // インストール済みアプリケーションアイコンをGridViewに表示
        new GetInstalledAppIconTask().execute();
    }

    /**
     * インストール済みのアプリのアイコンを取得するAsyncTask.
     */
    private class GetInstalledAppIconTask extends AsyncTask<Void, Void, List<ApplicationInfo>> {

        @Override
        protected List<ApplicationInfo> doInBackground(Void... voids) {
            //アプリケーション情報を取得
            return getPackageManager().getInstalledApplications(0);
        }

        @Override
        protected void onPostExecute(List<ApplicationInfo> result) {
            super.onPostExecute(result);
            //GirdViewにアイコンを表示
            GridView gridView = (GridView) findViewById(R.id.gridView);
            InstalledAppAdapter adapter = new InstalledAppAdapter(MainActivity.this, result);
            gridView.setAdapter(adapter);
        }
    }
}
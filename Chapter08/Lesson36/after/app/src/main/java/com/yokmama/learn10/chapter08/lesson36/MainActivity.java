package com.yokmama.learn10.chapter08.lesson36;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends FragmentActivity {
    public static final Uri CONTENT_URI = Uri.parse("content://com.yokmama.learn10.chapter08.lesson35/memo");
    private EditText mEditText;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                insertMemo(mEditText.getText().toString(), System.currentTimeMillis());
                mEditText.setText("");
                loadMemo();
            }
        });
        loadMemo();
    }

    private void insertMemo(String text, long date) {
        ContentValues values = new ContentValues();
        values.put("text", text);
        values.put("date", date);
        getContentResolver().insert(CONTENT_URI, values);
    }

    private void loadMemo() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder builder = new StringBuilder();
        Cursor cursor = getContentResolver().query(CONTENT_URI, new String[]{"date", "text"}, null, null, "date ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                calendar.setTimeInMillis(cursor.getLong(0));
                CharSequence date = android.text.format.DateFormat.format("yyyy/MM/dd, E, kk:mm", calendar);
                String text = cursor.getString(1);
                builder.append(date + ":" + text).append("\n");
            }
        }
        mTextView.setText(builder.toString());
    }
}

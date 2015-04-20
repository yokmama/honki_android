package com.yokmama.learn10.chapter06.lesson28.lesson28;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;


public class TodoActivity extends ActionBarActivity implements RadioGroup.OnCheckedChangeListener,
        TextWatcher {

    public static final String KEY_COLORLABEL = "key-colorlabel";

    public static final String KEY_VALUE = "key-value";

    public static final String KEY_CREATEDTIME = "key-createdtime";

    private int mColorLabel = Todo.ColorLabel.NONE;

    private long mCreatedTime = 0;

    private EditText mEtInput;

    private boolean mIsTextEdited = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //戻るボタンをクリック
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        mEtInput = (EditText) findViewById(R.id.input);
        mEtInput.addTextChangedListener(this);

        RadioGroup rgColorLabel = (RadioGroup) findViewById(R.id.colorLabelFrame);
        rgColorLabel.setOnCheckedChangeListener(this);

        //編集データを受け取っていたらセット
        Intent intent = getIntent();
        if (intent != null) {
            //カラーラベルをセット
            mColorLabel = intent.getIntExtra(KEY_COLORLABEL, Todo.ColorLabel.NONE);
            mEtInput.setTextColor(getColorResource(mColorLabel));
            if (mColorLabel == Todo.ColorLabel.NONE) {
                rgColorLabel.check(R.id.none);
            } else if (mColorLabel == Todo.ColorLabel.AMBER) {
                rgColorLabel.check(R.id.amber);
            } else if (mColorLabel == Todo.ColorLabel.PINK) {
                rgColorLabel.check(R.id.pink);
            } else if (mColorLabel == Todo.ColorLabel.INDIGO) {
                rgColorLabel.check(R.id.indigo);
            } else if (mColorLabel == Todo.ColorLabel.GREEN) {
                rgColorLabel.check(R.id.green);
            }

            //値をセット
            String value = intent.getStringExtra(KEY_VALUE);
            mEtInput.setText(value);

            //作成時間をセット
            mCreatedTime = intent.getLongExtra(KEY_CREATEDTIME, 0);
        }

        //戻るボタンを追加
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        //Todoリストの中身があればデータを送信
        String value = mEtInput.getText().toString();
        if (!TextUtils.isEmpty(value) && mIsTextEdited) {
            Intent resultData = new Intent();
            resultData.putExtra(KEY_COLORLABEL, mColorLabel);
            resultData.putExtra(KEY_VALUE, value);
            if (mCreatedTime == 0) {
                //作成時間がない場合は新規データとして作成時間を生成
                resultData.putExtra(KEY_CREATEDTIME, System.currentTimeMillis());
            } else {
                //作成時間がある場合は既存のデータを更新
                resultData.putExtra(KEY_CREATEDTIME, mCreatedTime);
            }
            resultData.setAction(MainActivity.ACTION_CREATE_TODO);
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultData);
            finish();
        }
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.none) {
            mColorLabel = Todo.ColorLabel.NONE;
        } else if (checkedId == R.id.amber) {
            mColorLabel = Todo.ColorLabel.AMBER;
        } else if (checkedId == R.id.pink) {
            mColorLabel = Todo.ColorLabel.PINK;
        } else if (checkedId == R.id.indigo) {
            mColorLabel = Todo.ColorLabel.INDIGO;
        } else if (checkedId == R.id.green) {
            mColorLabel = Todo.ColorLabel.GREEN;
        }
        mEtInput.setTextColor(getColorResource(mColorLabel));
    }

    /**
     * カラーラベルに応じたカラーリソースを返却.
     *
     * @param color : カラー
     */
    private int getColorResource(int color) {
        int resId = Todo.ColorLabel.NONE;
        if (color == Todo.ColorLabel.NONE) {
            resId = getResources().getColor(R.color.md_grey_500);
        } else if (color == Todo.ColorLabel.AMBER) {
            resId = getResources().getColor(R.color.md_amber_500);
        } else if (color == Todo.ColorLabel.PINK) {
            resId = getResources().getColor(R.color.md_pink_500);
        } else if (color == Todo.ColorLabel.INDIGO) {
            resId = getResources().getColor(R.color.md_indigo_500);
        } else if (color == Todo.ColorLabel.GREEN) {
            resId = getResources().getColor(R.color.md_green_500);
        }
        return resId;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //テキストを編集したと判定
        mIsTextEdited = true;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}

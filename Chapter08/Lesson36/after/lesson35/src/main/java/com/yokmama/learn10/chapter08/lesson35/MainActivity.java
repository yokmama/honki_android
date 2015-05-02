package com.yokmama.learn10.chapter08.lesson35;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import greendao.Memo;
import greendao.MemoDao;


public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTextView;
    private SpeechRecognizer mSpeechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                        getPackageName());
                mSpeechRecognizer.startListening(intent);
            }
        });

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(myRecognitionListener);

        loadMemo();
    }

    private void loadMemo() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder builder = new StringBuilder();
        List<Memo> list = getDao(this).loadAll();
        for (Memo memo : list) {
            calendar.setTimeInMillis(memo.getDate());
            CharSequence date = android.text.format.DateFormat.format("yyyy/MM/dd, E, kk:mm", calendar);
            String text = memo.getText();
            builder.append(date + ":" + text).append("\n");
        }
        mTextView.setText(builder.toString());
    }

    private void insertMemo(String text, long date) {
        Memo voice = new Memo();
        voice.setText(text);
        voice.setDate(date);
        getDao(this).insertOrReplace(voice);
    }

    private static MemoDao getDao(Context c) {
        return ((MyApplication) c.getApplicationContext()).getDaoSession().getMemoDao();
    }

    RecognitionListener myRecognitionListener = new RecognitionListener() {

        @Override
        public void onBeginningOfSpeech() {
            // 音声入力開始
            Toast.makeText(getApplicationContext(), "音声入力開始", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // 録音データのフィードバック
            Log.d(TAG, "onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            // 音声入力終了
            Toast.makeText(getApplicationContext(), "音声入力終了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int error) {
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    // 音声データ保存失敗
                    Log.d(TAG, "音声データ保存失敗:" + error);
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    // Android端末内のエラー(その他)
                    Log.d(TAG, "Android端末内のエラー(その他):" + error);
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    // 権限無し
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    // ネットワークエラー
                    Toast.makeText(getApplicationContext(), "ネットワークエラーですデバイスの設定を見なおしてください。:" + error, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    // 音声認識結果無し
                    Toast.makeText(getApplicationContext(), "音声をテキストに変換できませんでした。:" + error, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    // RecognitionServiceへ要求出せず
                    Log.d(TAG, "RecognitionServiceへ要求出せず:" + error);
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    // Server側からエラー通知
                    Log.d(TAG, "Server側からエラー通知:" + error);
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    // 音声入力無し
                    Toast.makeText(getApplicationContext(), "音声入力がありません。:" + error, Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            //イベント発生時に呼び出される
            Log.d(TAG, "onEvent:" + eventType);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            //部分的な認識結果が得られる場合に呼び出される
            Log.d(TAG, "onPartialResults");
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            // 音声認識準備完了
            Log.d(TAG, "onReadyForSpeech");
        }

        @Override
        public void onResults(Bundle results) {
            //認識結果
            ArrayList<String> candidates = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (candidates.size() > 0) {
                //他の候補はログとして出力しておく
                //必要であれば、選択ダイアログなどで選ばせると良いだろう
                for (String recognized : candidates) {
                    Log.d(TAG, "変換候補:" + recognized);
                }

                insertMemo(candidates.get(0), System.currentTimeMillis());
                loadMemo();
            }
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            //入力音声のdBが変化した
            Log.d(TAG, "onRmsChanged:" + rmsdB);
        }
    };
}

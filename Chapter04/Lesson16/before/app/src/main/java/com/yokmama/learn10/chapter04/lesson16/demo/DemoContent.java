package com.yokmama.learn10.chapter04.lesson16.demo;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yokmama.learn10.chapter04.lesson16.fragment.ButtonFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.CheckBoxFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.EditTextFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.ImageButtonFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.ImageViewFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.ProgressBarFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.RadioButtonFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.RatingBarFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.SeekBarFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.SnackbarFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.SpinnerFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.SwitchFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.TextViewFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.ToggleButtonFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.WebViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DemoContent {

    /**
     * An array of demo items.
     */
    public static List<DemoItem> ITEMS = new ArrayList<DemoItem>();

    static {
        addItem(new DemoItem(
                TextView.class.getSimpleName(),
                "TextViewを使った色々な装飾文字のサンプルを表示",
                TextViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                EditText.class.getSimpleName(),
                "EditTextやTextInputLayoutを使った文字入力のサンプルを表示",
                EditTextFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                Button.class.getSimpleName(),
                "装飾したButtonやFloatingActionButtonのサンプルを表示",
                ButtonFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                RadioButton.class.getSimpleName(),
                "RadioButtonを使ったサンプルを表示",
                RadioButtonFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                CheckBox.class.getSimpleName(),
                "CheckBoxを使ったサンプルを表示",
                CheckBoxFragment.class.getCanonicalName()));
        if (Build.VERSION.SDK_INT >= 14) {
            addItem(new DemoItem(
                    Switch.class.getSimpleName(),
                    "Switchを使ったサンプルを表示",
                    SwitchFragment.class.getCanonicalName()));
        }
        addItem(new DemoItem(
                ToggleButton.class.getSimpleName(),
                "ToggleButtonを使ったサンプルを表示",
                ToggleButtonFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                ImageButton.class.getSimpleName(),
                "ImageButtonを使った画像付きボタンのサンプルを表示",
                ImageButtonFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                ImageView.class.getSimpleName(),
                "ImageViewを使った画像のサンプルを表示",
                ImageViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                ProgressBar.class.getSimpleName(),
                "ProgressBarを使った進捗のサンプルを表示",
                ProgressBarFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                SeekBar.class.getSimpleName(),
                "SeekBarを使ったサンプルを表示",
                SeekBarFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                RatingBar.class.getSimpleName(),
                "RatingBarを使ったサンプルを表示",
                RatingBarFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                Spinner.class.getSimpleName(),
                "Spinnerを使ったサンプルを表示",
                SpinnerFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                WebView.class.getSimpleName(),
                "WebViewによるWebサイトの表示をするサンプル",
                WebViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                Toast.class.getSimpleName(),
                "ToastとSnackbarのサンプルを表示",
                SnackbarFragment.class.getCanonicalName()));
    }

    private static void addItem(DemoItem item) {
        ITEMS.add(item);
    }

    /**
     * A demo item representing a piece of content.
     */
    public static class DemoItem implements Parcelable {
        private String content;
        private String description;
        private String fragmentName;

        public DemoItem(String content, String description, String fragmentName) {
            this.content = content;
            this.description = description;
            this.fragmentName = fragmentName;
        }

        @Override
        public String toString() {
            return content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(content);
            dest.writeString(description);
            dest.writeString(fragmentName);
        }

        public static final Parcelable.Creator<DemoItem> CREATOR
                = new Parcelable.Creator<DemoItem>() {
            public DemoItem createFromParcel(Parcel in) {
                return new DemoItem(in);
            }

            public DemoItem[] newArray(int size) {
                return new DemoItem[size];
            }
        };

        private DemoItem(Parcel in) {
            content = in.readString();
            description = in.readString();
            fragmentName = in.readString();
        }

        public String getFragmentName() {
            return fragmentName;
        }

        public String getContent() {
            return content;
        }

        public String getDescription() {
            return description;
        }
    }
}

package com.yokmama.learn10.chapter04.lesson16.demo;

import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import com.yokmama.learn10.chapter04.lesson16.fragment.ButtonFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.CheckBoxFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.EditTextFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.ImageButtonFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.ProgressBarFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.RadioButtonFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.RatingBarFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.SeekBarFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.SpinnerFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.SwitchFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.TextViewFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.ToggleButtonFragment;
import com.yokmama.learn10.chapter04.lesson16.fragment.WebViewFragment;

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
        addItem(new DemoItem(TextView.class.getSimpleName(), TextViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(EditText.class.getSimpleName(), EditTextFragment.class.getCanonicalName()));
        addItem(new DemoItem(Button.class.getSimpleName(), ButtonFragment.class.getCanonicalName()));
        addItem(new DemoItem(RadioButton.class.getSimpleName(), RadioButtonFragment.class.getCanonicalName()));
        addItem(new DemoItem(CheckBox.class.getSimpleName(), CheckBoxFragment.class.getCanonicalName()));
        addItem(new DemoItem(Switch.class.getSimpleName(), SwitchFragment.class.getCanonicalName()));
        addItem(new DemoItem(ToggleButton.class.getSimpleName(), ToggleButtonFragment.class.getCanonicalName()));
        addItem(new DemoItem(ImageButton.class.getSimpleName(), ImageButtonFragment.class.getCanonicalName()));
        addItem(new DemoItem(ProgressBar.class.getSimpleName(), ProgressBarFragment.class.getCanonicalName()));
        addItem(new DemoItem(SeekBar.class.getSimpleName(), SeekBarFragment.class.getCanonicalName()));
        addItem(new DemoItem(RatingBar.class.getSimpleName(), RatingBarFragment.class.getCanonicalName()));
        addItem(new DemoItem(Spinner.class.getSimpleName(), SpinnerFragment.class.getCanonicalName()));
        addItem(new DemoItem(WebView.class.getSimpleName(), WebViewFragment.class.getCanonicalName()));
    }

    private static void addItem(DemoItem item) {
        ITEMS.add(item);
    }

    /**
     * A demo item representing a piece of content.
     */
    public static class DemoItem implements Parcelable {
        private String content;
        private String fragmentName;

        public DemoItem(String content, String fragmentName) {
            this.content = content;
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
            fragmentName = in.readString();
        }

        public String getFragmentName() {
            return fragmentName;
        }
    }
}

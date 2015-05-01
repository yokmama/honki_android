package com.yokmama.learn10.chapter04.lesson18.demo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.yokmama.learn10.chapter04.lesson18.fragment.GridViewFragment;
import com.yokmama.learn10.chapter04.lesson18.fragment.ListViewFragment;
import com.yokmama.learn10.chapter04.lesson18.fragment.ScrollViewFragment;
import com.yokmama.learn10.chapter04.lesson18.fragment.TabHostFragment;
import com.yokmama.learn10.chapter04.lesson18.fragment.ViewPagerFragment;

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
        addItem(new DemoItem(ListView.class.getSimpleName(), ListViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(GridView.class.getSimpleName(), GridViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(ScrollView.class.getSimpleName(), ScrollViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(TabHost.class.getSimpleName(), TabHostFragment.class.getCanonicalName()));
        addItem(new DemoItem(ViewPager.class.getSimpleName(), ViewPagerFragment.class.getCanonicalName()));
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

        public static final Creator<DemoItem> CREATOR
                = new Creator<DemoItem>() {
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

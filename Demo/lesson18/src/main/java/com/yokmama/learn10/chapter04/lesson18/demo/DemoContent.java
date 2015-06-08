package com.yokmama.learn10.chapter04.lesson18.demo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.yokmama.learn10.chapter04.lesson18.activity.CollapsingToolbarLayoutActivity;
import com.yokmama.learn10.chapter04.lesson18.activity.NavigationDrawerActivity;
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
        addItem(new DemoItem(
                ListView.class.getSimpleName(),
                "ListViewを使ったサンプルを表示",
                ListViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                GridView.class.getSimpleName(),
                "GridViewを使ったサンプルを表示",
                GridViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                ScrollView.class.getSimpleName(),
                "ScrollViewを使ったサンプルを表示",
                ScrollViewFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                TabHost.class.getSimpleName(),
                "TabHostを使ったサンプルを表示",
                TabHostFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                ViewPager.class.getSimpleName(),
                "ViewPagerを使ったサンプルを表示",
                ViewPagerFragment.class.getCanonicalName()));
        addItem(new DemoItem(
                CollapsingToolbarLayout.class.getSimpleName(),
                "CollapsingToolbarLayoutを使ったサンプルを表示",
                CollapsingToolbarLayoutActivity.class.getCanonicalName()));
        addItem(new DemoItem(
                NavigationView.class.getSimpleName(),
                "NavigationViewを使ったサンプルを表示",
                NavigationDrawerActivity.class.getCanonicalName()));
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

        public String getContent(){
            return content;
        }

        public String getDescription(){
            return description;
        }
    }
}

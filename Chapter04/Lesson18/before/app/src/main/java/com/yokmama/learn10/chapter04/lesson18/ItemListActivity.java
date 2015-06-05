package com.yokmama.learn10.chapter04.lesson18;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yokmama.learn10.chapter04.lesson18.demo.DemoContent;
import com.yokmama.learn10.chapter04.lesson18.demo.DemoCreator;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link com.yokmama.learn10.chapter04.lesson18.demo.DemoCreator}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends AppCompatActivity
        implements ItemListFragment.Callbacks {

    public static final String ARG_CLASS_NAME = "demoItem";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(DemoContent.DemoItem demoItem) {
        if(demoItem.getFragmentName().contains("Activity")){
            Intent intent = new Intent();
            intent.setClassName(
                    BuildConfig.APPLICATION_ID,
                    demoItem.getFragmentName());
            startActivity(intent);
        }else {
            if (mTwoPane) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, DemoCreator.create(demoItem))
                        .commit();

            } else {
                // In single-pane mode, simply start the detail activity
                // for the selected item ID.
                Intent detailIntent = new Intent(this, ItemDetailActivity.class);
                detailIntent.putExtra(ARG_CLASS_NAME, demoItem);
                startActivity(detailIntent);
            }
        }
    }
}

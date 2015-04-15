package com.yokmama.learn10.chapter07.lesson34;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;

/**
 * Created by kayo on 15/04/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TransitionActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.btn_fragment).setOnClickListener(this);

        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.RIGHT);
        getWindow().setEnterTransition(slide);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button) {
            Intent intent = new Intent(this, getClass());
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this, null).toBundle());
        } else if (id == R.id.btn_fragment) {
//            TransitionDialogFragment f = new TransitionDialogFragment();
//
//            TransitionSet ts = new TransitionSet();
//            ts.addTransition(new Fade());
//
//            Slide slide = new Slide();
//            slide.setSlideEdge(Gravity.RIGHT);
//            ts.addTransition(slide);
//
//            f.setEnterTransition(ts);
//            f.setExitTransition(ts);
////            f.setReturnTransition();
////            f.setReenterTransition();
//
//            f.show(getSupportFragmentManager(), "AnimationDialogFragment");
        } else {
            throw new RuntimeException();
        }
    }

    public static class TransitionDialogFragment extends Fragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
    }
}

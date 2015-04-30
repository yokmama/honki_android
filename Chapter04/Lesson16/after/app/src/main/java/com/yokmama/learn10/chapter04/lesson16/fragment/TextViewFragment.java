package com.yokmama.learn10.chapter04.lesson16.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yokmama.learn10.chapter04.lesson16.R;

/**
 * Created by yokmama on 15/02/19.
 */
public class TextViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text_view, container, false);

        //文字に取り消し線を設定
        TextView textRevoke = (TextView) rootView.findViewById(R.id.textRevoke);
        TextPaint paint = textRevoke.getPaint();
        paint.setFlags(textRevoke.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        paint.setAntiAlias(true);

        return rootView;
    }
}

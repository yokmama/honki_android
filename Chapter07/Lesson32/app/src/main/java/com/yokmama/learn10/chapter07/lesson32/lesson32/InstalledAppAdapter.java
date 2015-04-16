package com.yokmama.learn10.chapter07.lesson32.lesson32;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * インストール済みのアイコンを表示するアダプター.
 */
public class InstalledAppAdapter extends ArrayAdapter<ApplicationInfo> {

    private static final String TAG = InstalledAppAdapter.class.getSimpleName();

    private LayoutInflater mLayoutInflater;


    public InstalledAppAdapter(Context context, List<ApplicationInfo> items) {
        super(context, 0, items);
        mLayoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_grid_row, null);
            holder = new ViewHolder();
            holder.cardView = (CardView) convertView.findViewById(R.id.cardView);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //アプリ情報を取得
        ApplicationInfo appInfo = getItem(position);
        Drawable icon = null;
        try {
            icon = getContext().getPackageManager().getApplicationIcon(
                    appInfo.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "PackageName not found :" + e);
        }

        //アプリ名をセット
        String name = (String) getContext().getPackageManager().getApplicationLabel(appInfo);
        holder.name.setText(name);

        if (icon != null) {
            //アイコンをセット
            holder.icon.setImageDrawable(icon);

            //アイコンのカラー情報を取得してテキスト背景をダイナミックに変更
            if (icon instanceof BitmapDrawable) {
                //背景をビットマップで取得
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();

                //Paletteを使ってアイコンに使用されている鮮やかな色を取得
                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant = palette.getVibrantSwatch();
                        if (vibrant != null) {
                            //テキスト背景のカラーを変更
                            GradientDrawable bgShape = (GradientDrawable) holder.name
                                    .getBackground().getCurrent();
                            bgShape.setColor(vibrant.getRgb());
                        }
                    }
                });
            }
        }
        return convertView;
    }

    private static class ViewHolder {

        CardView cardView;

        ImageView icon;

        TextView name;
    }
}
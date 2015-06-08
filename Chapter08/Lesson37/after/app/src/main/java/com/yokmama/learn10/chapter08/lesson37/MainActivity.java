package com.yokmama.learn10.chapter08.lesson37;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends FragmentActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RequestQueue mQueue;
    private LinearLayout mColorsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorsLayout = (LinearLayout) findViewById(R.id.colorsLayout);
        mQueue = Volley.newRequestQueue(this);

        loadColor();
    }

    private void loadColor() {
        Log.d(TAG, "loadColor");
        mColorsLayout.removeAllViews();

        //接続先
        String url = "https://raw.githubusercontent.com/yokmama/honki_android/master/samples/colors.json";

        //キューにリクエストを追加
        mQueue.add(new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONArray colorsArray = response.getJSONArray("colorsArray");
                            for (int i = 0; i < colorsArray.length(); i++) {
                                JSONObject colorObject = colorsArray.getJSONObject(i);
                                addItem(colorObject.getString("colorName"), colorObject.getString("hexValue"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());

                    }
                }));
    }

    private void addItem(String colorName, String hexValue) {
        Log.d(TAG, colorName + "," + hexValue);
        TextView item = (TextView) getLayoutInflater().inflate(R.layout.color_row, null, false);

        item.setText(colorName);
        item.setBackgroundColor(Color.parseColor(hexValue));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mColorsLayout.addView(item, params);
    }
}

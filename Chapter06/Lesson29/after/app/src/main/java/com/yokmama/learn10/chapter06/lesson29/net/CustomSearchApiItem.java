package com.yokmama.learn10.chapter06.lesson29.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kayo on 15/04/13.
 */
public class CustomSearchApiItem {
    /** 画像へのリンク */
    private String link;

    public static ArrayList<CustomSearchApiItem> parse(JSONArray jsItems) throws JSONException {
        ArrayList<CustomSearchApiItem> list = new ArrayList<CustomSearchApiItem>();
        for (int i=0, iL=jsItems.length(); i<iL; i++) {
            JSONObject jsItem = jsItems.getJSONObject(i);
            CustomSearchApiItem item = new CustomSearchApiItem();

            // とりあえず、最低限必要な画像リンクだけを取得
            item.link = jsItem.getString("link");

            list.add(item);
        }

        return list;
    }

    public String getLink() {
        return link;
    }

/* 引数の JSONArray は、以下の様なJSONの形です。
 "items": [
  {
   "kind": "customsearch#result",
   "title": "Android (operating system) - Wikipedia, the free encyclopedia",
   "htmlTitle": "\u003cb\u003eAndroid\u003c/b\u003e (operating system) - Wikipedia, the free encyclopedia",
   "link": "http://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Android-System-Architecture.svg/2000px-Android-System-Architecture.svg.png",
   "displayLink": "en.wikipedia.org",
   "snippet": "Android's architecture diagram",
   "htmlSnippet": "\u003cb\u003eAndroid&#39;s\u003c/b\u003e architecture diagram",
   "mime": "image/png",
   "fileFormat": "Image Document",
   "image": {
    "contextLink": "http://en.wikipedia.org/wiki/Android_(operating_system)",
    "height": 1623,
    "width": 2000,
    "byteSize": 402096,
    "thumbnailLink": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhjpIUvXOlkYGU2rziu-Wcyf6n4E4a4TtS0QoCZKCs4zlf01xEnjsGRLPj",
    "thumbnailHeight": 122,
    "thumbnailWidth": 150
   }
  }, ...]
 */
}

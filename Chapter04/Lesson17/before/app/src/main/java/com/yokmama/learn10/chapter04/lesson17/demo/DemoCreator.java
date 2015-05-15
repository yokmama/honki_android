package com.yokmama.learn10.chapter04.lesson17.demo;


import android.support.v4.app.Fragment;

/**
 * Created by yokmama on 15/02/18.
 */
public class DemoCreator {

    public static Fragment create(DemoContent.DemoItem demoItem) {
        try {
            Class cls = Class.forName(demoItem.getFragmentName());
            return (Fragment) cls.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

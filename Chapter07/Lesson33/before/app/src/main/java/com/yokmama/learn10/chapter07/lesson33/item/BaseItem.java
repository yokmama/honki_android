package com.yokmama.learn10.chapter07.lesson33.item;

/**
 * Created by yokmama on 15/03/18.
 */
public abstract  class BaseItem {
    private String mName;

    public abstract int getType();

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}

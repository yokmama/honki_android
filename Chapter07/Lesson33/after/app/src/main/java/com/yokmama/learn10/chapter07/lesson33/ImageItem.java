package com.yokmama.learn10.chapter07.lesson33;

/**
 * Created by yokmama on 15/03/18.
 */
public class ImageItem extends BaseItem{
    private int mId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public int getType() {
        return 1;
    }
}

package com.yokmama.learn10.chapter08.lesson38;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yokmama on 15/03/20.
 */
public class Contact implements Parcelable {
    private String mName;
    private int mAge;
    private String mEmail;

    public Contact(){

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    /***
     * シリアライズされたオブジェクトの種類を判別するためのビットマスクを返す
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /***
     * データのParcelに保存
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mAge);
        dest.writeString(mEmail);
    }

    /***
     * ParcelからこのParcelableのインスタンスを作るためのCreator
     */
    public static final Parcelable.Creator<Contact> CREATOR
            = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    /***
     * Parcelからデータを読み出すprivateコンストラクタ
     *
     * @param in
     */
    private Contact(Parcel in) {
        mName = in.readString();
        mAge = in.readInt();
        mEmail = in.readString();
    }
}

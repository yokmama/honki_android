package com.yokmama.learn10.chapter06.lesson28.lesson28;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m_iwasaki on 15/03/12.
 */
public class Todo {

    private int colorLabel;

    private long createdTime;

    private String value;

    public static interface ColorLabel {

        public static final int NONE = 1;
        public static final int PINK = 2;
        public static final int INDIGO = 3;
        public static final int GREEN = 4;
        public static final int AMBER = 5;
    }

    public Todo(int colorLabel, String value, long createdTime) {
        this.colorLabel = colorLabel;
        this.value = value;
        this.createdTime = createdTime;
    }

    public int getColorLabel() {
        return colorLabel;
    }

    public void setColorLabel(int colorLabel) {
        this.colorLabel = colorLabel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * テスト表示用にダミーのリストアイテムを作成.
     */
    public static List<Todo> addDummyItem() {
        List<Todo> items = new ArrayList<>();
        items.add(new Todo(Todo.ColorLabel.INDIGO,
                "100回叩くと壊れる壁があったとする。でもみんな何回叩けば壊れるかわからないから、99回まで来ていても途中であきらめてしまう。",
                System.currentTimeMillis() + 1));
        items.add(new Todo(Todo.ColorLabel.PINK,
                "失敗？これはうまくいかないということを確認した成功だよ。",
                System.currentTimeMillis() + 2));
        items.add(new Todo(Todo.ColorLabel.GREEN,
                "つらいのは、頑張っているから。迷っているのは、進もうとしているから",
                System.currentTimeMillis() + 3));
        items.add(new Todo(Todo.ColorLabel.AMBER,
                "自分をダメだと思えば、その時点から自分はダメになる",
                System.currentTimeMillis() + 4));
        items.add(new Todo(Todo.ColorLabel.NONE,
                "本当に転がった者は、起き上がる時になにか得をしている。",
                System.currentTimeMillis() + 5));
        return items;
    }

}

package com.yokmama.learn10.chapter08.lesson35;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "greendao");
        Entity memo = schema.addEntity("Memo");
        memo.addIdProperty();
        memo.addStringProperty("text");
        memo.addLongProperty("date");
        new DaoGenerator().generateAll(schema, args[0]);
    }
}

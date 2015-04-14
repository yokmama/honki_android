package com.yokmama.learn10.chapter06.lesson29.storage;

import java.io.File;

/**
 * Created by kayo on 15/04/14.
 */
public class FileUtils {
    /** ファイルを再帰的に削除 */
    public static void delete(File root) {
        if (!root.exists()) {
            return;
        }
        if (root.isFile()) {
            if (root.exists() && !root.delete()) {
                root.deleteOnExit();
            }
        } else {
            File[] list = root.listFiles();
            for (int i = 0; i < list.length; i++) {
                delete(list[i]);
            }
            if (root.exists() && !root.delete()) {
                root.deleteOnExit();
            }
        }
    }
}

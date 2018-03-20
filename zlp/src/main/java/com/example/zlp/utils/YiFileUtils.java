package com.example.zlp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class YiFileUtils {
    public static final String FILE_SUFFIX = ".ik";

    private static String mStorePath = null;
    private static Context mContext = null;

    private YiFileUtils() {
    }

    public static void register(Context context) {
        mContext = context;
    }

    /**
     * 缓存数据的存放位置为:/sdcard
     * 如果SD卡不存在时缓存存放位置为:/data/data/
     *
     * @return
     */
    public static String getStorePath() {
        if (mStorePath == null) {
            String path = Environment.getExternalStorageDirectory().getPath();
            if (path == null
                    || !Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) { // 部分手机没有sd卡会报空指针
                    path = mContext.getFilesDir().getPath();
                }
            }

            if (!path.endsWith("/")) {
                path = path + "/";
            }
            mStorePath = path;
        }
        return mStorePath;
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        deleteFile(file2);
                    } else {
                        file2.deleteOnExit();
                    }
                }
            }
            file.deleteOnExit();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存Bitmap到sdcard
     *
     * @param b
     */
    public static String saveBitmap(Bitmap b) {
        String path = getStorePath();
        long dataTake = System.currentTimeMillis();
        String jpegName = path + "/" + dataTake + ".jpg";
        FileOutputStream fout = null;
        BufferedOutputStream bos = null;
        try {
            fout = new FileOutputStream(jpegName);
            bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "";
    }

}

package com.example.zlp.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.zlp.R;
import com.example.zlp.constans.FileConstant;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;


/**
 * 图片裁剪
 */
public class UCropUtil {

    /**
     * 裁剪默认使用1：1
     *
     * @param activity
     * @param path
     */
    public static void startUCropActivity(Activity activity, String path) {
        startUCropActivity(activity, path, 1, 1, FileConstant.CACHE_DIR_CROP);
    }

    public static void startUCropActivity(Activity activity, String path, Integer xRatio, Integer yRatio) {
        startUCropActivity(activity, path, xRatio, yRatio, FileConstant.CACHE_DIR_CROP);
    }

    /**
     * @param activity
     * @param path     路径
     * @param xRatio   比例x
     * @param yRatio   比例y
     */
    public static void startUCropActivity(Activity activity, String path, Integer xRatio, Integer yRatio, String savalocalPath) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        String savePath = savalocalPath + System.currentTimeMillis() + "-CROP_IMAGE.jpeg";
        File saveFile = new File(savePath);

        Uri saveUri = Uri.fromFile(saveFile);

        UCrop uCrop = UCrop.of(uri, saveUri);
        if (xRatio != null && yRatio != null) {
            uCrop = uCrop.withAspectRatio(xRatio, yRatio);
        }
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.setToolbarColor(ContextCompat.getColor(activity, R.color.color_white));
        options.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_white));
        options.setActiveWidgetColor(ContextCompat.getColor(activity, R.color.color_white));
        options.setToolbarTitleTextColor(ContextCompat.getColor(activity, R.color.color_black));
        uCrop.withOptions(options);
        uCrop.start(activity);
    }

    /**
     * 错误提示
     *
     * @param activity
     * @param result
     */
    public static void handleCropError(Activity activity, Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(activity, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "Unexpected error", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 启动裁剪
     * @param activity 上下文
     * @param sourceFilePath 需要裁剪图片的绝对路径
     * @param requestCode 比如：UCrop.REQUEST_CROP
     * @param aspectRatioX 裁剪图片宽高比
     * @param aspectRatioY 裁剪图片宽高比
     * @return
     */
    public static String startUCrop(Activity activity, String sourceFilePath,
                                    int requestCode, float aspectRatioX, float aspectRatioY) {
        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, System.currentTimeMillis() + "-CROP_IMAGE.jpeg");
        //裁剪后图片的绝对路径
        String cameraScalePath = outFile.getAbsolutePath();
        Uri destinationUri = Uri.fromFile(outFile);
        //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.setToolbarColor(ContextCompat.getColor(activity, R.color.color_white));
        options.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_white));
        options.setActiveWidgetColor(ContextCompat.getColor(activity, R.color.color_white));
        options.setToolbarTitleTextColor(ContextCompat.getColor(activity, R.color.color_black));
        uCrop.withOptions(options);
        //设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(aspectRatioX, aspectRatioY);
        //uCrop.useSourceImageAspectRatio();
        //跳转裁剪页面
        uCrop.start(activity, requestCode);
        return cameraScalePath;
    }
}

package com.example.zlp.constans;


import com.example.zlp.utils.YiFileUtils;

import java.io.File;

/**
 * 文件保存路径
 */
public class FileConstant {
    static {
        File file = new File(YiFileUtils.getStorePath() + "fenqibei/ucrop/");
        if (!file.exists()) {
            file.mkdir();
        }

    }
    public static String CACHE_DIR_CROP = YiFileUtils.getStorePath() + "fenqibei/ucrop/"; //

}

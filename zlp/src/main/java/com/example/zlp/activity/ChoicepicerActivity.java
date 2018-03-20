package com.example.zlp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.zlp.R;
import com.example.zlp.utils.UCropUtil;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class ChoicepicerActivity extends Activity {

    private final static int REQUEST_PHOTO_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choicepicer);

        ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ChoicepicerActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_PHOTO_PICK_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("YiLog", "requestcode:" + requestCode + ",resultcode:" + resultCode + ",data:" + data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PHOTO_PICK_CODE:
                    if (data != null) {
                        ArrayList<String> photos =
                                data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                        UCropUtil.startUCrop(this, photos.get(0), UCrop.REQUEST_CROP, 1, 1);
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    Log.d("YiLog", "data:" + data);
                    final Uri resultUri = UCrop.getOutput(data);
                    Log.d("YiLog", "path:" + resultUri.getPath());
                    break;
            }

        }
    }

    //长宽压缩

    /**
     * 计算samplesize
     *
     * @param options
     * @param height
     * @param width
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int height, int width) {
        int reHeitht = options.outHeight;
        int reWidth = options.outWidth;
        int sampleSize = 1;

        if (reHeitht > height || reWidth > width) {

            int halfHeight = reHeitht / 2;
            int halfWidth = reWidth / 2;

            while ((halfHeight / sampleSize) >= height && (halfWidth / sampleSize) >= width) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int resHeight, int resWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, resHeight, resWidth);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);

    }


    //质量压缩

    /**
     * @param bitmap    待压缩图片
     * @param limitSize 限制大小
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap, long limitSize) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);

        while (bos.toByteArray().length / 1024 > limitSize) {
            //清空bos
            bos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            quality -= 10;
        }
        Bitmap b = BitmapFactory.decodeStream(new ByteArrayInputStream(bos.toByteArray()), null, null);
        return b;
    }

}

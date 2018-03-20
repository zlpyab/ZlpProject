package com.example.zlp.wight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by zlp on 2017/5/8.
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {

    private static final String TAG = "CameraSurfaceView";

    private Context mContext;
    private SurfaceHolder holder;
    private Camera mCamera;

    private int mScreenWidth;
    private int mScreenHeight;


    public CameraSurfaceView(Context context) {
        this(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getScreenMatrix(context);
        initView();
    }


    private void getScreenMatrix(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;
    }

    private void initView() {
        holder = getHolder();//获得surfaceholder引用
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置类型
        holder.setKeepScreenOn(true);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        if (mCamera == null) {
            mCamera = Camera.open();//开启相机
            try {
                mCamera.setPreviewDisplay(holder);//摄像头画面显示在surface上
                mCamera.setDisplayOrientation(getPriviewDegree((Activity) mContext));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
        setCameraParams(mCamera, mScreenWidth, mScreenHeight);
        mCamera.startPreview();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        mCamera.stopPreview();//停止预览
        mCamera.release();//释放相机资源
        mCamera = null;
        holder = null;
    }


    private void setCameraParams(Camera mCamera, int width, int height) {
        Log.d(TAG, "setCameraParams");
        Camera.Parameters parameters = mCamera.getParameters();
        //获取摄像头支持的pictureSize列表
        List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
        for (Camera.Size size : pictureSizeList) {
            Log.d(TAG, "pictureSize size.width:" + size.width + ",size,height:" + size.height);
        }
        //从列表中选取合适的分辨率
        Camera.Size picSize = getProperSize(pictureSizeList, ((float) height / width));
        if (null == picSize) {
            picSize = parameters.getPictureSize();
        }
        Log.d(TAG, "picSize.width=" + picSize.width + "  picSize.height=" + picSize.height);
        //根据选出的picSize 重新设置surfaceview的大小
        float w = picSize.width;
        float h = picSize.height;
        parameters.setPictureSize(picSize.width, picSize.height);
        this.setLayoutParams(new FrameLayout.LayoutParams((int) (height * (h / w)), height));

        // 获取摄像头支持的PreviewSize列表
        List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : previewSizeList) {
            Log.d(TAG, "previewSizeList size.width:" + size.width + ",size.height:" + size.height);
        }
        Camera.Size preSize = getProperSize(previewSizeList, ((float) height / width));
        if (null != preSize) {
            Log.d(TAG, "preSize.width=" + preSize.width + "  preSize.height=" + preSize.height);
            parameters.setPreviewSize(preSize.width, preSize.height);
        }

        //设置照片质量
        parameters.setJpegQuality(100);
        parameters.setPreviewFrameRate(5);  //设置每秒显示4帧

        if (parameters.getSupportedFocusModes().contains(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
        }

       // mCamera.cancelAutoFocus();//自动对焦。
        // mCamera.setDisplayOrientation(90);// 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
        mCamera.setParameters(parameters);

    }


    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     * h对应屏幕的width<p/>
     */
    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Log.d(TAG, "screenRatio=" + screenRatio);
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.height / size.width);
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }
        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float currentRatio = ((float) size.height / size.width);
                if (currentRatio == 4f / 3) ;
                {
                    result = size;
                }
            }
        }
        return result;
    }


    // 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
    public static int getPriviewDegree(Activity activity) {
        //获得手机方向
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            Log.d(TAG, "success");
        }
    }

    //拍照瞬间调用
    private Camera.ShutterCallback shutter = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            Log.d(TAG, "shutter");
        }
    };

    // 获得没有压缩过的图片数据
    private Camera.PictureCallback raw = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "raw");
        }
    };

    //创建jpeg图片回调数据对象
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            BufferedOutputStream bos = null;
            Bitmap bitmap = null;
            try {
                //获得图片
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Log.i(TAG, "Environment.getExternalStorageDirectory()=" + Environment.getExternalStorageDirectory());
                    String filePath = "/sdcard/zlp" + System.currentTimeMillis() + ".jpg";//保存图片路径
                    Toast.makeText(mContext,"拍照成功",Toast.LENGTH_SHORT).show();
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);//将图片压缩到流中
                }else {
                    Toast.makeText(mContext,"没有检测到内存卡",Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    bos.flush();//输出
                    bos.close();//关闭
                    bitmap.recycle();//回收bitmap空间
                    mCamera.stopPreview();//关闭预览
                    mCamera.startPreview();//开启预览
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void takePicture(){
        //设置参数并拍照
        setCameraParams(mCamera,mScreenWidth,mScreenHeight);
        // 当调用camera.takePiture方法后，camera关闭了预览，这时需要调用startPreview()来重新开启预览
        mCamera.takePicture(null,null,pictureCallback);
    }
}

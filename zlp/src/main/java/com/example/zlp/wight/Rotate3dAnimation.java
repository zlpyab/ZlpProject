package com.example.zlp.wight;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by zlp on 2017/5/5.
 */
public class Rotate3dAnimation extends Animation {

    private Context context;
    private float mformDegress;
    private float mtoDegress;
    private float mCenterX;
    private float mCenterY;
    private float mDepthz;
    private boolean mRevers;
    private Camera mcamera;

    float scale = 1; //像素密度

    /**
     * 创建一个绕y轴旋转的3D动画效果，旋转过程中具有深度调节，可以指定旋转中心。
     *
     * @param context     <------- 添加上下文,为获取像素密度准备
     * @param formDegress 起始时角度
     * @param toDegress   结束时角度
     * @param mCenterX    旋转中心x坐标
     * @param mCenterY    旋转中心y坐标
     * @param mDepthz     最远到达的z轴坐标
     * @param mRevers     true 表示由从0到depthZ，false相反
     */
    public Rotate3dAnimation(Context context, float formDegress, float toDegress, float mCenterX, float mCenterY, float mDepthz, boolean mRevers) {
        this.context = context;
        this.mformDegress = formDegress;
        this.mtoDegress = toDegress;
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
        this.mDepthz = mDepthz;
        this.mRevers = mRevers;

        // 获取手机像素密度 （即dp与px的比例）
        scale = context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mcamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        float fromDegress = mformDegress;
        float degrees = fromDegress + ((mtoDegress - fromDegress) * interpolatedTime);
        float centerX = mCenterX;
        float centerY = mCenterY;
        Camera camera = mcamera;
        Matrix matrix = t.getMatrix();
        camera.save();

        //调节深度
        if (mRevers){
            camera.translate(0.0f,0.0f,mDepthz * interpolatedTime);
        }else {
            camera.translate(0.0f,0.0f,(1.0f - mDepthz * interpolatedTime));
        }

        // 绕y轴旋转
        camera.rotateY(degrees);

        camera.getMatrix(matrix);
        camera.restore();

        // 修正失真，主要修改 MPERSP_0 和 MPERSP_1

        float[] valuse = new float[9]; //{0,1,12,22,22,54,45,13,52}
        matrix.getValues(valuse);
        valuse[6] = valuse[6]/scale;
        valuse[7] = valuse[7]/scale;
        matrix.setValues(valuse);

        //调节中心点
        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);
    }
}

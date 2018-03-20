package com.example.zlp.activity;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zlp.R;
import com.example.zlp.adapter.ZoomOutPageTransformer;

public class ViewPagerSlideActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_slide);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

    }


    private class MyPagerAdapter extends PagerAdapter {

        private int[] images = {R.mipmap.ic_guide1, R.mipmap.ic_guide2,
                R.mipmap.ic_guide3, R.mipmap.ic_guide4};

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(ViewPagerSlideActivity.this);
            imageView.setImageResource(images[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
}

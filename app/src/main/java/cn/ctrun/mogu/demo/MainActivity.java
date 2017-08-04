package cn.ctrun.mogu.demo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if (actionBarToolbar != null) {
            setSupportActionBar(actionBarToolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        private ViewGroup.LayoutParams params;
        private int diffHeight;//二张图片之间的高度差

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if (position < adapter.getCount() - 1) {
                diffHeight = imageInfoArray[position + 1].height - imageInfoArray[position].height;

                params = viewPager.getLayoutParams();
                params.height = imageInfoArray[position].height + (int) (diffHeight * positionOffset);
                viewPager.requestLayout();
            }
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                params = viewPager.getLayoutParams();
                params.height = imageInfoArray[viewPager.getCurrentItem()].height;
                viewPager.setLayoutParams(params);
            }
        }
    };

    private static final ImageInfo[] imageInfoArray = {
            new ImageInfo(R.mipmap.image1, 933),
            new ImageInfo(R.mipmap.image2, 781),
            new ImageInfo(R.mipmap.image3, 977)};

    PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return imageInfoArray == null ? 0 : imageInfoArray.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);

            imageView.setImageResource(imageInfoArray[position].resId);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    static class ImageInfo {
        int resId;
        int height;

        public ImageInfo(int resId, int height) {
            this.resId = resId;
            this.height = height;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

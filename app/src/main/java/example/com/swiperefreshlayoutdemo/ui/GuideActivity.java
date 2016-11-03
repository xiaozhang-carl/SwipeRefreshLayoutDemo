package example.com.swiperefreshlayoutdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import example.com.swiperefreshlayoutdemo.R;
import example.com.swiperefreshlayoutdemo.databinding.ActivityGuideBinding;

/**
 * Created by zhanghongqiang on 2016/11/2  下午4:22
 * ToDo:
 */
public class GuideActivity extends BaseActivity {

    private ActivityGuideBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_guide);

    }

    public void RecylerViewActivity(View view) {
        startActivity(RecylerViewActivity.class);
    }

    public void SwipeRefreshLayoutActivity(View view) {
        startActivity(SwipeRefreshLayoutActivity.class);
    }
}
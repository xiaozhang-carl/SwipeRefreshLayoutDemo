package example.com.swiperefreshlayoutdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import example.com.swiperefreshlayoutdemo.R;
import example.com.swiperefreshlayoutdemo.databinding.ActivitySwipeRefreshLayoutBinding;
import example.com.swiperefreshlayoutdemo.viewmodel.SwpieViewModel;

/**
 * Created by zhanghongqiang on 2016/11/2  下午4:26
 * ToDo:
 */
public class SwipeRefreshLayoutActivity extends BaseNetActivity {

    private ActivitySwipeRefreshLayoutBinding mBinding;

    private SwpieViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_swipe_refresh_layout);
        mViewModel = new SwpieViewModel();
        mViewModel.text.set("下拉刷新");
        mBinding.setViewModel(mViewModel);
//        mBinding.swipeRefreshLayout.setRefreshing(true);
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新5秒
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
        });

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            mBinding.
            mViewModel.text.set("刷新完成");
            //刷新完成后停止刷新
            mBinding.swipeRefreshLayout.setRefreshing(false);
        }
    };


}
package example.com.swiperefreshlayoutdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import example.com.swiperefreshlayoutdemo.ActivityManager;
import example.com.swiperefreshlayoutdemo.net.ProgressCancelListener;
import example.com.swiperefreshlayoutdemo.net.ProgressDialogHandler;

/**
 * Created by zhanghongqiang on 2016/11/4  下午4:08
 * ToDo:activity的基类
 */
public class BaseActivity extends FragmentActivity implements ProgressCancelListener {
    //标题返回按钮
    private View view;

    //显示进度条对话
    private ProgressDialogHandler mProgressDialogHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加入任务栈
        ActivityManager.getInstance().addActivity(this);
        //进度对话框
        mProgressDialogHandler = new ProgressDialogHandler(this, this, true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        view = findViewById(R.id.toolbar_back);
//        if (view != null) {
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clickToolbarBack();
//                }
//            });
//        }
    }

    public void clickToolbarBack() {
        finish();
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }


    public void showLoading() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    public void hideLoading() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onCancelProgress() {

    }
}

package example.com.swiperefreshlayoutdemo.ui;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhanghongqiang on 2016/11/4  下午4:08
 * ToDo:网络访问的activity的基类
 */
public abstract class BaseNetActivity extends BaseActivity {

    //rxjava访问的Subscription集合
    public CompositeSubscription pendingSubscriptions = new CompositeSubscription();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pendingSubscriptions.clear();
    }
}

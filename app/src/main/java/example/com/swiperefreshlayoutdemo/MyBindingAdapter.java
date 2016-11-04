package example.com.swiperefreshlayoutdemo;

import android.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import example.com.swiperefreshlayoutdemo.utils.Config;


/**
 * Created by liangyaotian on 1/20/16.
 */
public final class MyBindingAdapter {

    /**
     * 显示图片
     * xml写法:app:url="@{url}"
     *
     * @param simpleDraweeView fresco的图片
     * @param url              图片地址
     */
    @BindingAdapter({"url"})
    public static void showloadImage(SimpleDraweeView simpleDraweeView, String url) {
        url = "http://img.hb.aicdn.com/3d0f381c5017acc6a90d8e056fee35f28bcc3db087f09-QucLwn_fw658";
        Config.getImageLoader().displayImage(simpleDraweeView, url);
    }

}

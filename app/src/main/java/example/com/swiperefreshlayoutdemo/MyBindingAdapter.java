package example.com.swiperefreshlayoutdemo;

import android.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import example.com.swiperefreshlayoutdemo.utils.Config;
import me.relex.photodraweeview.PhotoDraweeView;


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
        Config.getImageLoader().displayImage(simpleDraweeView, url);
    }


    @BindingAdapter({"zoomUrl"})
    public static void showloadImage(PhotoDraweeView photoDraweeView, String url) {
        Config.getImageLoader().displayImage(photoDraweeView, url);
    }
}

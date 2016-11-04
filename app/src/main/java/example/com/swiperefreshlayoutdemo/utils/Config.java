package example.com.swiperefreshlayoutdemo.utils;

import android.content.Context;

/**
 * Created by zhanghongqiang on 16/7/4  上午9:54
 * ToDo:图片加载配置
 */
public class Config {


    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader() {
        if (imageLoader == null) {
            synchronized (Config.class) {
                if (imageLoader == null) {
                    throw new RuntimeException("请调用SibuConfig.init方法初始化，并传入ImageLoader接口的实现类");
                }
            }
        }
        return imageLoader;
    }

    public static void init(Context context, ImageLoader imgLoader) {
        imageLoader = imgLoader;
    }
}

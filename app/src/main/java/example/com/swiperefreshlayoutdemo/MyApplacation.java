package example.com.swiperefreshlayoutdemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import example.com.swiperefreshlayoutdemo.utils.Config;
import example.com.swiperefreshlayoutdemo.utils.ImageLoader;
import okhttp3.OkHttpClient;

/**
 * Created by zhanghongqiang on 16/5/5  上午9:37
 * ToDo:
 */
public class MyApplacation extends Application {


    private static MyApplacation application;


    public static MyApplacation getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initFresco();
    }

    private void initFresco() {
        Config.init(this, new ImageLoader());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
//                            .addNetworkInterceptor(new StethoInterceptor())
                .build(); // build on your own
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, okHttpClient)
                .build();
        Fresco.initialize(this, config);
    }
}

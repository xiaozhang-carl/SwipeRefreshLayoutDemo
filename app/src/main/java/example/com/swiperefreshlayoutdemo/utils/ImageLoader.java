package example.com.swiperefreshlayoutdemo.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import example.com.swiperefreshlayoutdemo.MyApplacation;
import example.com.swiperefreshlayoutdemo.R;

/**
 * Created by zhanghongqiang on 16/7/4  上午9:53
 * ToDo:图片加载的接口
 */
public class ImageLoader {


    /**
     * 显示网络图片
     *
     * @param simpleDraweeView
     * @param url
     */

    public void displayImage(SimpleDraweeView simpleDraweeView, String url) {
        displayImage(simpleDraweeView, url, R.mipmap.ic_launcher);
    }


    /**
     * 显示SD卡图片
     *
     * @param simpleDraweeView
     * @param fileName
     */

    public void displaySDImage(SimpleDraweeView simpleDraweeView, String fileName) {
        String uri = "file://" + fileName;
        //File资源加载
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .build();

        simpleDraweeView.setController(controller);
    }

    /**
     * 显示res图片   http://www.jianshu.com/p/bb32bca8796b
     *
     * @param simpleDraweeView
     * @param resId
     */

    public void displayResImage(SimpleDraweeView simpleDraweeView, Integer resId) {

        String uri = "res:///" + resId;
        //res资源加载
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .build();

        simpleDraweeView.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(resId)).build());
        ;
    }

    /**
     * 显示网络图片
     *
     * @param simpleDraweeView
     * @param url
     * @param placeholderResID
     */

    public void displayImage(SimpleDraweeView simpleDraweeView, String url, Integer placeholderResID) {
        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(simpleDraweeView.getContext().getResources())
                //设置占位图 R.drawable.img_square_avatar
                .setPlaceholderImage(ContextCompat.getDrawable(simpleDraweeView.getContext(), placeholderResID))
                //占位图缩放类型是 CENTER_CROP
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                //构建
                .build();
        //设置GenericDraweeHierarchy
        simpleDraweeView.setHierarchy(hierarchy);
        //
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .build();
        //设置DraweeController
        simpleDraweeView.setController(controller);

    }

    /**
     * 分享用，查看图片用
     *
     * @param path
     * @param bitmapListener
     */
    public void getBitmap(String path, final SibuBitmapListener bitmapListener) {

        if (TextUtils.isEmpty(path)) {
            return;
        }

        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(path))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, MyApplacation.getInstance());

        dataSource.subscribe(new BaseBitmapDataSubscriber() {

                                 @Override
                                 public void onNewResultImpl(Bitmap bitmap) {
                                     // You can use the bitmap in only limited ways
                                     // No need to do any cleanup.
                                     if (bitmapListener != null)
                                         //可以优化
                                         bitmapListener.onSuccess(bitmap);
                                 }

                                 @Override
                                 public void onFailureImpl(DataSource dataSource) {
                                     // No cleanup required here.
                                 }
                             },
                CallerThreadExecutor.getInstance());

    }

    public interface SibuBitmapListener {
        void onSuccess(Bitmap bitmap);
    }

}
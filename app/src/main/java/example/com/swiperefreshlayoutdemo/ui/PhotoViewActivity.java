package example.com.swiperefreshlayoutdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import example.com.swiperefreshlayoutdemo.R;
import example.com.swiperefreshlayoutdemo.databinding.ActivityPhotoViewBinding;
import example.com.swiperefreshlayoutdemo.utils.Config;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnViewTapListener;

/**
 * Created by zhanghongqiang on 2016/11/4  下午4:43
 * ToDo:
 */
public class PhotoViewActivity extends BaseActivity {

    private ActivityPhotoViewBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo_view);

//        mBinding.photoDraweeView.setPhotoUri(Uri.parse("res:///" + R.drawable.panda));
//        http://img.hb.aicdn.com/3d0f381c5017acc6a90d8e056fee35f28bcc3db087f09-QucLwn_fw658
        Config.getImageLoader().displayImage(mBinding.photoDraweeView, "http://testxws.sibumbg.com:81/G1/M00/00/3A/eBjqJlfPes6AdZ8eAAAL15fn7p0586.jpg");
        //点击事件
        mBinding.photoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Toast.makeText(view.getContext(), "onPhotoTap :  x =  " + x + ";" + " y = " + y,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                Toast.makeText(view.getContext(), "onViewTap", Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.photoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "onLongClick", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
package example.com.swiperefreshlayoutdemo.viewmodel;

import android.databinding.BaseObservable;
import android.os.Bundle;



import org.parceler.Parcel;
import org.parceler.Parcels;

/**
 * Created by liangyaotian on 1/25/16.
 */
@Parcel
public class BaseViewModel extends BaseObservable {

    public BaseViewModel(){

    }
    public static Object create(Bundle savedInstanceState, Class tClass){
        Object result = null;
        if (savedInstanceState != null) {
            result = Parcels.unwrap(
                    savedInstanceState.getParcelable("a"));
        }

        if (result == null){
            try {
                if (tClass != null){
                    return tClass.newInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void save(Bundle outState, Object instance) {
        if (outState != null
                && instance != null){
            outState.putParcelable("a", Parcels.wrap(instance));
        }
    }
}

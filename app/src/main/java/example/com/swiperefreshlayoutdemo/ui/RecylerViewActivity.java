package example.com.swiperefreshlayoutdemo.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.com.swiperefreshlayoutdemo.R;
import example.com.swiperefreshlayoutdemo.api.Api;
import example.com.swiperefreshlayoutdemo.databinding.ActivityRecyclerViewBinding;
import example.com.swiperefreshlayoutdemo.databinding.ItemMovieBinding;
import example.com.swiperefreshlayoutdemo.model.HttpResult;
import example.com.swiperefreshlayoutdemo.model.Movie;
import example.com.swiperefreshlayoutdemo.presenter.RecyclerViewContract;
import example.com.swiperefreshlayoutdemo.presenter.RecyclerViewPresenter;
import example.com.swiperefreshlayoutdemo.subscribers.OnNextOnError;

public class RecylerViewActivity extends BaseActivity implements RecyclerViewContract.IFLoadData, RecyclerViewContract.IFAdapter<Movie> {

    private ActivityRecyclerViewBinding mBinding;

    private RecyclerViewPresenter mRecyclerViewPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);
        View header = getLayoutInflater().inflate(R.layout.list_header_picture, null, false);
        mRecyclerViewPresenter = RecyclerViewPresenter.with(this, this)
                .recyclerView(mBinding.swipeRefreshLayout, mBinding.recyclerView)
                .setHeaderView(header)
                .build();
        mRecyclerViewPresenter.reLoadData();
    }


    @Override
    public int getViewType(int position) {
        return 0;
    }


    @Override
    public void loadData() {
        pendingSubscriptions.add(Api.toSubscribe(Api.getInstance().getRest().getTopMovie(
                mRecyclerViewPresenter.nextPage()
                , 10)
                , new OnNextOnError<HttpResult<List<Movie>>>() {


                    @Override
                    public void onNext(HttpResult<List<Movie>> listHttpResult) {
                        mRecyclerViewPresenter.add(listHttpResult.getSubjects());
                    }



                    @Override
                    public void onError(Throwable e) {
                        mRecyclerViewPresenter.refreshComplete();
                    }
                }));
    }


    @Override
    public void setData(@NonNull final Movie data, @NonNull ViewDataBinding binding) {
        ItemMovieBinding b = (ItemMovieBinding) binding;
        b.setMovie(data);

        b.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = recyclerViewPresenter.indexOf(data);
//                recyclerViewPresenter.notifyItemRangeRemoved(position);
//                mRecyclerViewPresenter.notifyItemRangeInserted(0, data);
//                (Movie)mRecyclerViewPresenter.getDataList().get(0).
                data.setTitle("notifyItemChanged");
                int position = mRecyclerViewPresenter.indexOf(data);
//                mRecyclerViewPresenter.notifyItemChanged(position);
                mRecyclerViewPresenter.notifyItemRangeRemoved(position);
            }
        });

    }

    @Override
    public ViewDataBinding createView(ViewGroup parent, int viewType) {
        return DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_movie, parent, false);
    }
}

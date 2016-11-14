package example.com.swiperefreshlayoutdemo.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;

import example.com.swiperefreshlayoutdemo.R;
import example.com.swiperefreshlayoutdemo.widget.HeaderViewRecyclerAdapter;
import example.com.swiperefreshlayoutdemo.widget.LoadingMoreView;


/**
 * Created by zhanghongqiang on 2016/10/31  下午2:38
 * ToDo:列表的数据加载
 */
public class RecyclerViewPresenter<T> extends RecyclerViewContract.RVPresenter {

    private String TAG = "123";

    //谷歌下拉刷新的布局
    private SwipeRefreshLayout mSwipeLayout;
    //列表
    private RecyclerView mRecyclerView;
    //列表头部
    private View mHeaderView;
    //列表底部
    private View mFooterView;
    //加载更多的布局
    private View mLoadingView;
    //列表空布局的现实
    private View mEmptyView;
    //万用的适配器
    private RVAdapter mAdapter;
    //是否在加载数据中
    private boolean mLoading = false;
    //列表最后一个
    private int mLastVisibleItemPosition;
    //包装adpter的适配器
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    //分页,从0开始
    private int mPage = 0;
    //每一页的item个数,默认20条
    private int mCount = 5;

    public RecyclerViewPresenter(RecyclerViewContract.IFLoadData loadData, RecyclerViewContract.IFAdapter adapter) {
        super(loadData, adapter);
    }

    /**
     * @return
     */
    public static RecyclerViewPresenter with(RecyclerViewContract.IFLoadData L, RecyclerViewContract.IFAdapter F) {
        return new RecyclerViewPresenter(L, F);
    }

    /**
     * 默认的单行列表竖向显示
     *
     * @param swipeRefreshLayout
     * @param recyclerView
     * @return
     */
    public RecyclerViewPresenter recyclerView(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView) {
        this.mSwipeLayout = swipeRefreshLayout;
        this.mRecyclerView = recyclerView;
        initSwipeRefreshLayout();
        initLinearLayoutManager();
        return this;
    }

    /**
     * 网格布局
     *
     * @param swipeRefreshLayout
     * @param recyclerView
     * @param spanCount          网格布局的格数
     * @return
     */
    public RecyclerViewPresenter recyclerView(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, int spanCount) {
        this.mSwipeLayout = swipeRefreshLayout;
        this.mRecyclerView = recyclerView;
        initSwipeRefreshLayout();
        initGridLayoutManager(spanCount);
        return this;
    }

    private void initLinearLayoutManager() {
        //列表布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        //设置layoutManager
        mRecyclerView.setLayoutManager(layoutManager);
    }


    private void initGridLayoutManager(int spanCount) {
        //列表布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(mRecyclerView.getContext(), spanCount);
        //设置layoutManager
        mRecyclerView.setLayoutManager(layoutManager);
    }


    //https://github.com/hanks-zyh/SwipeRefreshLayout
    private void initSwipeRefreshLayout() {
        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        mSwipeLayout.setProgressViewOffset(true, 50, 200);

        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // 通过 setEnabled(false) 禁用下拉刷新
//        mSwipeLayout.setEnabled(false);
        // 设定下拉圆圈的背景
//        mSwipeLayout.setProgressBackgroundColorSchemeResource(R.color.cardview_dark_background);
        //设置手势下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //下拉刷新使用
                mPage = 0;
                //初次加载，加载更多的不显示
                if (mLoadingView instanceof LoadingMoreView) {
                    LoadingMoreView loadingView = (LoadingMoreView) mLoadingView;
                    loadingView.setStatus(LoadingMoreView.STATUS_INIT);
                } else {
                    mLoadingView.setVisibility(View.INVISIBLE);
                }
                //网络请求数据
                if (mLoadData != null) {
                    mLoadData.loadData();
                }
            }
        });
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    /**
     * 设置列表加载的布局
     *
     * @param loadingView 自定义的列表底部加载布局
     * @return
     */
    public RecyclerViewPresenter setLoadingView(View loadingView) {
        mLoadingView = loadingView;
        return this;
    }

    /**
     * 设置列表的头部
     *
     * @param headerView
     * @return
     */
    public RecyclerViewPresenter setHeaderView(View headerView) {
        mHeaderView = headerView;
        return this;
    }

    /**
     * 设置列表加载的底部
     *
     * @param footerView
     * @return
     */
    public RecyclerViewPresenter setFooterView(View footerView) {
        mFooterView = footerView;
        return this;
    }

    public RecyclerViewPresenter build() {


        mAdapter = new RVAdapter();

        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        //添加头部
        if (mHeaderView != null) {
            mHeaderViewRecyclerAdapter.setHeaderView(mHeaderView);
        }
        //添加尾部
        if (this.mFooterView != null) {
            mHeaderViewRecyclerAdapter.setFooterView(mFooterView);
        }
        //加载更多的样式
        if (mLoadingView == null) {
            //默认的样式
            mLoadingView = new LoadingMoreView(mRecyclerView.getContext());
            mHeaderViewRecyclerAdapter.setLoadingView(mLoadingView);
        } else {
            mHeaderViewRecyclerAdapter.setLoadingView(mLoadingView);
        }

        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        return this;
    }


    /**
     * 下一页,获取数据的时候调用
     *
     * @return
     */
    public int nextPage() {
        return ++mPage;
    }

    /**
     * 下一页,获取当前的分页
     *
     * @return
     */
    public int getPage() {
        return mPage;
    }


    /**
     * 下一页,获取列表数据源
     *
     * @return
     */
    public List<T> getDataList() {
        return mAdapter.mDatas;
    }

    /**
     * 返回数据在列表中的位置position
     *
     * @param t item的单行数据源
     * @return
     */
    public int indexOf(T t) {
        return getDataList().indexOf(t);
    }

    /**
     * 返回每页的条数
     *
     * @return
     */
    public int getCount() {
        return mCount;
    }

    /**
     * 重新加载数据
     */
    @Override
    public void reLoadData() {
        //页面一定要设置为初始页
        mPage = 0;
        mSwipeLayout.setRefreshing(true);
        //网络请求数据
        if (mLoadData != null) {
            mLoadData.loadData();
        }
    }

    @Override
    public void add(List list) {
        //下拉刷新,多次请求首页的话,清空数据
        if (mPage == 1 || mPage == 0) {
            //第一页就没有数据,显示空数据
            if (list.size() == 0) {
                //刷新完成,隐藏进度条...
                refreshComplete();
                clearData();
                showEmptyView();
                return;
            } else {
                //隐藏占位图
                hideEmptyView();
                //刷新完成,隐藏进度条...
                refreshComplete();
                //有数据的话,清空原来的数据,防止数据重复添加。
                clearData();
                getDataList().addAll(list);
                mAdapter.notifyDataSetChanged();
                Log.i(TAG, "mPage =" + mPage + ",refreshComplete()");
                return;
            }
        }
        //隐藏占位图
        hideEmptyView();
        //刷新完成,隐藏进度条...
        refreshComplete();
        //加入新的数据
        //一定要调用这个方法,因为XRecyclerView添加了头部,所以这个position+1
        int position = getDataList().size();
        if (mHeaderView == null) {
            mAdapter.addNewList(position, list);
        } else {
            mAdapter.addNewList(position + 1, list);
        }
        Log.i(TAG, "mPage =" + mPage + ",refreshComplete()");
    }

    //刷新完成,隐藏进度条...
    public void refreshComplete() {
        mSwipeLayout.setRefreshing(false);
        mLoading = false;
        //加载更多的设置
        if (mLoadingView instanceof LoadingMoreView) {
            LoadingMoreView loadingView = (LoadingMoreView) mLoadingView;
            loadingView.setStatus(LoadingMoreView.STATUS_INIT);
        } else {
            mLoadingView.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void clearData() {
        if (mAdapter != null) {
            mAdapter.clearList(0);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (mHeaderViewRecyclerAdapter != null) {
            mHeaderViewRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyItemChanged(int position) {
        if (position < 0) {
            return;
        }
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void notifyItemRangeRemoved(Object o) {

        if (o == null) {
            return;
        }
        T t = (T) o;
        int position = indexOf(t);
        if (mAdapter != null) {
            getDataList().remove(position);
            if (mHeaderView == null) {
                mAdapter.notifyItemRangeRemoved(position, 1);
            } else {
                mAdapter.notifyItemRangeRemoved(position + 1, 1);
            }
        }
    }


    @Override
    public void notifyItemRangeInserted(int position, Object o) {
        if (position < 0) {
            return;
        }
        T t = (T) o;
        if (mAdapter != null) {
            //一定要调用这个方法,因为RecyclerView添加了头部,所以这个position+1
            getDataList().add(position, t);
            mAdapter.notifyItemRangeInserted(position, 1);
            //滚动到添加的item哪里，加强用户体验
            mRecyclerView.scrollToPosition(position + 1);
        }
    }

    /**
     * https://github.com/AlexSmille/alex_mahao_sample
     */
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        //瀑布流使用的
        int[] mLastPostions = null;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            //RecycleView 显示的条目数
            int visibleCount = layoutManager.getChildCount();
            //显示数据总数
            int totalCount = layoutManager.getItemCount();
            //判断RecyclerView的状态

            if (visibleCount > 0
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && mLastVisibleItemPosition >= totalCount - 1
                    && !mLoading && mLoadData != null) {

                mLoading = true;
                //说明需要下拉刷新
                if (getDataList().size() == 0) {
                    reLoadData();
                } else {
                    //加载更多的显示
                    if (mLoadingView instanceof LoadingMoreView && !mSwipeLayout.isRefreshing()) {
                        LoadingMoreView LoadingMoreView = (LoadingMoreView) mLoadingView;
                        LoadingMoreView.setStatus(LoadingMoreView.STATUS_LOADING);
                    } else {
                        mLoadingView.setVisibility(View.VISIBLE);
                    }
                    //加载数据
                    mLoadData.loadData();
                }
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            //判断下列表布局管理器
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                mLastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                LinearLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                mLastVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (mLastPostions == null) {
                    mLastPostions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(mLastPostions);
                mLastVisibleItemPosition = findMax(mLastPostions);
            }

        }

        /**
         * 当是瀑布流时，获取到的是每一个瀑布最下方显示的条目，通过条目进行对比
         */
        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
    };


    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
    }

}

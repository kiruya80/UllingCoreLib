package com.ulling.lib.core.viewutil.recyclerView;

import android.support.v7.widget.RecyclerView;

/**
 * Created by P100651 on 2017-07-03.
 * 리스너를 RecyclerView에 addOnScrollListener로 설정하면 됩니다.
 * OnLoadMore 함수로 리턴 받을 수가 있습니다.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int currentPage = 1;
    RecyclerViewPositionHelper mRecyclerViewHelper;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mRecyclerViewHelper.getItemCount();
        firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            // Do something
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    //Start loading
    public abstract void onLoadMore(int currentPage);




//    private LinearLayoutManager mLayoutManager;
//    //    RecyclerView.LayoutManager mLayoutManager;
//    private Boolean isLastItem = false;
//    private boolean isMoreLoading = false;
//    private boolean hasNextPage = false;
//    private int visibleThreshold = 1;
//    private int firstVisibleItem;
//    private int visibleItemCount;
//    private int totalItemCount;
//
//    public EndlessScrollListener(LinearLayoutManager layoutManager) {
//        this.mLayoutManager = layoutManager;
//    }
//
//    public EndlessScrollListener(GridLayoutManager layoutManager) {
//        this.mLayoutManager = layoutManager;
//    }
////    public EndlessScrollListener(StaggeredGridLayoutManager layoutManager) {
////        this.mLayoutManager = layoutManager;
////    }
//
//    @Override
//    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);
//        visibleItemCount = recyclerView.getChildCount();
//        totalItemCount = mLayoutManager.getItemCount();
//        firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//
//        if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
//            if (hasNextPage) {
//                onLoadMore();
//                isMoreLoading = true;
//            }
//            isLastItem = true;
//        } else {
//            isLastItem = false;
//        }
//    }
//
//    @Override
//    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//        super.onScrollStateChanged(recyclerView, newState);
//        if (isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
//            onLastPage();
//        }
//    }
//
//    // Call this method whenever performing new searches
//    public void resetState() {
//        this.isLastItem = false;
//        this.isMoreLoading = false;
//        this.hasNextPage = false;
//        this.visibleThreshold = 1;
//    }
//
//    public void setMoreLoading(boolean isMoreLoading) {
//        this.isMoreLoading = isMoreLoading;
//    }
//
//    public void setHasNextPage(boolean hasNextPage) {
//        this.hasNextPage = hasNextPage;
//    }
//    // Defines the process for actually loading more data based on page
////    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
//
//    public abstract void onLoadMore();
//
//    public abstract void onLastPage();
}
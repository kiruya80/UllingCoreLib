/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.view;

/**
 * Created by KILHO on 2016. 7. 12..
 */


import android.support.v7.widget.RecyclerView;

/**
 * Custom Scroll listener for RecyclerView. Based on implementation https://gist.github.com/ssinss/e06f12ef66c51252563e
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
//    public String TAG = getClass().getSimpleName();

  private int previousTotal = 0; // The total number of items in the dataset after the last load
  private boolean loading = true; // True if we are still waiting for the last set of data to load.
  private int visibleThreshold = 10; // The minimum amount of items to have below your current scroll position before loading more.
  int firstVisibleItem, visibleItemCount, totalItemCount;

  private int currentPage = 1;

  private RecyclerViewPositionHelper mRecyclerViewHelper;

  public EndlessRecyclerOnScrollListener(int pageLimit) {
    this.visibleThreshold = pageLimit;
  }

  public void setLoading(boolean loading_) {
    this.loading = loading_;
  }

  public void resetScroll() {
    loading = false;
    firstVisibleItem = 0;
    visibleItemCount = 0;
    totalItemCount = 0;
    currentPage = 1;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);
    if (dy < 0)
      return;

    mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
    visibleItemCount = recyclerView.getChildCount();
    totalItemCount = mRecyclerViewHelper.getItemCount();
    firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();

//        if (loading) {
//            if (totalItemCount > previousTotal) {
////                loading = false;
//                previousTotal = totalItemCount;
//            }
//
//        } else {
//            synchronized (this) {
//                if (!last && (totalItemCount - visibleItemCount)
//                        <= (firstVisibleItem + visibleThreshold)) {
////            loading = true;
//                    currentPage++;
//                    onLoadMorePage(currentPage);
//                }
//            }
//        }

    synchronized (this) {
      if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
        // End has been reached, Do something
        currentPage++;
        onLoadMorePage(currentPage);
        loading = true;
      }
    }
  }

  //Start loading
  public abstract void onLoadMorePage(int currentPage);
}
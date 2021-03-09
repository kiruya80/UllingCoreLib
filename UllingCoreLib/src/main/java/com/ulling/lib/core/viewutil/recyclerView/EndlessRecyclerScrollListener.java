package com.ulling.lib.core.viewutil.recyclerView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ulling.lib.core.utils.QcLog;

/**
 * Created by P100651 on 2017-04-27.
 */
public abstract class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * mLayoutManager 에서 가져오는 값들정리
     */
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleItemCount;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    private int spanCount = 0;
    /**
     * 뷰의 네트워크데이터들에 의해 변화될수 있는 값들
     */

    /**
     * Sets the starting page index
     * 변하지 않는 시작 페이지
     * 리스너를 리셋하는 경우에도 불변
     */
    private int startingPageIndex = 1;
    /**
     * The current offset index of data you have loaded
     * 리스트가 마지막으로 도달하는 경우 다음로딩하는 페이지
     * 증가함
     */
    private int currentPage = 1;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = false;
//    private boolean hasNextPage = true;
//    private boolean networkError = false;
    /**
     * 고정영역이 있는 경우
     */
    private int visibleThreshold = 0;
    private int viewStartingPageIndex = 1;
//    private int viewCurrentPage = 1;
    /**
     * 엣지 타입
     */
    public static final int EDGE_TYPE_NONE = 0;
    public static final int EDGE_TYPE_TOP = 1;
    public static final int EDGE_TYPE_BOTTOM = 2;
    private int isEdgetype = EDGE_TYPE_NONE;
    private int pageSize = 1;

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

    public abstract void onPositionBottom();

    public abstract void onPositionTop();


    /**
     * 필요이유
     * 1. 초기셋팅문제 (시작페이지를 몇으로 할것인지 로컬인경우 처리가 필요한지등)
     *
     * 2. 네트워크 로딩 실패 유무에 따른 처리를 위해서
     *
     * 2. 데이터 로딩 실패인경우 처리하기 위해서
     *
     *
     * 리사이클뷰를 가진 프레그먼트(액티비티)에서
     * 다음 페이지를 가져올때 상태값을 리스너에 저장
     */
    public void onStartingPageIndex(int viewStartingPageIndex_) {
        QcLog.e("onStartingPageIndex =====" + viewStartingPageIndex_);
        viewStartingPageIndex = viewStartingPageIndex_;
    }

    public void setPgeSize(int pageSize) {
        QcLog.e("setPgeSize =====" + pageSize);
        this.pageSize = pageSize;
    }

    /**
     * 데이터 로딩이 실패한 경우
     */
    public void onCurrentPage(int reLoadCurrentPage) {
        QcLog.e("onCurrentPage =====" + reLoadCurrentPage);
        currentPage = reLoadCurrentPage;
    }

    public void onNetworkLoading(boolean loading_) {
        QcLog.e("onNetworkLoading =====" + loading_);
//        loading = loading_;
    }

    public void onNextPage(boolean hasNextPage_) {
        QcLog.e("onNextPage =====" + hasNextPage_);
//        hasNextPage = hasNextPage_;
    }

    public void onResetStatus() {
        resetStatus();
    }

    public void onNetworkError(boolean networkError_) {
        QcLog.e("onNetworkError =====" + networkError_);
//        networkError = networkError_;
    }

    private void resetStatus() {
        startingPageIndex = viewStartingPageIndex;
        currentPage = this.startingPageIndex;
//        networkError = false;
//        loading = false;
//        hasNextPage = true;
    }

    /**
     * 생성자
     */
    public EndlessRecyclerScrollListener(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            spanCount = staggeredGridLayoutManager.getSpanCount();
            this.mLayoutManager = (StaggeredGridLayoutManager) layoutManager;

        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            spanCount = gridLayoutManager.getSpanCount();
            this.mLayoutManager = (GridLayoutManager) layoutManager;

        } else if (layoutManager instanceof LinearLayoutManager) {
            this.mLayoutManager = (LinearLayoutManager) layoutManager;
        }
    }


    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (mLayoutManager == null)
            return;
        /**
         * 화면에서 아래쪽에 보이는 포지션뷰
         * 0부터 시작
         * */
        int lastVisibleItemPosition = 0;
        int firstVisibleItemPosition = 0;
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            int[] firstVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getStaggeredLastVisibleItem(lastVisibleItemPositions);
            firstVisibleItemPosition = getStaggeredFirstVisibleItem(firstVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            firstVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else {
            return;
        }
        /**
         * 화면에 보이는 뷰 갯수
         * spanCount가 있는 경우
         * */
        if (spanCount == 0) {
            visibleItemCount = mLayoutManager.getChildCount();
        } else {
            visibleItemCount = mLayoutManager.getChildCount() * spanCount;
        }
        /**
         * 아이템 총 갯수
         * 아이템이 없는 경우 return;
         */
        int totalItemCount = mLayoutManager.getItemCount();
        if (totalItemCount <= 0) {
            return;
        }
        isEdgetype = EDGE_TYPE_NONE;
//        QcLog.e("lastVisibleItemPosition == " + lastVisibleItemPosition
//                + " ,firstVisibleItemPosition == " + firstVisibleItemPosition
//                + " , visibleItemCount = " + visibleItemCount
//                + " , totalItemCount == " + totalItemCount
//                + " , hasNextPage == " + hasNextPage + " , loading == " + loading + " , networkError == " + networkError);
        /**
         * 화면에 보이는 아이템이 마지막 아이템보다 큰 경우
         * 즉, 화면에 보이는 갯수가 적은 경우는 패스한다
         *
         */
        if (visibleItemCount == totalItemCount && (lastVisibleItemPosition + 1) <= visibleItemCount) {
            QcLog.e("총갯수와 화면에 보이는 갯수가 같고, 화면에 보이는 갯수가 화면 마지막 포지션보다 큰경우 Pass =====");
            return;
        }
        /**
         * If the total item count is zero and the previous isn't, assume the
         * list is invalidated and should be reset back to initial state
         *
         * 이전 아이템 총갯수와 현재 아이템 총 갯수 비교
         *
         * 아이템이 줄어든 경우??
         * 아이템을 삭제하거나 프로그레스바등이 삭제되는 경우
         *
         * 이전 아이템 갯수가 현재 아이템 갯수보다 큰경우
         */
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
//            if (totalItemCount == 0) {
//                this.loading = true;
//            }
        } else if (totalItemCount > (previousTotalItemCount + 1)) {
            /**
             * If it’s still loading, we check to see if the dataset count has
             *  changed, if so we conclude it has finished loading and update the current page
             *  number and total item count.
             *
             * 이전 아이템 갯수가 현재 아이템 갯수보다 작은 경우
             * +페이지 사이즈보다 커지는 경우
             * 작은 경우는 마지막 페이지로 인식?
             * 실패한 경우는
             *
             *
             * 처음과 로딩이 완료된 시점에 호출됨
             *  프로그레스바등이 추가되는 경우
             *
             * 아이템이 늘어난 경우 로딩이 완료되었다고 처리?
             */
            if (this.loading) {
                this.loading = false;
                previousTotalItemCount = totalItemCount;
            }
        }
//        QcLog.e("hasNextPage == " + hasNextPage + " , loading = " + loading+ " , networkError = " + networkError);
        /**
         * If it isn’t currently loading, we check to see if we have breached
         * the visibleThreshold and need to reload more data.
         * If we do need to reload some more data, we execute onLoadMore to fetch the data.
         *  threshold should reflect how many total columns there are too
         *
         * (totalItemCount - visibleItemCount) 남은 보여줄 데이터 갯수
         * (lastVisibleItemPosition + visibleThreshold) 마지막 보여주고 있는 포지션
         *
         * ###### visibleItemCount 갯수만큼 데이터가 남아잇는 경우 미리 데이터를 로딩한다 ######
         *
         */
//        if (hasNextPage && !loading && (lastVisibleItemPosition + visibleItemCount) > totalItemCount) {
//        if (hasNextPage && !networkError && !loading
//                && (totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + visibleThreshold)) {

        if (!loading && (totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + visibleThreshold)) {
            if (pageSize > 1)
                currentPage = (totalItemCount / pageSize);
            currentPage++;
            loading = true;
            QcLog.e("onLoadMore  마지막, 더보기 호출  == " + currentPage);
            onLoadMore(currentPage, totalItemCount, view);
            return;
        }
        /**
         * find end
         * 마지막 끝에 닿는 경우 체크
         * 단, 로딩하고 곂치는 경우 처리를 어떻게 할것인지
         *
         * lastVisibleItemPosition =====29 , visibleThreshold= 5 , totalItemCount= 30
         */
        if (lastVisibleItemPosition > visibleItemCount && lastVisibleItemPosition >= (totalItemCount - 1)) {
            QcLog.e("로딩중이 아닌경우, 마지막일때 == ");
            isEdgetype = EDGE_TYPE_BOTTOM;
            return;
        }

        if (firstVisibleItemPosition == 0) {
            QcLog.e("최상단인 경우  == ");
            isEdgetype = EDGE_TYPE_TOP;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            QcLog.e("isEdgetype == " + isEdgetype);
            if (isEdgetype == EDGE_TYPE_TOP) {
                onPositionTop();
            } else if (isEdgetype == EDGE_TYPE_BOTTOM) {
                onPositionBottom();
            }
        }
    }



    private int getStaggeredLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    private int getStaggeredFirstVisibleItem(int[] lastVisibleItemPositions) {
        int minSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                minSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] < minSize) {
                minSize = lastVisibleItemPositions[i];
            }
        }
        return minSize;
    }



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
//    public abstract void onLoadMore();
//
//    public abstract void onLastPage();
//    // Defines the process for actually loading more data based on page
////    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
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
}
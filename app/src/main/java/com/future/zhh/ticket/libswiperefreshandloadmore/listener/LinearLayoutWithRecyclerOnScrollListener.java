package com.future.zhh.ticket.libswiperefreshandloadmore.listener;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class LinearLayoutWithRecyclerOnScrollListener extends RecyclerOnScrollListener {
    public static String TAG = LinearLayoutWithRecyclerOnScrollListener.class.getSimpleName();


    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLinearLayoutManager;

    public abstract void onLoadMore(int pagination, int pageSize);


    public LinearLayoutWithRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, int pagination, int pageSize) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.pagination = pagination;
        this.pageSize = pageSize;
    }

    public LinearLayoutWithRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!isLoading()) {
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            //totalItemCount > visibleItemCount load more
            if (loadMoreEnable && !loading && totalItemCount > visibleItemCount && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached
                loading = true;
                pagination++;
                onLoadMore(pagination, pageSize);
            }
        }
    }


    public boolean checkCanDoRefresh() {
        //fixed https://github.com/captain-miao/RecyclerViewUtils/issues/5
        if(mLinearLayoutManager.getItemCount() == 0) return true;
        int firstVisiblePosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        if(firstVisiblePosition == 0) {
            View firstVisibleView = mLinearLayoutManager.findViewByPosition(firstVisiblePosition);
            int top = firstVisibleView.getTop();
            return top >= 0;
        } else {
            return false;
        }

        //it's also work.(but I can't test enough)
        //since support library 22.1, suggest using ViewCompat.canScrollVertically()
        //but not recyclerView reference
        //return !ViewCompat.canScrollVertically(recyclerView, -1);
    }


}

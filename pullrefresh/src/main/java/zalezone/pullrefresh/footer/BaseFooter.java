package zalezone.pullrefresh.footer;

import android.view.View;

import zalezone.pullrefresh.content.BaseRecyclerViewAdapter;

/**
 * Created by zale on 2017/2/14.
 */

public abstract class BaseFooter {

    public boolean loading = false;
    public boolean hasMoreData = true;
    public int currentTotal = 0;
    public int currentPage = 1;

    public boolean isEnable = true;


    public abstract View getContainerView();
    protected abstract void setNoMoreDataView();
    protected abstract void setHasMoreDataView();
    protected abstract void setLoadMoreErrorView();
    protected abstract void hideLoadMoreView();
    protected abstract void showLoadMoreView();

    public void setState(int newDataSize,int currentPage){

        if (!checkIsEnable()){
            return;
        }
        if (currentPage == 1){
            resetState();
            isNoMoreData(newDataSize);
        }else{
            if (isNoMoreData(newDataSize)){
            } else {
                hasMoreData = true;
                loading = false;
                setHasMoreDataView();
                hideLoadMoreView();
            }
        }
    }

    public boolean isNeedLoadMore(int lastPosition,int currentTotal){

        if (!checkIsEnable()){
            return false;
        }

        this.currentTotal = currentTotal;
        if (lastPosition == currentTotal-1 && hasMoreData){
            if (!loading){
                currentPage++;
                showLoadMoreView();
                loading = true;
                return true;
            }
        }
        return false;

    }

    public void setLoadingError(){
        if (!checkIsEnable()){
            return;
        }
        currentPage--;
        loading = false;
        setLoadMoreErrorView();
    }

    private boolean checkIsEnable(){
        if (!isEnable){
            hideLoadMoreView();
            return false;
        }else {
            return true;
        }
    }

    private void resetState(){
        loading = false;
        currentTotal = 0;
        currentPage = 1;
        hasMoreData = true;
        hideLoadMoreView();
        setHasMoreDataView();
    }

    private boolean isNoMoreData(int newDataSize){
        if (newDataSize < BaseRecyclerViewAdapter.DEFAULT_PER_PAGE_SIZE){
            hasMoreData = false;
            loading = false;
            setNoMoreDataView();
            if (newDataSize>0){
                showLoadMoreView();
            }else {
                hideLoadMoreView();
            }
            return true;
        }
        return false;
    }
}

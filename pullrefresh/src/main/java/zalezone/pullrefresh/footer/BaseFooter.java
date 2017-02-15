package zalezone.pullrefresh.footer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zalezone.pullrefresh.R;

/**
 * Created by zale on 2017/2/13.
 */

public class BaseFooter extends ILoadMore {

    private TextView loadMoreTv;
    private View containterView;

    public BaseFooter(Context context, ViewGroup parent) {
        initView(context,parent);
    }

    public void initView(Context context,ViewGroup parent) {
        containterView = LayoutInflater.from(context).inflate(R.layout.listitem_loadmore,parent,false);
        loadMoreTv = (TextView) containterView.findViewById(R.id.load_more_tv);
        containterView.setVisibility(View.GONE);
    }

    @Override
    public View getContainerView() {
        return containterView;
    }

    @Override
    public void setNoMoreDataView() {
        loadMoreTv.setText("没有更多了");
    }

    @Override
    public void setHasMoreDataView() {
        loadMoreTv.setText("加载更多...");
    }

    @Override
    public void setLoadMoreErrorView() {
        loadMoreTv.setText("加载失败");
    }
    @Override
    public void hideLoadMoreView(){
        containterView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoreView(){
        containterView.setVisibility(View.VISIBLE);
    }

}

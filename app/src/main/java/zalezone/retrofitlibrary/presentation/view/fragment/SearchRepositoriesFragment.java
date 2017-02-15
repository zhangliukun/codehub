package zalezone.retrofitlibrary.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

import okhttp3.Headers;
import zalezone.pullrefresh.PtrClassicFrameLayout;
import zalezone.pullrefresh.PtrDefaultHandler;
import zalezone.pullrefresh.PtrFrameLayout;
import zalezone.pullrefresh.content.BaseRecyclerViewAdapter;
import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.common.base.BaseFragment;
import zalezone.retrofitlibrary.githubapi.GithubApi;
import zalezone.retrofitlibrary.model.ReponseRepositories;
import zalezone.retrofitlibrary.model.RepositoryInfo;
import zalezone.retrofitlibrary.network.IDataCallback;
import zalezone.retrofitlibrary.presentation.view.adapter.adapterimpl.RepositoriesAdapter;

/**
 * Created by zale on 2017/2/9.
 */

public class SearchRepositoriesFragment extends BaseFragment implements View.OnClickListener{

    SearchView searchView;
    RecyclerView recyclerView;
    Button searchBtn;
    PtrClassicFrameLayout ptrFrameLayout;
    RepositoriesAdapter mAdapter;

    private CharSequence query;

    public static SearchRepositoriesFragment newInstance(){
        return new SearchRepositoriesFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        searchView = (SearchView) findViewById(R.id.search_repository);
        searchBtn = (Button) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);

        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_container);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                },2000);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        mAdapter = new RepositoriesAdapter(recyclerView,new ArrayList<RepositoryInfo>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(recyclerView, new BaseRecyclerViewAdapter.onLoadMoreListener() {
            @Override
            public void loadMore(int currentPage) {
                searchRepository(currentPage);
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,long id) {
                showToastShort("你点击的是第"+position+"个位置"+" id="+id);
            }
        });

    }

    @Override
    protected void loadData() {

    }

    @Override
    public int getContainerLayoutId() {
        return R.layout.fragment_search_repositories;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                query =  searchView.getQuery();
                if (TextUtils.isEmpty(query)){
                    query = "zhangliukun";
                }
                searchRepository(1);
                break;
        }
    }

    private void searchRepository(final int currentPage){
        GithubApi.searchRepositiories(query.toString(), "stars", "desc",currentPage,new IDataCallback<ReponseRepositories>() {
            @Override
            public void onSuccess(ReponseRepositories object, Headers headers) {
                if (object!=null&&object.getRepositoryList()!=null){
                        mAdapter.addListData(object.getRepositoryList(),currentPage);
                }
            }

            @Override
            public void onError(int code, String message) {
                mAdapter.loadMoreError();
                showToastShort(code+message);
            }
        });
    }
}

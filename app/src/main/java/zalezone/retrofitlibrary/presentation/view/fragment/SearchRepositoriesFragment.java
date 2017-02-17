package zalezone.retrofitlibrary.presentation.view.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
    PtrClassicFrameLayout ptrFrameLayout;
    RepositoriesAdapter mAdapter;

    private String mQuery;

    public static SearchRepositoriesFragment newInstance(){
        return new SearchRepositoriesFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        searchView = (SearchView) findViewById(R.id.search_repository);
        searchView.setSubmitButtonEnabled(true);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                if (TextUtils.isEmpty(mQuery)){
                    mQuery = "zhangliukun";
                }
                searchRepository(1);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
                if (id>=0){
                    Bundle bundle = new Bundle();
                    bundle.putInt("int", position);
                    bundle.putString(RepositoryInfoFragment.IMAGE_URL_EXTRA,mAdapter.getItem((int) id).owner.avatar_url);
                    View avatat = view.findViewById(R.id.repository_owner_im);
                    ViewCompat.setTransitionName(avatat,position+"");
                    getFragmentManager().beginTransaction().
                            addSharedElement(avatat,position+"").
                            add(getContainerId(),RepositoryInfoFragment.newInstance(bundle)).addToBackStack(null).commitAllowingStateLoss();
                }
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
    }

    private void searchRepository(final int currentPage){
        GithubApi.searchRepositiories(mQuery, "stars", "desc",currentPage,new IDataCallback<ReponseRepositories>() {
            @Override
            public void onSuccess(ReponseRepositories object, Headers headers) {
                if (object!=null&&object.getRepositoryList()!=null){
                        mAdapter.addListData(object.getRepositoryList(),currentPage);
                }
            }

            @Override
            public void onError(int code, String message) {
                mAdapter.loadMoreError(currentPage);
                showToastShort(code+message);
            }
        });
    }
}

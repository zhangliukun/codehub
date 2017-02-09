package zalezone.retrofitlibrary.presentation.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import okhttp3.Headers;
import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.common.base.BaseFragment;
import zalezone.retrofitlibrary.githubapi.GithubApi;
import zalezone.retrofitlibrary.model.RepositoryInfo;
import zalezone.retrofitlibrary.network.IDataCallback;

/**
 * Created by zale on 2017/2/9.
 */

public class SearchRepositoriesFragment extends BaseFragment implements View.OnClickListener{

    SearchView searchView;
    ListView listView;
    Button searchBtn;

    public static SearchRepositoriesFragment newInstance(){
        return new SearchRepositoriesFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        searchView = (SearchView) findViewById(R.id.search_repository);
        listView = (ListView) findViewById(R.id.listview);
        searchBtn = (Button) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);
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
                CharSequence query =  searchView.getQuery();
                if (TextUtils.isEmpty(query)){
                    query = "zhangliukun";
                }
                GithubApi.searchRepositiories(query.toString(), "stars", "desc", new IDataCallback<List<RepositoryInfo>>() {
                    @Override
                    public void onSuccess(List<RepositoryInfo> object, Headers headers) {

                    }

                    @Override
                    public void onError(int code, String message) {

                    }
                });
                break;
        }
    }
}

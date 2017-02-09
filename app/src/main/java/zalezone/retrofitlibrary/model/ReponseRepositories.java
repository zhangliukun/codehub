package zalezone.retrofitlibrary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zale on 2017/2/9.
 */

public class ReponseRepositories extends GithubBaseModel{
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<RepositoryInfo> repositoryList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<RepositoryInfo> getRepositoryList() {
        return repositoryList;
    }

    public void setRepositoryList(List<RepositoryInfo> repositoryList) {
        this.repositoryList = repositoryList;
    }
}

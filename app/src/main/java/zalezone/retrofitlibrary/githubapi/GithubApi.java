package zalezone.retrofitlibrary.githubapi;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import zalezone.pullrefresh.content.BaseRecyclerViewAdapter;
import zalezone.retrofitlibrary.common.config.GithubConfig;
import zalezone.retrofitlibrary.model.Authorization;
import zalezone.retrofitlibrary.model.CreateAuthorization;
import zalezone.retrofitlibrary.model.ReponseRepositories;
import zalezone.retrofitlibrary.model.UserInfo;
import zalezone.retrofitlibrary.network.IDataCallback;
import zalezone.retrofitlibrary.network.IRequestCallback;
import zalezone.retrofitlibrary.network.OkBuilder;
import zalezone.retrofitlibrary.network.OkClient;
import zalezone.retrofitlibrary.network.util.Base64Encoder;

/**
 * Created by zale on 2016/12/24.
 */

public class GithubApi {

    /**
     * OAuth验证
     * @param userName
     * @param password
     * @param dataCallback
     */
    public static void login(String userName, String password, IDataCallback<Authorization> dataCallback){
        String authorization = userName+":"+password;
        try {
            String base64Result = "Basic "+Base64Encoder.encode(authorization);
            OkBuilder.updateAuthorization(base64Result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        CreateAuthorization createAuthorization = new CreateAuthorization();
        createAuthorization.note = GithubConfig.NOTE;
        createAuthorization.client_id = GithubConfig.CLIENT_ID;
        createAuthorization.client_secret = GithubConfig.CLIENT_SECRET;
        createAuthorization.scopes = GithubConfig.SCOPES;

        String str = new Gson().toJson(createAuthorization,CreateAuthorization.class);

        OkClient.httpPostRequestForJson("https://api.github.com/authorizations", str, dataCallback, new IRequestCallback<Authorization>() {
            @Override
            public Authorization success(String content) {
                return new Gson().fromJson(content,Authorization.class);
            }
        });
    }

    /**
     * 获取自己用户信息
     * @param dataCallback
     */
    public static void user(IDataCallback<UserInfo> dataCallback){
        OkClient.httpGetRequest("https://api.github.com/user",null,dataCallback, new IRequestCallback<UserInfo>() {
            @Override
            public UserInfo success(String content) {
                return new Gson().fromJson(content,UserInfo.class);
            }
        });
    }

    /**
     * 获取制定用户名的信息
     * @param userName
     * @param dataCallback
     */
    public static void users(String userName,IDataCallback<String> dataCallback){
        String url = "https://api.github.com/users/"+userName;
        OkClient.httpGetRequest(url,null,dataCallback,new IRequestCallback<String>() {
            @Override
            public String success(String content) {
                content = "zale"+content;
                return content;
            }
        });
    }

    /**
     *
     * @param q 搜索关键字 对于q字段，有以下扩展用法，语法格式为 q=keywords+key:value+key2:value   如language	string	搜索的语言类型，例java,c,python等
     * @param sort 排序依据，值可取stars,forks,updated。默认为best match。
     * @param order 排序顺序，升或降。值可取asc或desc。
     * @param dataCallback
     */
    public static void searchRepositiories(String q,String sort,String order,int page,IDataCallback<ReponseRepositories> dataCallback){
        Map<String,String> params = new HashMap<>();
        params.put("q",q);
        params.put("sort",sort);
        params.put("order",order);
        params.put("page", String.valueOf(page));
        params.put("per_page", String.valueOf(BaseRecyclerViewAdapter.PER_PAGE_SIZE));
        String url = "https://api.github.com/search/repositories";
        OkClient.httpGetRequest(url, params, dataCallback, new IRequestCallback<ReponseRepositories>() {
            @Override
            public ReponseRepositories success(String content) {
                ReponseRepositories reponseRepositories = new Gson().fromJson(content,ReponseRepositories.class);
                if (reponseRepositories!=null){
                    return reponseRepositories;
                }
                return null;
            }
        });
    }

}

package zalezone.retrofitlibrary.githubapi;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import zalezone.retrofitlibrary.common.config.GithubConfig;
import zalezone.retrofitlibrary.model.Authorization;
import zalezone.retrofitlibrary.model.CreateAuthorization;
import zalezone.retrofitlibrary.network.IDataCallback;
import zalezone.retrofitlibrary.network.IRequestCallback;
import zalezone.retrofitlibrary.network.OkBuilder;
import zalezone.retrofitlibrary.network.OkClient;
import zalezone.retrofitlibrary.network.util.Base64Encoder;

/**
 * Created by zale on 2016/12/24.
 */

public class GithubApi {

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

    public static void user(IDataCallback<String> dataCallback){
        OkClient.httpGetRequest("https://api.github.com/user",null,dataCallback, new IRequestCallback<String>() {
            @Override
            public String success(String content) {
                return content;
            }
        });
    }



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

}

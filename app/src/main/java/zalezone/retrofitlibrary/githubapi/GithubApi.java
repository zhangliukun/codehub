package zalezone.retrofitlibrary.githubapi;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;
import zalezone.retrofitlibrary.config.GithubConfig;
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

    public static void authentication(String userName,String password,IDataCallback<String> dataCallback){
        String authorization = userName+":"+password;
        try {
            String base64Result = "Basic "+Base64Encoder.encode(authorization);
            Request request = OkBuilder.urlPost("https://api.github.com","").header("Authorization",base64Result).build();
            //OkClient.httpPostAsync(request,);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        CreateAuthorization createAuthorization = new CreateAuthorization();
        createAuthorization.note = GithubConfig.NOTE;
        createAuthorization.client_id = GithubConfig.CLIENT_ID;
        createAuthorization.client_secret = GithubConfig.CLIENT_SECRET;
        createAuthorization.scopes = GithubConfig.SCOPES;

        String str = new Gson().toJson(createAuthorization,CreateAuthorization.class);

        OkClient.httpPostRequestForJson("https://api.github.com", str, dataCallback, new IRequestCallback<String>() {
            @Override
            public String success(String content) {
                return null;
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

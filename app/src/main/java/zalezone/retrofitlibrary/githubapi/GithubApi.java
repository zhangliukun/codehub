package zalezone.retrofitlibrary.githubapi;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;
import zalezone.retrofitlibrary.network.IDataCallback;
import zalezone.retrofitlibrary.network.IRequestCallback;
import zalezone.retrofitlibrary.network.OkBuilder;
import zalezone.retrofitlibrary.network.OkClient;
import zalezone.retrofitlibrary.network.util.Base64Encoder;

/**
 * Created by zale on 2016/12/24.
 */

public class GithubApi {

    public static void authentication(){
        String authorization = "zhangliukun:zlk19941117";
        try {
            String base64Result = Base64Encoder.encode(authorization);
            Request request = OkBuilder.urlPost("https://api.github.com","").header("Authorization",base64Result).build();
            //OkClient.httpPostAsync(request);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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

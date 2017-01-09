package zalezone.retrofitlibrary.network.interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import zalezone.retrofitlibrary.manager.AccountManager;

/**
 * Created by zale on 2017/1/9.
 */

public class CommonInterceptor implements Interceptor{

    private Context mContext;

    public String getAcceptHeader() {
        return "application/vnd.github.v3.json";
    }

    public CommonInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Accept",getAcceptHeader())
                .header("User-Agent","corehub");
        if (AccountManager.hasLogin(mContext)){
            requestBuilder.header("Authorization","token "+AccountManager.getLoginToken(mContext));
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);

    }
}

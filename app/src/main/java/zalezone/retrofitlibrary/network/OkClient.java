package zalezone.retrofitlibrary.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zalezone.retrofitlibrary.manager.AccountManager;
import zalezone.retrofitlibrary.network.constants.MsgConstants;

/**
 * Created by zale on 16/12/19.
 */

public class OkClient{

    public static final String TAG = "OKClient";
    public static final String testUrl = "http://www.baidu.com";
    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    private static Handler UIHander = new Handler(Looper.getMainLooper());

    private static ExecutorDelivery delivery = new ExecutorDelivery(UIHander);

    public static OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }


    public void setProxy(){

    }

    public static void initOkHttp(Context context){
        OkConfig okConfig = new OkConfig();
        okConfig.authorization = AccountManager.getLoginToken(context);
        OkBuilder.setOkConfig(okConfig);
        OkHttpClient.Builder builder = mOkHttpClient.newBuilder();
        builder.connectTimeout(okConfig.connectionTimeout, TimeUnit.MILLISECONDS);
        builder.readTimeout(okConfig.readTimeout,TimeUnit.MILLISECONDS);
        mOkHttpClient = builder.build();
    }


    private static <T> void processResponse(Response response,IRequestCallback<T> requestCallback,IDataCallback<T> dataCallback) throws IOException {
        if (response.code()>=300){
            delivery.postError(dataCallback,response.code(),response.message());
        }else {
            String responseStr = response.body().string();
            delivery.postSuccess(dataCallback,requestCallback.success(responseStr),response.headers());
        }
        response.body().close();
    }

    //异步get
    private static <T> void httpGetAsync(Request request, final IRequestCallback<T> requestCallback, final IDataCallback<T> dataCallback){

        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                delivery.postError(dataCallback,MsgConstants.DEFAULT_ERROR_CODE, MsgConstants.NET_ERR_CONTENT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                processResponse(response,requestCallback,dataCallback);
            }
        });
    }


    private static <T> void httpPostAsync(Request request, final IRequestCallback<T> requestCallback, final IDataCallback<T> dataCallback){
            getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    delivery.postError(dataCallback,MsgConstants.DEFAULT_ERROR_CODE, MsgConstants.NET_ERR_CONTENT);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    processResponse(response,requestCallback,dataCallback);
                }
            });
    }


    public static <T> void httpGetRequest(String url, Map<String,String> params,IDataCallback<T> dataCallback,IRequestCallback<T> requestCallback){
        Request request = OkBuilder.urlGet(url,params).build();
        httpGetAsync(request,requestCallback,dataCallback);
    }

    public static <T> void httpPostRequest(String url,Map<String,String> params,IDataCallback<T> dataCallback,IRequestCallback<T> requestCallback){
        Request request = OkBuilder.urlPost(url,params).build();
        httpPostAsync(request,requestCallback,dataCallback);
    }

    public static <T> void httpPostRequestForJson(String url,String postString,IDataCallback<T> dataCallback,IRequestCallback<T> requestCallback){
        Request request = OkBuilder.urlPost(url,postString).build();
        httpPostAsync(request,requestCallback,dataCallback);
    }



}

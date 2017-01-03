package zalezone.retrofitlibrary.network;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    private static <T> void processResponse(Response response,IRequestCallback<T> requestCallback,IDataCallback<T> dataCallback) throws IOException {
        String responseStr = response.body().string();
        try {
            JSONObject repJson = new JSONObject(responseStr);
            int ret = repJson.optInt("ret",-1);
            if (ret!=0){
                delivery.postSuccess(dataCallback,requestCallback.success(responseStr),response.headers());

            }else {
                delivery.postError(dataCallback,ret, MsgConstants.NET_ERR_CONTENT);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //异步get
    public static <T> void httpGetAsync(Request request, final IRequestCallback<T> requestCallback, final IDataCallback<T> dataCallback){

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


    public static <T> void httpPostAsync(Request request, final IRequestCallback<T> requestCallback, final IDataCallback<T> dataCallback){
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



}

package zalezone.retrofitlibrary.network;

import android.text.TextUtils;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import zalezone.retrofitlibrary.network.util.OkUtil;

/**
 * Created by zale on 16/12/21.
 */

public class OkBuilder {

    private static OkConfig mOkConfig = new OkConfig();

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");


    public static void setOkConfig(OkConfig okConfig){
        mOkConfig = okConfig;
    }

    public static void updateAuthorization(String authorization){
        mOkConfig.authorization = authorization;
    }

    public static OkConfig getOkConfig() {
        return mOkConfig;
    }

    public static Request.Builder urlGet(String url, Map<String,String> params){
        if (params !=null && params.isEmpty()){
            url = url + "?" + OkUtil.convertMap2SGetParams(params,false);
        }
        Request.Builder builder = new Request.Builder().url(url);

        return addCommonCookie(builder);
    }

    public static Request.Builder urlPost(String url,Map<String,String> params){
        FormBody.Builder builder = new FormBody.Builder();
        if (params!=null && !params.isEmpty()){
            for (Map.Entry<String,String> entry:params.entrySet()){
                if (entry.getValue()!=null){
                    builder.add(entry.getKey(),entry.getValue());
                }
            }
        }
        return addCommonCookie(new Request.Builder().url(url).post(builder.build()));
    }

    public static Request.Builder urlPost(String url,String str){
        return addCommonCookie(new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_JSON,str)));
    }

    public static Request.Builder addCommonCookie(Request.Builder builder){
        if (!TextUtils.isEmpty(mOkConfig.authorization)){
            builder.header("Authorization",mOkConfig.authorization);
        }
        return builder;
    }
}

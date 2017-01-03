package zalezone.retrofitlibrary.network;

import okhttp3.Headers;

/**
 * Created by zale on 2016/12/26.
 */

public interface IDataCallback<T>{
    public void onSuccess(T object, Headers headers);

    public void onError(int code,String message);
}

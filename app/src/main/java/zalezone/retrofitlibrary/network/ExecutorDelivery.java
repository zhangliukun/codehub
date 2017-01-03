package zalezone.retrofitlibrary.network;

import android.os.Handler;

import java.util.concurrent.Executor;

import okhttp3.Headers;

/**
 * Created by zale on 2016/12/26.
 */

public class ExecutorDelivery {

    private Executor mResponseDeliver;

    public ExecutorDelivery(final Handler handler) {
        mResponseDeliver = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    public <T> void postSuccess(final IDataCallback<T> callback, final T t, final Headers headers){
        mResponseDeliver.execute(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(t,headers);
            }
        });
    }

    public <T> void postError(final IDataCallback<T> callback, final int code, final String message){
        mResponseDeliver.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(code,message);
            }
        });
    }



}

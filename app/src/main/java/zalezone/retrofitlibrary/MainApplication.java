package zalezone.retrofitlibrary;

import android.app.Application;

import zalezone.retrofitlibrary.network.OkClient;

/**
 * Created by zale on 2017/1/9.
 */

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        OkClient.initOkHttp(this);
    }
}

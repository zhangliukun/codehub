package zalezone.retrofitlibrary;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import zalezone.retrofitlibrary.experiment.hotswapclass.ClassLoaderManager;
import zalezone.retrofitlibrary.network.OkClient;

/**
 * Created by zale on 2017/1/9.
 */

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        OkClient.initOkHttp(this);
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this,OkClient.getOkHttpClient()).build();
        Fresco.initialize(this,config);
        ClassLoaderManager.init(this);
    }
}

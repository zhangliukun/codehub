package zalezone.retrofitlibrary.experiment.hotswapclass;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zalezone.retrofitlibrary.experiment.hotswapclass.model.BundleModel;

/**
 * Created by zale on 2017/2/28.
 */

public class ClassLoaderManager {

    public static final int BUF_SIZE = 8 * 1024;
    private File internalDexPath;
    private File outDirectory;
    private static Context mContext;
    public static final String TAG = "ClassLoaderManager";
    public Map<String, BundleModel> loaderMap = new HashMap<String, BundleModel>();
    private List<String> packageList = new ArrayList<String>();

    private static ClassLoaderManager instance;
    //private DispatchClassLoader dispatchClassLoader;

    private BundleModel hostBundleModel;
    private BundleModel dispatchBundleModel;

    public static ClassLoaderManager getInstance() {
        if (instance == null) {
            instance = new ClassLoaderManager();
        }
        return instance;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public ClassLoaderManager() {

        hostBundleModel = new BundleModel("host_bundle", "");
        dispatchBundleModel = new BundleModel("dispatch_bundle", "dispatch.jar");

        internalDexPath = mContext.getDir("dex", Context.MODE_PRIVATE);
        outDirectory = mContext.getDir("outdex", Context.MODE_PRIVATE);

    }

    public void printInfo(){
        Log.i(TAG,"dex:"+internalDexPath+";"+"outdex:"+outDirectory);

    }

}

package zalezone.retrofitlibrary.experiment.hotswapclass.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack.qin on 2016/12/7.
 */

public class BundleModel {
    public BundleModel(String bundleName, String bundleFileName) {
        this.bundleName = bundleName;
        this.bundleFileName = bundleFileName;
    }
    public ClassLoader classLoader;
    public ClassLoader parentClassLoader;
    public String assetPath;
    public String dexPath;
    public String optimizedDirectory;
    public String libraryPath;
    public String bundleName;
    public String bundleFileName;
    public List<String> packageNameList = new ArrayList<String>();
    public boolean installSuccess;
    public volatile boolean isInstalling;

    public int minFid = -1000;
    public int maxFid = -1000;
    public String applicationClassName;
//    public Map<String, Integer> fragmentMap = new HashMap<>();


}

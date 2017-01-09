package zalezone.retrofitlibrary.network;

/**
 * Created by zale on 2017/1/3.
 */

public class OkConfig {

    public boolean useProxy = false;
    public boolean useCache = false;
    public String proxyHost;
    public String authorization;
    public int connectionTimeout = 30000;//30s
    public int readTimeout = 30000;

}

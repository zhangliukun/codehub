package zalezone.retrofitlibrary.manager;

import android.content.Context;
import android.text.TextUtils;

import zalezone.retrofitlibrary.model.UserInfo;
import zalezone.retrofitlibrary.network.OkBuilder;
import zalezone.retrofitlibrary.common.sharepreference.SharePreferenceUtil;

/**
 * Created by zale on 2017/1/9.
 */

public class AccountManager {

    public static UserInfo user;
    public final static String USER_TOKEN = "user_token";

    public static void saveLoginToken(Context context,String loginToken){
        OkBuilder.updateAuthorization(loginToken);
        SharePreferenceUtil.getInstance(context).saveString(USER_TOKEN,loginToken);
    }

    public static String getLoginToken(Context context){
        return SharePreferenceUtil.getInstance(context).getString(USER_TOKEN);
    }

    public static void clearLoginToken(Context context){
        OkBuilder.updateAuthorization("");
        SharePreferenceUtil.getInstance(context).removeByKey(USER_TOKEN);
    }

    public static void loginOut(Context context){
        clearLoginToken(context);
    }

    public static boolean hasLogin(Context context){
        return !TextUtils.isEmpty(getLoginToken(context))&&!TextUtils.isEmpty(OkBuilder.getOkConfig().authorization);
    }

}

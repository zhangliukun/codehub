package zalezone.retrofitlibrary.manager;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import zalezone.retrofitlibrary.model.UserInfo;
import zalezone.retrofitlibrary.network.OkBuilder;
import zalezone.retrofitlibrary.common.sharepreference.SharePreferenceUtil;

/**
 * Created by zale on 2017/1/9.
 */

public class AccountManager {

    public static UserInfo mUser;
    public final static String USER_TOKEN = "user_token";
    public final static String USER_INFO = "user_info";

    public static void saveLoginToken(Context context,String loginToken){
        OkBuilder.updateAuthorization(loginToken);
        SharePreferenceUtil.getInstance(context).saveString(USER_TOKEN,loginToken);
    }

    public static String getLoginToken(Context context){
        return SharePreferenceUtil.getInstance(context).getString(USER_TOKEN);
    }

    public static void clearLoginInfo(Context context){
        OkBuilder.updateAuthorization("");
        removeUser();
        SharePreferenceUtil.getInstance(context).removeByKey(USER_TOKEN);
        SharePreferenceUtil.getInstance(context).removeByKey(USER_INFO);
    }

    public static boolean hasLogin(Context context){
        return !TextUtils.isEmpty(getLoginToken(context))&&mUser!=null;
    }

    public static void loginOut(Context context){
        clearLoginInfo(context);
    }

    public static void setUser(Context context,UserInfo user){
        mUser = user;
        String userString = new Gson().toJson(user,UserInfo.class);
        SharePreferenceUtil.getInstance(context).saveString(USER_INFO,userString);
    }

    public static void getUserFromSp(Context context){
        String user = SharePreferenceUtil.getInstance(context).getString(USER_INFO);
        if (!TextUtils.isEmpty(user)){
            mUser = new Gson().fromJson(user,UserInfo.class);
        }
    }

    public static UserInfo getUser(){
        return mUser;
    }

    public static void removeUser(){
        mUser = null;
    }

}

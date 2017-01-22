package zalezone.retrofitlibrary.presentation.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.githubapi.GithubApi;
import zalezone.retrofitlibrary.manager.AccountManager;
import zalezone.retrofitlibrary.model.Authorization;
import zalezone.retrofitlibrary.model.MenuItemModel;
import zalezone.retrofitlibrary.model.UserInfo;
import zalezone.retrofitlibrary.network.IDataCallback;
import zalezone.retrofitlibrary.presentation.contract.MainActivityContract;

/**
 * Created by zale on 2017/1/22.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter{

    private MainActivityContract.View taskView;

    public MainActivityPresenter(@NonNull MainActivityContract.View taskView) {
        this.taskView = taskView;
    }

    @Override
    public void start() {
        loadMenuItem();
        checkLogin();
    }

    private void loadMenuItem() {
        String[] arrays = taskView.getViewContext().getResources().getStringArray(R.array.github_menu_navigation);
        List<MenuItemModel> dataList = new ArrayList<>();
        for (String menuItem:arrays){
            dataList.add(new MenuItemModel(menuItem));
        }
        taskView.onGetMenuList(dataList);
    }

    @Override
    public void githubLogin(String accountName, String password) {
        taskView.showOnLoading();
        GithubApi.login(accountName, password, new IDataCallback<Authorization>() {
            @Override
            public void onSuccess(Authorization object, Headers headers) {
                if (object != null&&!TextUtils.isEmpty(object.getToken())) {
                    AccountManager.saveLoginToken(taskView.getViewContext(), "token " + object.getToken());
                    githubUserInfo();
                }
            }

            @Override
            public void onError(int code, String message) {
                taskView.hideOnLoading();
                taskView.showToastShort("登录失败" + code + message);
            }
        });
    }

    @Override
    public void githubUserInfo(){
        taskView.showOnLoading();
        GithubApi.user(new IDataCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo object, Headers headers) {
                taskView.hideOnLoading();
                if (object != null) {
                    taskView.refreshUserInfo(object);
                    AccountManager.setUser(taskView.getViewContext(),object);
                    taskView.showToastShort("登录成功");
                }
            }

            @Override
            public void onError(int code, String message) {
                taskView.hideOnLoading();
                taskView.showToastShort(code+message);
            }
        });
    }

    @Override
    public void checkLogin() {
            AccountManager.getUserFromSp(taskView.getViewContext());
        if (AccountManager.hasLogin(taskView.getViewContext())){
            taskView.refreshUserInfo(AccountManager.getUser());
        }else {
            //taskView.showLoginDialog();
        }
    }

    @Override
    public void LoginOrOut() {
        if (AccountManager.hasLogin(taskView.getViewContext())) {
            taskView.showLogoutConfirmDialog();
        } else {
            taskView.showLoginDialog();
        }
    }
}

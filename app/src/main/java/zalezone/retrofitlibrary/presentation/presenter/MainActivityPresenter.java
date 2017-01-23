package zalezone.retrofitlibrary.presentation.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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
import zalezone.retrofitlibrary.presentation.widget.dialog.DialogBuilder;

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
            showLogoutConfirmDialog();
        } else {
            showLoginDialog();
        }
    }

    private void showLoginDialog() {
        View loginView = View.inflate(taskView.getViewContext(), R.layout.view_login_dialog, null);
        final EditText accountEt = (EditText) loginView.findViewById(R.id.et_account);
        final EditText passwordEt = (EditText) loginView.findViewById(R.id.et_password);
        DialogBuilder builder = new DialogBuilder(taskView.getViewContext());
        builder.setContentView(loginView)
                .setPositiveBtnCallback("登录", new DialogBuilder.OnBtnClickListener() {
                    @Override
                    public void exectute() {
                        String password = passwordEt.getText().toString();
                        String account = accountEt.getText().toString();
                        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                            taskView.showToastShort("账号或密码不能为空！");
                        }
                        githubLogin(account,password);
                    }
                })
                .setNegativeBtnCallback("取消", new DialogBuilder.OnBtnClickListener() {
                    @Override
                    public void exectute() {

                    }
                }).setTitle("账号登录").setMessage("username or email").showDialog();
    }

    public void showLogoutConfirmDialog() {
        DialogBuilder builder = new DialogBuilder(taskView.getViewContext());
        builder.setPositiveBtnCallback("确定", new DialogBuilder.OnBtnClickListener() {
            @Override
            public void exectute() {
                AccountManager.loginOut(taskView.getViewContext());
                taskView.clearAvatar();
            }
        }).setTitle("提醒").setMessage("确定要退出登录？").showDialog();
    }
}

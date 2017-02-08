package zalezone.retrofitlibrary.presentation.presenter;

import zalezone.retrofitlibrary.manager.AccountManager;
import zalezone.retrofitlibrary.presentation.contract.UserInfoFragmentContract;

/**
 * Created by zale on 2017/2/7.
 */

public class UserInfoFragmentPresenter implements UserInfoFragmentContract.Presenter{

    UserInfoFragmentContract.View taskView;

    public UserInfoFragmentPresenter(UserInfoFragmentContract.View taskView) {
        this.taskView = taskView;
    }

    @Override
    public void start() {
        loadUserInfo();
    }

    private void loadUserInfo(){
        if (AccountManager.getUser()!=null){
            taskView.setUserInfoView(AccountManager.getUser());
        }
    }
}

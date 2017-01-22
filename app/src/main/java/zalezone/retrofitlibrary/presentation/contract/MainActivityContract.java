package zalezone.retrofitlibrary.presentation.contract;

import java.util.List;

import zalezone.retrofitlibrary.common.base.BasePresenter;
import zalezone.retrofitlibrary.common.base.BaseView;
import zalezone.retrofitlibrary.model.MenuItemModel;
import zalezone.retrofitlibrary.model.UserInfo;

/**
 * Created by zale on 2017/1/22.
 */

public interface MainActivityContract {

    interface View extends BaseView{
        public void onGetMenuList(List<MenuItemModel> dataList);
        public void refreshUserInfo(UserInfo userInfo);
        public void showLoginDialog();
        public void showLogoutConfirmDialog();
        public void clearAvatar();
    }

    interface Presenter extends BasePresenter{
        public void githubLogin(String name,String password);
        public void githubUserInfo();
        public void checkLogin();
        public void LoginOrOut();
    }

}

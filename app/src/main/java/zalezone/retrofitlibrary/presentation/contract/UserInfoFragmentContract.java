package zalezone.retrofitlibrary.presentation.contract;

import zalezone.retrofitlibrary.common.base.BasePresenter;
import zalezone.retrofitlibrary.common.base.BaseView;
import zalezone.retrofitlibrary.model.UserInfo;

/**
 * Created by zale on 2017/2/7.
 */

public interface UserInfoFragmentContract {
    interface View extends BaseView{
        public void setUserInfoView(UserInfo userInfo);
    }

    interface Presenter extends BasePresenter{

    }
}

package zalezone.retrofitlibrary.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.common.base.BaseFragment;
import zalezone.retrofitlibrary.model.UserInfo;
import zalezone.retrofitlibrary.presentation.contract.UserInfoFragmentContract;
import zalezone.retrofitlibrary.presentation.presenter.UserInfoFragmentPresenter;
import zalezone.retrofitlibrary.util.ImageUitl;

/**
 * Created by zale on 2017/2/7.
 */

public class UserInfoFragment extends BaseFragment implements UserInfoFragmentContract.View{

    UserInfoFragmentPresenter mPresenter;


    SimpleDraweeView avatarIm;
    TextView nameTv;
    TextView emailTv;

    public static BaseFragment newInstance(){
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        return userInfoFragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        avatarIm = (SimpleDraweeView) findViewById(R.id.avatar);
        nameTv = (TextView) findViewById(R.id.name_tv);
        emailTv = (TextView) findViewById(R.id.email_tv);
    }

    @Override
    protected void loadData() {
        mPresenter = new UserInfoFragmentPresenter(this);
        mPresenter.start();
    }



    @Override
    public int getContainerLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    public Context getViewContext() {
        return mActivity;
    }

    @Override
    public void setUserInfoView(UserInfo userInfo) {
        ImageUitl.loadUriPic(userInfo.getAvatar_url(),avatarIm);
        nameTv.setText(TextUtils.isEmpty(userInfo.getName())?"":userInfo.getName());
        emailTv.setText(TextUtils.isEmpty(userInfo.getEmail())?"":userInfo.getEmail());
    }
}

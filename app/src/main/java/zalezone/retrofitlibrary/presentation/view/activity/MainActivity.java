package zalezone.retrofitlibrary.presentation.view.activity;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.common.base.BaseActivity;
import zalezone.retrofitlibrary.manager.AccountManager;
import zalezone.retrofitlibrary.model.MenuItemModel;
import zalezone.retrofitlibrary.model.UserInfo;
import zalezone.retrofitlibrary.presentation.contract.MainActivityContract;
import zalezone.retrofitlibrary.presentation.presenter.MainActivityPresenter;
import zalezone.retrofitlibrary.presentation.view.adapter.adapterimpl.MenuItemAdapter;
import zalezone.retrofitlibrary.presentation.widget.dialog.DialogBuilder;
import zalezone.retrofitlibrary.util.ImageUitl;

public class MainActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,MainActivityContract.View{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private MenuItemAdapter mAdapter;
    private ListView mDrawerList;

    private View menuHeader;
    private SimpleDraweeView avatarView;
    private TextView userNameTv;
    private TextView userDescptionTv;

    private MainActivityPresenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //fix for white window background
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        initToolBar();
        mAdapter = new MenuItemAdapter(this, new ArrayList<MenuItemModel>());
        initMenuHeader();
        mDrawerList.addHeaderView(menuHeader);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(this);

    }

    @Override
    public void loadData() {
        mPresenter = new MainActivityPresenter(this);
        mPresenter.start();
    }

    @Override
    public int getContainerLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_avatar:
                mPresenter.LoginOrOut();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLogoutConfirmDialog() {
        DialogBuilder builder = new DialogBuilder(this);
        builder.setPositiveBtnCallback("确定", new DialogBuilder.OnBtnClickListener() {
            @Override
            public void exectute() {
                AccountManager.loginOut(getViewContext());
                clearAvatar();
            }
        }).setTitle("提醒").setMessage("确定要退出登录？").showDialog();
    }

    @Override
    public void showLoginDialog() {
        View loginView = View.inflate(this, R.layout.view_login_dialog, null);
        final EditText accountEt = (EditText) loginView.findViewById(R.id.et_account);
        final EditText passwordEt = (EditText) loginView.findViewById(R.id.et_password);
        DialogBuilder builder = new DialogBuilder(this);
        builder.setContentView(loginView)
                .setPositiveBtnCallback("登录", new DialogBuilder.OnBtnClickListener() {
                    @Override
                    public void exectute() {
                        String password = passwordEt.getText().toString();
                        String account = accountEt.getText().toString();
                        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                            showToastShort("账号或密码不能为空！");
                        }
                        mPresenter.githubLogin(account,password);
                    }
                })
                .setNegativeBtnCallback("取消", new DialogBuilder.OnBtnClickListener() {
                    @Override
                    public void exectute() {

                    }
                }).setTitle("账号登录").setMessage("username or email").showDialog();
    }

    @Override
    public void clearAvatar() {
        ImageUitl.loadUriPic("", avatarView);
    }

    @Override
    public void onGetMenuList(List<MenuItemModel> dataList) {
        mAdapter.addListData(dataList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshUserInfo(UserInfo userInfo){
        ImageUitl.loadUriPic(userInfo.getAvatar_url(), avatarView);
        userNameTv.setText(userInfo.getName());
        userDescptionTv.setText(userInfo.getEmail());
    }

    private void initToolBar() {
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop(), toolbar.getPaddingRight(), toolbar.getPaddingBottom());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void initMenuHeader() {
        menuHeader = View.inflate(this, R.layout.view_drawer_header, null);
        menuHeader.setBackgroundColor(ContextCompat.getColor(this, R.color.cyan));
        avatarView = (SimpleDraweeView) menuHeader.findViewById(R.id.user_avatar);
        userNameTv = (TextView) menuHeader.findViewById(R.id.tv_user_name);
        userDescptionTv = (TextView) menuHeader.findViewById(R.id.tv_user_description);
        avatarView.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch ((int) id){
            case 0:
                break;
            default:
                break;
        }
    }
}

package zalezone.retrofitlibrary.view.activity;

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

import okhttp3.Headers;
import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.common.base.BaseActivity;
import zalezone.retrofitlibrary.githubapi.GithubApi;
import zalezone.retrofitlibrary.manager.AccountManager;
import zalezone.retrofitlibrary.model.Authorization;
import zalezone.retrofitlibrary.model.MenuItemModel;
import zalezone.retrofitlibrary.model.UserInfo;
import zalezone.retrofitlibrary.network.IDataCallback;
import zalezone.retrofitlibrary.util.ImageUitl;
import zalezone.retrofitlibrary.view.adapter.adapterimpl.MenuItemAdapter;
import zalezone.retrofitlibrary.view.dialog.DialogBuilder;


public class MainActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private MenuItemAdapter mAdapter;
    private ListView mDrawerList;

    private View menuHeader;
    private SimpleDraweeView avatarView;
    private TextView userNameTv;
    private TextView userDescptionTv;


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
    public int getContainerLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_avatar:
                if (AccountManager.hasLogin(this)) {
                    AccountManager.loginOut(this);
                    ImageUitl.loadUriPic("", avatarView);
                } else {
                    View loginView = View.inflate(this, R.layout.view_login_dialog, null);
                    final EditText accountEt = (EditText) loginView.findViewById(R.id.et_account);
                    final EditText passwordEt = (EditText) loginView.findViewById(R.id.et_password);
                    DialogBuilder builder = new DialogBuilder(this);
                    builder.setContentView(loginView)
                            .setPositiveBtnCallback("登录", new DialogBuilder.OnBtnClickListener() {
                                @Override
                                public void exectute() {
                                    String password = passwordEt.toString();
                                    String account = accountEt.toString();
                                    if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                                        showToastShort("账号或密码不能为空！");
                                    }
                                    githubLogin(account, password);
                                }
                            })
                            .setNegativeBtnCallback("取消", new DialogBuilder.OnBtnClickListener() {
                                @Override
                                public void exectute() {

                                }
                            }).setTitle("账号登录").setMessage("username or email").showDialog();
                }

                break;
            default:
                break;
        }
    }

    private void githubLogin(String accountName, String password) {
        showProgressDialog();
        GithubApi.login(accountName, password, new IDataCallback<Authorization>() {
            @Override
            public void onSuccess(Authorization object, Headers headers) {
                if (object != null&&!TextUtils.isEmpty(object.getToken())) {
                    AccountManager.saveLoginToken(MainActivity.this, "token " + object.getToken());
                    showToastShort("登录成功");
                    GithubApi.user(new IDataCallback<UserInfo>() {
                        @Override
                        public void onSuccess(UserInfo object, Headers headers) {
                            hideProgressDialog();
                            if (object != null && !TextUtils.isEmpty(object.getAvatar_url())) {
                                ImageUitl.loadUriPic(object.getAvatar_url(), avatarView);
                                userNameTv.setText(object.getName());
                                userDescptionTv.setText(object.getEmail());
                            }
                        }

                        @Override
                        public void onError(int code, String message) {
                            showToastShort(code+message);
                            hideProgressDialog();
                        }
                    });
                }
            }

            @Override
            public void onError(int code, String message) {
                showToastShort("登录失败" + code + message);
            }
        });
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
    public void loadData() {
        List<MenuItemModel> dataList = new ArrayList<>();
        dataList.add(new MenuItemModel("Home"));
        dataList.add(new MenuItemModel("Repositories"));
        mAdapter.addListData(dataList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        final TextView textView = (TextView) findViewById(R.id.testTV);
////        final String text = null;
////        Toast.makeText(this,text+"",Toast.LENGTH_LONG).show();
//
//
//        findViewById(R.id.btnT).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                GithubApi.authentication();
////                GithubApi.users("zhangliukun", new IDataCallback<String>() {
////                    @Override
////                    public void onSuccess(String object, Headers headers) {
////                        Toast.makeText(MainActivity.this,object,Toast.LENGTH_LONG).show();
////                    }
////
////                    @Override
////                    public void onError(int code, String message) {
////                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
////                    }
////                });
//                GithubApi.login("test", "test", new IDataCallback<Authorization>() {
//                    @Override
//                    public void onSuccess(Authorization object, Headers headers) {
//                        if (object!=null){
//                            textView.setText(object.toString()+"");
//                            AccountManager.saveLoginToken(MainActivity.this,"token "+object.getToken());
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(int code, String message) {
//                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });
//        findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GithubApi.user(new IDataCallback<String>() {
//                    @Override
//                    public void onSuccess(String object, Headers headers) {
//                        if (!TextUtils.isEmpty(object)){
//                            textView.setText(object+"");
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(int code, String message) {
//
//                    }
//                });
//            }
//        });

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

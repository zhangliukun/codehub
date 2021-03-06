package zalezone.retrofitlibrary.presentation.view.activity;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.common.base.BaseActivity;
import zalezone.retrofitlibrary.common.base.BaseFragment;
import zalezone.retrofitlibrary.model.MenuItemModel;
import zalezone.retrofitlibrary.model.UserInfo;
import zalezone.retrofitlibrary.presentation.contract.MainActivityContract;
import zalezone.retrofitlibrary.presentation.presenter.MainActivityPresenter;
import zalezone.retrofitlibrary.presentation.view.adapter.adapterimpl.MenuItemAdapter;
import zalezone.retrofitlibrary.presentation.view.fragment.SearchRepositoriesFragment;
import zalezone.retrofitlibrary.presentation.view.fragment.UserInfoFragment;
import zalezone.retrofitlibrary.presentation.widget.bottomnavigation.ZBottomNavigation;
import zalezone.retrofitlibrary.presentation.widget.bottomnavigation.ZBottomNavigationItem;
import zalezone.retrofitlibrary.util.ImageUitl;

public class MainActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,MainActivityContract.View{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private MenuItemAdapter mAdapter;
    private ListView mDrawerList;
    private ZBottomNavigation zBottomNavigation;

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
        zBottomNavigation = (ZBottomNavigation) findViewById(R.id.nav_bar);
        initBottomBar();
    }

    private SparseArray<BaseFragment> mainTabFragments = new SparseArray<>();

    private void initBottomBar() {
        ZBottomNavigationItem item1 = new ZBottomNavigationItem("Search",R.drawable.vector_ic_search_black_24dp,R.color.blue);
        ZBottomNavigationItem item2 = new ZBottomNavigationItem("Personal",R.drawable.vector_ic_person_black_24dp,R.color.green);
        ZBottomNavigationItem item3 = new ZBottomNavigationItem("Item3",R.drawable.vector_ic_add_circle_black_24dp,R.color.cyan);
        ZBottomNavigationItem item4 = new ZBottomNavigationItem("Item4",R.drawable.vector_ic_add_circle_black_24dp,R.color.colorAccent);
        zBottomNavigation.addItem(item1);
        zBottomNavigation.addItem(item2);
        zBottomNavigation.addItem(item3);
        zBottomNavigation.addItem(item4);
        zBottomNavigation.setColored(true);
        zBottomNavigation.setForceTitlesDisplay(true);
        zBottomNavigation.setOnTabSelectedListener(new ZBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        if (mainTabFragments.get(0) == null){
                            mainTabFragments.put(0,SearchRepositoriesFragment.newInstance());
                            loadRootFragment(R.id.content_frame, mainTabFragments.get(0),false);
                        }
                        break;
                    case 1:
                        if (mainTabFragments.get(1) == null){
                            mainTabFragments.put(1,UserInfoFragment.newInstance());
                            loadRootFragment(R.id.content_frame, mainTabFragments.get(1),false);
                        }
                        break;
                }
                selectTabShowFragment(position);
                return true;
            }
        });
        zBottomNavigation.setCurrentItem(0);
    }

    private void selectTabShowFragment(int position){
        for (int i=0;i<mainTabFragments.size();i++){
            int key=mainTabFragments.keyAt(i);
            if (position == key){
                mainTabFragments.get(key).showFragment();
            }else {
                mainTabFragments.get(key).hideFragment();
            }
        }

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
    public void clearAvatar() {
        ImageUitl.loadUriPic("", avatarView);
        userNameTv.setText("");
        userDescptionTv.setText("");
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
        if (mDrawerToggle!=null){
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle!=null){
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch ((int) id){
            case 0:
                //ClassLoaderManager.getInstance().printInfo();
                break;
            default:
                break;
        }
    }
}

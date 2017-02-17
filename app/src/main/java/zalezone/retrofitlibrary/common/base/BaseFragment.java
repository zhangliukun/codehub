package zalezone.retrofitlibrary.common.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import zalezone.retrofitlibrary.manager.FragmentMaster;

/**
 * Created by zale on 2017/1/23.
 */

public abstract class BaseFragment extends Fragment{

    protected ProgressDialog progressDialog;

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";


    private int mContainerId;
    private boolean mIsRoot;
    private boolean mIsHidden = true;

    private FragmentMaster mFragmentMaster;

    private View mContainerView;

    protected BaseActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle !=null ){
            mIsRoot = bundle.getBoolean(FragmentMaster.FRAGMENT_ARG_IS_ROOT,false);
            mContainerId = bundle.getInt(FragmentMaster.FRAGMENT_ARG_CONTAINER);
        }

        if (savedInstanceState != null){
            mIsHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (mIsHidden){
                ft.hide(this);
            }else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setCanceledOnTouchOutside(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainerView = inflater.inflate(getContainerLayoutId(),container,false);
        return mContainerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
        loadData();
    }

    public View findViewById(int id) {
        View view = null;
        if (mContainerView != null) {
            view = mContainerView.findViewById(id);
        }else{
            view = getActivity().findViewById(id);
        }
        return view;
    }


    /**
     * 在该方法中初始化UI
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 在该方法中进行数据初始化，当NetworkError，点击NetworkErrorView,会自动重新执行该方法
     */
    protected abstract void loadData();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN,isHidden());
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity){
            mActivity = (BaseActivity) context;
            mFragmentMaster = mActivity.getFragmentMaster();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT<23){
            if (activity instanceof BaseActivity){
                mActivity = (BaseActivity) activity;
                mFragmentMaster = mActivity.getFragmentMaster();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public int getContainerId() {
        return mContainerId;
    }

    public void loadRootFragment(int containerId,BaseFragment toFragment,boolean addToBack){
        mFragmentMaster.loadRootTransaction(getChildFragmentManager(),containerId,toFragment,addToBack);
    }

    public void replaceLoadRootFragment(int containerId,BaseFragment toFragment,boolean addToBack){
        mFragmentMaster.replaceLoadRootTransaction(getChildFragmentManager(),containerId,toFragment,addToBack);
    }

    public void start(BaseFragment toFragment){
        mFragmentMaster.start(getFragmentManager(),this,toFragment,toFragment.getClass().getName());
    }

    //replace事务, 主要用于子Fragment之间的replace
    public void replaceFragment(BaseFragment toFragment,boolean addToBack){
        mFragmentMaster.replaceTransaction(this.getFragmentManager(),this.getContainerId(),toFragment,addToBack);
    }

    public void hideFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(this);
        ft.commitAllowingStateLoss();
    }

    public void showFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(this);
        ft.commitAllowingStateLoss();
    }

    public void showToastShort(String text) {

        if(Looper.myLooper()!=Looper.getMainLooper())
            return;

        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String text) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(mActivity, text, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(int textId) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (textId == 0) {
            return;
        }
        Toast.makeText(mActivity, textId, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(int textId) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (textId == 0) {
            return;
        }
        Toast.makeText(mActivity, textId, Toast.LENGTH_LONG).show();
    }

    public abstract int getContainerLayoutId();

    public void showOnLoading() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void hideOnLoading() {
        progressDialog.dismiss();
    }

}

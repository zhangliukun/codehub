package zalezone.retrofitlibrary.manager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zalezone.retrofitlibrary.common.base.BaseActivity;
import zalezone.retrofitlibrary.common.base.BaseFragment;
import zalezone.retrofitlibrary.util.debug.DebugFragmentRecord;
import zalezone.retrofitlibrary.util.debug.DebugHierarchyViewContainer;

/**
 * Created by zale on 2017/2/7.
 */

public class FragmentMaster {

    public static final String FRAGMENT_ARG_CONTAINER = "fragment_arg_container";
    public static final String FRAGMENT_ARG_IS_ROOT = "fragment_arg_is_root";

    private BaseActivity mActivity;

    public FragmentMaster(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void loadRootTransaction(FragmentManager fragmentManager, int containerId, BaseFragment to,boolean addToBack){
        bindContainerId(containerId,to);
        if (addToBack){
            start(fragmentManager,null,to,to.getClass().getName());
        }else {
            start(fragmentManager,null,to,"false");
        }
    }

    public void replaceLoadRootTransaction(FragmentManager fragmentManager,int containerId,BaseFragment to,boolean addToBack){
        replaceTransaction(fragmentManager,containerId,to,addToBack);
    }

    public void start(FragmentManager fragmentManager,BaseFragment from,BaseFragment to,String toFragmentTag){
        FragmentTransaction ft = fragmentManager.beginTransaction();

        Bundle bundle = to.getArguments();

        if (from == null){
            ft.add(bundle.getInt(FRAGMENT_ARG_CONTAINER),to,toFragmentTag);
            bundle.putBoolean(FRAGMENT_ARG_IS_ROOT,true);
        }else {
            ft.add(from.getContainerId(),to,toFragmentTag);
            if (from.getTag() != null){
                ft.hide(from);
            }
        }
        if (TextUtils.isEmpty(toFragmentTag)||!toFragmentTag.equals("false")){
            ft.addToBackStack(toFragmentTag);
        }
        ft.commitAllowingStateLoss();
    }

    public  void replaceTransaction(FragmentManager fragmentManager,int containerId,BaseFragment to,boolean addToBack){
        if (fragmentManager == null){
            return;
        }
        bindContainerId(containerId,to);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerId,to,to.getClass().getName());
        if (addToBack){
            ft.addToBackStack(to.getClass().getName());
        }
        Bundle bundle = to.getArguments();
        bundle.putBoolean(FRAGMENT_ARG_IS_ROOT,true);
        ft.commitAllowingStateLoss();
    }

    private void bindContainerId(int containerId, BaseFragment to) {
        Bundle args = to.getArguments();
        if (args == null) {
            args = new Bundle();
            to.setArguments(args);
        }
        args.putInt(FRAGMENT_ARG_CONTAINER, containerId);
    }

    /**
     * 调试相关:以dialog形式 显示 栈视图
     */
    void showFragmentStackHierarchyView() {
        DebugHierarchyViewContainer container = new DebugHierarchyViewContainer(mActivity);
        container.bindFragmentRecords(getFragmentRecords());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        new AlertDialog.Builder(mActivity)
                .setTitle("栈视图")
                .setView(container)
                .setPositiveButton("关闭", null)
                .setCancelable(true)
                .show();
    }

    private List<DebugFragmentRecord> getFragmentRecords() {
        List<DebugFragmentRecord> fragmentRecordList = new ArrayList<>();

        List<Fragment> fragmentList = mActivity.getSupportFragmentManager().getFragments();

        if (fragmentList == null || fragmentList.size() < 1) return null;

        for (Fragment fragment : fragmentList) {
            if (fragment == null) continue;
            fragmentRecordList.add(new DebugFragmentRecord(fragment.getClass().getSimpleName(), getChildFragmentRecords(fragment)));
        }
        return fragmentRecordList;
    }

    private List<DebugFragmentRecord> getChildFragmentRecords(Fragment parentFragment) {
        List<DebugFragmentRecord> fragmentRecords = new ArrayList<>();

        List<Fragment> fragmentList = parentFragment.getChildFragmentManager().getFragments();
        if (fragmentList == null || fragmentList.size() < 1) return null;


        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment != null) {
                fragmentRecords.add(new DebugFragmentRecord(fragment.getClass().getSimpleName(), getChildFragmentRecords(fragment)));
            }
        }
        return fragmentRecords;
    }

}

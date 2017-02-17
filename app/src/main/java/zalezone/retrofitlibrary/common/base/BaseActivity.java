package zalezone.retrofitlibrary.common.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import zalezone.retrofitlibrary.manager.FragmentMaster;

/**
 * Created by zale on 2017/1/9.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected ProgressDialog progressDialog;
    protected FragmentMaster mFragmentMaster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContainerLayoutId());
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        getFragmentation();
        initView();
        loadData();
    }

    public abstract void initView();

    public abstract void loadData();

    public FragmentMaster getFragmentMaster(){
        if (mFragmentMaster == null){
            mFragmentMaster = new FragmentMaster(this);
        }
        return mFragmentMaster;
    }

    FragmentMaster getFragmentation() {
        if (mFragmentMaster == null) {
            mFragmentMaster = new FragmentMaster(this);
        }
        return mFragmentMaster;
    }

    /**
     * 针对与分类也tab切换
     * @param containerId
     * @param toFragment
     * @param addToBack
     */
    public void loadRootFragment(int containerId,BaseFragment toFragment,boolean addToBack){
        mFragmentMaster.loadRootTransaction(getSupportFragmentManager(),containerId,toFragment,addToBack);
    }

    /**
     * 针对没有同层布局的页面
     * @param containerId
     * @param toFragment
     */
    public void loadRootFragment(int containerId,BaseFragment toFragment){
        mFragmentMaster.loadRootTransaction(getSupportFragmentManager(),containerId,toFragment,true);
    }

    public void replaceLoadRootFragment(int containerId,BaseFragment toFragment,boolean addToBack){
        mFragmentMaster.replaceLoadRootTransaction(getSupportFragmentManager(),containerId,toFragment,addToBack);
    }

    public void start(BaseFragment toFragment){
        mFragmentMaster.start(getSupportFragmentManager(),null,toFragment,toFragment.getClass().getName());
    }

    public void showToastShort(String text) {

        if(Looper.myLooper()!=Looper.getMainLooper())
            return;

        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String text) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(int textId) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (textId == 0) {
            return;
        }
        Toast.makeText(this, textId, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(int textId) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (textId == 0) {
            return;
        }
        Toast.makeText(this, textId, Toast.LENGTH_LONG).show();
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

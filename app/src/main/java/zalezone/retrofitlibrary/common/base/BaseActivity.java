package zalezone.retrofitlibrary.common.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by zale on 2017/1/9.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContainerLayoutId());
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        initView();
        loadData();
    }

    public abstract void initView();

    public abstract void loadData();

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

    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    public void showProgressDialog() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public int getContainerLayoutId(){
        return -1;
    }

}

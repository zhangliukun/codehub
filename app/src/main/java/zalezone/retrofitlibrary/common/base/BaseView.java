package zalezone.retrofitlibrary.common.base;

import android.os.Bundle;

/**
 * Created by zale on 2017/1/9.
 */

public interface BaseView {

    void initData(Bundle savedInstanceState);

    int getLayoutId();

    void initView();
}

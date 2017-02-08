package zalezone.retrofitlibrary.common.base;

import android.content.Context;

/**
 * Created by zale on 2017/1/9.
 */

public interface BaseView{

    void showOnLoading();
    void hideOnLoading();

    void showToastShort(String text);
    void showToastLong(String text);

    Context getViewContext();
}

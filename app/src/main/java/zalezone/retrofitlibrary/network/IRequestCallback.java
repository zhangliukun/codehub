package zalezone.retrofitlibrary.network;

/**
 * Created by zale on 2016/12/26.
 */

public interface IRequestCallback<T> {
    public T success(String content);
}

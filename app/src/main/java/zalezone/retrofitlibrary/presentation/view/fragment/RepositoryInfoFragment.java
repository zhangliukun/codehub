package zalezone.retrofitlibrary.presentation.view.fragment;

import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;

import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.common.base.BaseFragment;

/**
 * Created by zale on 2017/2/16.
 */

public class RepositoryInfoFragment extends BaseFragment{

    public final static String IMAGE_URL_EXTRA = "image_url";

    public static RepositoryInfoFragment newInstance(Bundle bundle){
        RepositoryInfoFragment repositoryInfoFragment = new RepositoryInfoFragment();
        repositoryInfoFragment.setArguments(bundle);
        return repositoryInfoFragment;
    }

    SimpleDraweeView avatar;

    @Override
    protected void initView(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int position = bundle.getInt("int");
        String url = bundle.getString(IMAGE_URL_EXTRA);

        avatar = (SimpleDraweeView) findViewById(R.id.repository_owner_im);
        avatar.setImageURI(url);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public int getContainerLayoutId() {
        return R.layout.fragment_repositoryinfo;
    }
}

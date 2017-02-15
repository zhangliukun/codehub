package zalezone.retrofitlibrary.presentation.view.adapter.adapterimpl;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import zalezone.pullrefresh.content.BaseRecyclerViewAdapter;
import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.model.RepositoryInfo;
import zalezone.retrofitlibrary.util.ImageUitl;

/**
 * Created by zale on 2017/2/9.
 */

//public class RepositoriesAdapter extends BaseRecyclerViewAdapter<RepositoryInfo,RepositoriesAdapter.ViewHolder> {
//
//
//    public RepositoriesAdapter(List<RepositoryInfo> mData) {
//        super(mData);
//    }
//
//    @Override
//    public void bindDataToItemView(ViewHolder viewHolder, RepositoryInfo item) {
//        viewHolder.repositoryNameTv.setText(item.name+"");
//    }
//
//    @Override
//    public ViewHolder createViewHolder(View v) {
//        return new ViewHolder(v);
//    }
//
////    @Override
////    public void bindDataToItemView(RecyclerViewHolder recyclerViewHolder, RepositoryInfo repositoryInfo) {
////        ((TextView)recyclerViewHolder.getView(R.id.repository_name)).setText(repositoryInfo.name+"");
////    }
//
//    @Override
//    public int getAdapterLayout() {
//        return R.layout.listitem_repositories;
//    }
//
//    static class ViewHolder extends BaseRecyclerViewAdapter.RecyclerViewHolder{
//
//        private TextView repositoryNameTv;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            repositoryNameTv = (TextView) itemView.findViewById(R.id.repository_name);
//        }
//    }
//}

public class RepositoriesAdapter extends BaseRecyclerViewAdapter<RepositoryInfo,BaseRecyclerViewAdapter.RecyclerViewHolder> {


    public RepositoriesAdapter(RecyclerView recyclerView, List<RepositoryInfo> mData) {
        super(recyclerView,mData);
    }

    @Override
    public void bindDataToItemView(RecyclerViewHolder recyclerViewHolder, RepositoryInfo repositoryInfo) {
        ((TextView)recyclerViewHolder.getView(R.id.repository_name)).setText(TextUtils.isEmpty(repositoryInfo.fullName)?"":repositoryInfo.fullName);
        ((TextView)recyclerViewHolder.getView(R.id.language_type)).setText(TextUtils.isEmpty(repositoryInfo.language)?"":repositoryInfo.language);
        ((TextView)recyclerViewHolder.getView(R.id.repository_desc)).setText(TextUtils.isEmpty(repositoryInfo.description)?"":repositoryInfo.description);
        ((TextView)recyclerViewHolder.getView(R.id.stars_tv)).setText(repositoryInfo.starsCount+"");
        ((TextView)recyclerViewHolder.getView(R.id.forks_tv)).setText(repositoryInfo.forksCount+"");
        ((TextView)recyclerViewHolder.getView(R.id.update_time)).setText("Updated on "+repositoryInfo.updatedAt.substring(0,10)+"");
        ImageUitl.loadUriPic(repositoryInfo.owner.avatar_url, (SimpleDraweeView) recyclerViewHolder.getView(R.id.repository_owner_im));
    }

    @Override
    public int getAdapterLayout() {
        return R.layout.listitem_repositories;
    }
}

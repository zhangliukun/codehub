package zalezone.retrofitlibrary.presentation.view.adapter.adapterimpl;

import android.widget.TextView;

import java.util.List;

import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.model.RepositoryInfo;
import zalezone.retrofitlibrary.presentation.view.adapter.BaseRecyclerViewAdapter;

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


    public RepositoriesAdapter(List<RepositoryInfo> mData) {
        super(mData);
    }

    @Override
    public void bindDataToItemView(RecyclerViewHolder recyclerViewHolder, RepositoryInfo repositoryInfo) {
        ((TextView)recyclerViewHolder.getView(R.id.repository_name)).setText(repositoryInfo.name+"");
    }

    @Override
    public int getAdapterLayout() {
        return R.layout.listitem_repositories;
    }
}

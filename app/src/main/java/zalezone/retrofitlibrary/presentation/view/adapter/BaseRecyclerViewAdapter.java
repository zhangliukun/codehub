package zalezone.retrofitlibrary.presentation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zale on 2017/2/9.
 */

public abstract class BaseRecyclerViewAdapter<T,VH extends BaseRecyclerViewAdapter.RecyclerViewHolder> extends RecyclerView.Adapter<VH>{

    private List<T> mData;

    public BaseRecyclerViewAdapter(List<T> mData) {
        this.mData = mData;
    }

    public abstract void bindDataToItemView(VH vh,T item);

    /**
     * 这里直接使用RecyclerViewHolder，子类也直接使用这个ViewHolder，如果子类要自定义ViewHolder，这里的方法需要延迟到子类去加载。
     * @param v
     * @return
     */
    public VH createViewHolder(View v){
        return (VH) new RecyclerViewHolder(v);
    }

    public abstract int getAdapterLayout();

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), getAdapterLayout(),null);
        return createViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T item = getItem(position);
        bindDataToItemView(holder,item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position){
        return mData.get(position);
    }

    public void addListData(T data){
        if (mData == null){
            mData = new ArrayList<>();
            mData.add(data);
        }else {
            mData.add(data);
        }
        notifyDataSetChanged();
    }

    public void addListData(List<T> data){
        if(mData==null){
            mData = data;
        }else{
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> views;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<>();
        }

        public <T extends View> T getView(int id){
            View view = views.get(id);
            if (view == null){
                view = itemView.findViewById(id);
                views.put(id,view);
            }
            return (T) view;
        }
    }

}

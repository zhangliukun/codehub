package zalezone.pullrefresh.content;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zalezone.pullrefresh.footer.BaseFooter;
import zalezone.pullrefresh.footer.ILoadMore;

/**
 * Created by zale on 2017/2/9.
 */

public abstract class BaseRecyclerViewAdapter<T,VH extends BaseRecyclerViewAdapter.RecyclerViewHolder> extends RecyclerView.Adapter<VH>{


    public final static String TAG = "BaseRecyclerViewAdapter";

    public final static int ITEM_TYPE_HEADER = 55000;
    public final static int ITEM_TYPE_FOOTER = 60000;
    public final static int FIXED_ITEM_LOAD_MORE = 50000;

    public final static int COUNT_FIXED_ITEM = 1;

    public final static int PER_PAGE_SIZE =20;

    MultiItemType<T> multiItemType;

    OnItemClickListener mOnItemClickListener;

    ILoadMore loadMoreFooterView;

    RecyclerView mRecyclerView;

    private List<T> mData;
    private Context mContext;

    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private SparseArray<View> mFooterViews = new SparseArray<>();


    public BaseRecyclerViewAdapter(RecyclerView recyclerView,List<T> mData) {
        this.mData = mData;
        this.mContext = recyclerView.getContext();
        this.mRecyclerView = recyclerView;
        initDefaultLoadMoreView();
    }

    private void initDefaultLoadMoreView() {
        loadMoreFooterView = new BaseFooter(mContext,mRecyclerView);
    }

    public abstract void bindDataToItemView(VH vh, T item);

    public abstract int getAdapterLayout();

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void addHeaderView(View headerView){
        mHeaderViews.put(mHeaderViews.size()+ITEM_TYPE_HEADER,headerView);
    }

    public void addFooterView(View footerView){
        mFooterViews.put(mFooterViews.size()+ITEM_TYPE_FOOTER,footerView);
    }

    /**
     * 这里直接使用RecyclerViewHolder，子类也直接使用这个ViewHolder，如果子类要自定义ViewHolder，这里的方法需要延迟到子类去加载。
     * @param v
     * @return
     */
    public VH createViewHolder(View v){
        return (VH) new RecyclerViewHolder(v);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = View.inflate(parent.getContext(), getAdapterLayout(),null);
        //fix do supply the parent

        if (viewType == FIXED_ITEM_LOAD_MORE){
            return createViewHolder(loadMoreFooterView.getContainerView());
        }else if (viewType >= ITEM_TYPE_HEADER && viewType<ITEM_TYPE_FOOTER){
            return createViewHolder(mHeaderViews.get(viewType));
        }else if (viewType >= ITEM_TYPE_FOOTER){
            return createViewHolder(mFooterViews.get(viewType));
        }

        int layoutId = getAdapterLayout();
        if (multiItemType!=null){
            layoutId = multiItemType.getLayoutId(viewType);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
        return createViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1){
            return FIXED_ITEM_LOAD_MORE;
        }else if (isHeader(position)){
            return ITEM_TYPE_HEADER+position;
        }else if (isFooter(position)){
            return ITEM_TYPE_FOOTER+(position-mHeaderViews.size()-mData.size());
        }else if (multiItemType!=null){
            int realPosition = getRealDataPosition(position);
            return multiItemType.getItemViewType(realPosition,mData.get(realPosition));
        }else {
            return super.getItemViewType(position);
        }
    }

    public ILoadMore getFooter(){
        return loadMoreFooterView;
    }

    private int getRealDataPosition(int mixedPosition){
        return mixedPosition-mHeaderViews.size();
    }

    private boolean isHeader(int position) {
        return mHeaderViews.size() > 0 && position < mHeaderViews.size();
    }

    private boolean isFooter(int position) {
        return mFooterViews.size() > 0 && position >= getItemCount() - mFooterViews.size()- COUNT_FIXED_ITEM;
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        if (getItemViewType(position) < FIXED_ITEM_LOAD_MORE){
            T item = getItem(getRealDataPosition(position));
            bindDataToItemView(holder,item);
        }
        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v,position,getRealDataPosition(position));
                    debugHeaderFooterView();
                }
            });
        }
    }

    private void debugHeaderFooterView(){
        Log.i(TAG,mHeaderViews.toString()+mFooterViews.toString());
    }

    @Override
    public int getItemCount() {
        return mData.size()+ COUNT_FIXED_ITEM +mHeaderViews.size()+mFooterViews.size();
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

    public void addListData(List<T> data,int currentPage){
        if (currentPage == 1){
            replaceListData(data);
        }else {
            addListData(data);
        }
        if (loadMoreFooterView!=null){
            loadMoreFooterView.setState(data.size(),currentPage);
        }
    }

    public void replaceListData(List<T> data){
        if (mData == null){
            mData = data;
        }else {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void loadMoreError(){
        if (loadMoreFooterView!=null){
            loadMoreFooterView.setLoadingError();
        }
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

    /**
     * add for multiItemType
     * @param <T>
     */
    public interface MultiItemType<T>{
        int getLayoutId(int itemType);

        int getItemViewType(int position,T t);
    }



    public interface OnItemClickListener{
        void onItemClick(View view,int position,long id);
    }

    /**
     * 当LayoutManager是GridLayoutManager时，设置header和footer占据的列数
     * @param recyclerView recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isFooter(position) || isHeader(position))
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 当LayoutManager是StaggeredGridLayoutManager时，设置header和footer占据的列数
     * @param holder holder
     */
    @Override
    public void onViewAttachedToWindow(VH holder) {
        super.onViewAttachedToWindow(holder);
        final ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            params.setFullSpan(isHeader(holder.getLayoutPosition())
                    || isFooter(holder.getLayoutPosition()));
        }
    }


    public void setOnLoadMoreListener(RecyclerView recyclerView, final onLoadMoreListener onLoadMoreListener){
        if (recyclerView!=null && onLoadMoreListener!=null){
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    int lastPosition = -1;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof GridLayoutManager){
                            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                        }else if (layoutManager instanceof LinearLayoutManager){
                            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        }else if (layoutManager instanceof StaggeredGridLayoutManager){
                            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                            lastPosition = findMax(lastPositions);
                        }
                        if (loadMoreFooterView.isNeedLoadMore(lastPosition,recyclerView.getLayoutManager().getItemCount())){
                            onLoadMoreListener.loadMore(loadMoreFooterView.currentPage);
                        }
//                        Log.i("listener","currentTOtal:"+getFooter(recyclerView).currentTotal+" lastposition:"
//                                +lastPosition+" loading:"+getFooter(recyclerView).loading+" hasMoreData:"+getFooter(recyclerView).hasMoreData);


                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }
    }

    public interface onLoadMoreListener{
        void loadMore(int currentPage);
    }

    //找到数组中的最大值
    private static int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}

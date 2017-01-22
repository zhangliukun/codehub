package zalezone.retrofitlibrary.presentation.view.adapter.adapterimpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.model.MenuItemModel;
import zalezone.retrofitlibrary.presentation.view.adapter.HolderAdapter;

/**
 * Created by zale on 16/8/29.
 */
public class MenuItemAdapter extends HolderAdapter<MenuItemModel> {

    public MenuItemAdapter(Context context, List<MenuItemModel> listData) {
        super(context, listData);
    }

    @Override
    public int getConvertViewId() {
        return R.layout.listitem_menu;
    }

    @Override
    public BaseViewHolder buildHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.menuTv = (TextView) convertView.findViewById(R.id.menu_tv);
        return holder;
    }

    @Override
    public void bindViewDatas(BaseViewHolder h, MenuItemModel menuItem, int position) {
        ViewHolder holder = (ViewHolder) h;
        holder.menuTv.setText(String.format("%s", menuItem.menuText));
    }

    class ViewHolder extends BaseViewHolder{
        public TextView menuTv;
    }
}

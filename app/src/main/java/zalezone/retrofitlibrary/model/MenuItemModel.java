package zalezone.retrofitlibrary.model;

/**
 * Created by zale on 16/8/29.
 */
public class MenuItemModel {
    public static final int TYPE_NORMAL=0;
    public static final int TYPE_NO_ICON=1;

    public MenuItemModel(String menuText) {
        this.menuText = menuText;
    }

    public String menuText;
}

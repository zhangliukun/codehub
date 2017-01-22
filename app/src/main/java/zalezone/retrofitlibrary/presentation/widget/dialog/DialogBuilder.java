package zalezone.retrofitlibrary.presentation.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zale on 2017/1/11.
 */

public class DialogBuilder {

    private String positiveBtnText = "确定";
    private String neutralBtnText = "忽略";
    private String negativeBtnText = "取消";
    private String defaultTitle = "提示";

    private boolean cancelable = true;
    private boolean outsideTouchCancel = true;
    private boolean outsideTouchExecCallback = true;

    private AlertDialog.Builder dialogBuilder;
    private View contentView;
    private Button cancelBtn, okBtn, neutralBtn;
    private TextView msgTv, titleTv;

    private Context mContext;

    public DialogBuilder(Context context) {
        mContext = context;
        dialogBuilder = new AlertDialog.Builder(context);
    }

    public interface OnBtnClickListener{
        public void exectute();
    }

    private DialogInterface.OnClickListener defaultBtnCallback = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (dialog!=null){
                dialog.dismiss();
            }
        }
    };

    public DialogBuilder setPositiveBtnCallback(String positiveString, final OnBtnClickListener btnClickListener){
        dialogBuilder.setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null){
                    dialog.dismiss();
                }
                if (btnClickListener !=null){
                    btnClickListener.exectute();
                }
            }
        });
        return this;
    }

    public DialogBuilder setNegativeBtnCallback(String negativeString, final OnBtnClickListener btnClickListener){
        dialogBuilder.setNegativeButton(negativeString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null){
                    dialog.dismiss();
                }
                if (btnClickListener!=null){
                    btnClickListener.exectute();
                }
            }
        });
        return this;
    }

    public DialogBuilder setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            dialogBuilder.setTitle(title);
        }
        return this;

    }

    public DialogBuilder setMessage(String message){
        if (!TextUtils.isEmpty(message)){
            dialogBuilder.setMessage(message);
        }
        return this;
    }

    public DialogBuilder setContentView(View contentView){
        dialogBuilder.setView(contentView);
        return this;
    }

    public void showDialog(){
        dialogBuilder.create().show();

    }

}

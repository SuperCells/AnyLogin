package com.supercells.lib;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by solo on 16/7/8.
 * 等待框 Builder
 */
public class LoadingBuilder {

    /**
     * 创建加载框
     * @param context 上下文
     * @param message message
     * @return
     */
    public static Dialog createDialog(Context context, String message){
        Dialog dialog = new Dialog(context, ResUtil.style(context, "LoadingDialog"));
        dialog.setContentView(ResUtil.layout(context, "loading_layout"));
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setDialogMessage(context,dialog,message);
        return dialog;
    }

    public static void setDialogMessage(Context context,Dialog dialog,String message){
        TextView textView = (TextView)dialog.findViewById(ResUtil.view(context, "mc_message"));
        textView.setText(message);
    }
}

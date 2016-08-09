package com.supercells.lib;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by solo on 16/8/6.
 * 对外接口
 */
public class AnyLogin {
    /**
     * 初始化
     * @param context 上下文
     */
    public static void init(Context context){
        AnyData.getInstance().setContext(context);
    }
    public static void addListener(Listener listener){
        AnyData.getInstance().setListener(listener);
    }
    public static void addDialogStyle(Dialog dialog){
        AnyData.getInstance().setDialog(dialog);
    }
    public static void qq(String appId){

    }
    public static void wechat(){

    }
    public static void weibo(){

    }

    public interface Listener{
        void login(String uniqueId);
    }
}

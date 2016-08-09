package com.supercells.lib.wechat;


import android.content.Context;
import android.content.Intent;

/**
 * Created by solo on 16/8/7.
 */
public class Wechat {

    public static void login(Context context,String appId,String appSecret){
        Intent it = new Intent(context,WXActivity.class);
        it.putExtra("AppId",appId);
        it.putExtra("AppSecret",appSecret);
        context.startActivity(it);
    }
}
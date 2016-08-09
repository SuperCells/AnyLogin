package com.supercells.lib.weibo;

import android.content.Context;
import android.content.Intent;

/**
 * Created by solo on 16/8/9.
 */
public class Weibo {
    public static void login(Context context,String appKey){
        Intent it = new Intent(context,WBAuthActivity.class);
        it.putExtra("AppKey",appKey);
        context.startActivity(it);
    }
}

package com.supercells.anylogin.demo;

import android.app.Activity;
import android.widget.Toast;

import com.supercells.lib.AnyConfig;
import com.supercells.lib.AnyListener;
import com.supercells.lib.ConfigQQ;
import com.supercells.lib.utils.StringUtil;
import com.supercells.lib.ConfigWB;
import com.supercells.lib.ConfigWX;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lixiaojian on 16/7/15.
 */
public class AnyLogin {
    /**
     * 登录方式
     */
    enum LoginType {
        WX,
        WB,
        QQ
    }

    public void login(Activity activity, LoginType type, AnyConfig config, AnyListener listener) {
        try {
            if (type == LoginType.QQ) {
                ConfigQQ configQQ = (ConfigQQ) config;
                String appId = configQQ.QQ_appId();
                if (StringUtil.isEmpty(appId)) {
                    Toast.makeText(activity, "QQ appId empty", Toast.LENGTH_SHORT).show();
                }
                Class clazz = Class.forName("com.supercells.lib.ApiQQ");
                Method method = clazz.getMethod("login", new Class[]{Activity.class, String.class, AnyListener.class});
                method.invoke(clazz, new Object[]{activity, appId, listener});

            } else if (type == LoginType.WB) {
                ConfigWB configWB = (ConfigWB) config;
                String appKey = configWB.WB_appKey();
                String redirectUrl = configWB.WB_redirectURL();
                String scope = configWB.WB_scope();
                if (StringUtil.isEmpty(appKey)) {
                    Toast.makeText(activity, "WB appKey empty", Toast.LENGTH_SHORT).show();
                }
                if (StringUtil.isEmpty(redirectUrl)) {
                    Toast.makeText(activity, "WB redirectUrl empty", Toast.LENGTH_SHORT).show();
                }
                if(StringUtil.isEmpty(scope)){
                    scope = "email";
                }
                Class clazz = Class.forName("com.supercells.lib.ApiWB");
                Method method = clazz.getMethod("login", new Class[]{Activity.class, String.class,String.class,String.class, AnyListener.class});
                method.invoke(clazz, new Object[]{activity, appKey,redirectUrl,scope, listener});
            } else if (type == LoginType.WX) {
                ConfigWX configWX = (ConfigWX) config;
                String appId = configWX.WX_appId();
                String appSecret = configWX.WX_appSecret();
                if (StringUtil.isEmpty(appId)) {
                    Toast.makeText(activity, "WX appId empty", Toast.LENGTH_SHORT).show();
                }
                if (StringUtil.isEmpty(appSecret)) {
                    Toast.makeText(activity, "WX appSecret empty", Toast.LENGTH_SHORT).show();
                }
                Class clazz = Class.forName("com.supercells.lib.ApiWX");
                Method method = clazz.getMethod("login", new Class[]{Activity.class, String.class,String.class, AnyListener.class});
                method.invoke(clazz, new Object[]{activity, appId,appSecret, listener});
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

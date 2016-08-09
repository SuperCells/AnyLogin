package com.supercells.lib.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.supercells.lib.AnyData;
import com.supercells.lib.DBUtil;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by solo on 16/8/6.
 * QQ
 */
public class QQ {
    private static Tencent tencent;
    private static Context context;
    public static void login(Context context,String appId){
        login(context,appId,"get_simple_userinfo");
    }
    public static void login(Context context,String appId,String scope){
        QQ.context = context;
        QQ.tencent = Tencent.createInstance(appId,context.getApplicationContext());
        String data = DBUtil.getString(AnyData.getInstance().getContext(), "QQ_DB", "QQ_KEY");
        try {
            JSONObject jsonObject = new JSONObject(data);
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                QQ.tencent.setAccessToken(token, expires);
                QQ.tencent.setOpenId(openId);
            }
        } catch (JSONException e) {
        }finally {
            if (tencent!= null && !tencent.isSessionValid()){
                tencent.login((Activity)context, scope,iUiListener);
            }
        }
    }

    public static Tencent getTencent() {
        return tencent;
    }

    public static void logout(Context context){
        if (tencent!= null){
            tencent.logout(context);
        }
    }


    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
        }
    }


    private static IUiListener iUiListener =  new IUiListener() {
        @Override
        public void onComplete(Object o) {
            try {
                JSONObject jsonObject = (JSONObject) o;
                String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                        && !TextUtils.isEmpty(openId)) {
                    QQ.tencent.setAccessToken(token, expires);
                    QQ.tencent.setOpenId(openId);
                    AnyData.getInstance().getListener().login(openId);
                    DBUtil.setString(QQ.context, "QQ_DB", "QQ_KEY", jsonObject.toString());
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };
}

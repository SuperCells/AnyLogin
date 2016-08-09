package com.supercells.lib.wechat;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import com.supercells.lib.AnyData;
import com.supercells.lib.HttpUtil;
import com.supercells.lib.LoadingBuilder;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by solo on 16/8/7.
 */
public class WXActivity extends Activity implements IWXAPIEventHandler {
    /**
     * Wechat API
     */
    private String appId, appSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appId = getIntent().getStringExtra("AppId");
        appSecret = getIntent().getStringExtra("AppSecret");
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, appId, true);
        iwxapi.registerApp(appId);
        //Wechat处理回调
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        SendAuth.Resp resp = (SendAuth.Resp) baseResp;
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) { //用户同意授权
            String token = resp.code;
            if (null != token && !"".equals(token)) {
                //请求openId
                final Dialog dialog = LoadingBuilder.createDialog(this, "Loading...");
                dialog.show();
                dialog.setCancelable(false);

                String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                        "appid=" + appId +
                        "&secret=" + appSecret +
                        "&code=" + token +
                        "&grant_type=authorization_code";
                String response = HttpUtil.get(url);
                try {
                    if (null != response && !"".equals(response)) {
                        JSONObject jsonObject  = new JSONObject(response);
                        String openId = jsonObject.getString("openid");
                        if(null != openId && !"".equals(openId)){
                            AnyData.getInstance().getListener().login(openId);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                    finish();
                }

            }
        } else if (resp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {
            finish();
        } else {
            finish();
        }
    }
}



package com.supercells.lib.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.supercells.lib.AnyData;
import com.supercells.lib.AnyLogin;
import com.supercells.lib.ResUtil;

/**
 * Created by solo on 16/8/9.
 */
public class WBAuthActivity extends Activity{

    private static final String TAG = "weibosdk";
    public static final String REDIRECT_URL = "http://www.sina.com";
    private static final String SCOPE = "email";
    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
    private String appKey;
    /**
     * @see {@link Activity#onCreate}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建微博实例
        //mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
        appKey = getIntent().getStringExtra("AppKey");
        mAuthInfo = new AuthInfo(this, appKey, REDIRECT_URL,SCOPE);
        mSsoHandler = new SsoHandler(WBAuthActivity.this, mAuthInfo);
        mSsoHandler.authorize(new AuthListener());
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            String  uid =  mAccessToken.getUid();
            if (mAccessToken.isSessionValid()) {
                // 授权成功
                AnyData.getInstance().getListener().login(uid);
                finish();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = ResUtil.stringValue(WBAuthActivity.this,"mc_toast_auth_fail");
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(WBAuthActivity.this, message,Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onCancel() {
            finish();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(WBAuthActivity.this, "Auth exception : " + e.getMessage(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}

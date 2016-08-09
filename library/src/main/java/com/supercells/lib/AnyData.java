package com.supercells.lib;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by solo on 16/8/6.
 */
public class AnyData {
    private static AnyData ourInstance = new AnyData();

    public static AnyData getInstance() {
        return ourInstance;
    }

    private AnyData() {
    }

    private AnyLogin.Listener listener;
    public AnyLogin.Listener getListener() {
        return listener;
    }
    public void setListener(AnyLogin.Listener listener) {
        this.listener = listener;
    }

    private Context context;
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    private Dialog dialog;
    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}

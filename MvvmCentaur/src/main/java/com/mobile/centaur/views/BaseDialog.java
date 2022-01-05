package com.mobile.centaur.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.app.mobile.centaur.R;


/**
 * Created by zzz on 2017/3/22.
 */

public abstract class BaseDialog extends Dialog {
    protected Context mContext;

    protected BaseDialog(@NonNull Context context) {
        this(context, R.style.MyDialog);
        this.mContext = context;
    }
    protected BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());
        setCancelable(false);
        setLayoutParams();
        initViews(savedInstanceState);
    }
    private void setLayoutParams(){
        setLayoutParams(Gravity.CENTER);
    }
    protected void setLayoutParams(int gravity){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;
        window.setAttributes(lp);
    }
    protected abstract @LayoutRes int getLayoutId();
    protected abstract void initViews(Bundle savedInstanceState);

    @Override
    public void dismiss() {
        super.dismiss();
    }
}

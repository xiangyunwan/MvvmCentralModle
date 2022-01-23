package com.mobile.centaur.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.app.mobile.centaur.R;
import com.gyf.barlibrary.ImmersionBar;
import com.mobile.centaur.utils.LogUtils;
import com.mobile.centaur.utils.StatusBarUtil;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {
    protected View view;

    protected P mPresenter;

    private ImmersionBar mImmersionBar;

    protected Activity activity;
    protected ViewDataBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.color_2f62c9));
        activity = this;
        ActivityCollector.addActivity(this, getClass());
        setContentView(getView());
        mPresenter = initPresenter();
        initCommonData();
        initView();
        initListener();
        initData();
    }

    protected abstract P initPresenter();


    private void initCommonData() {
        if (mPresenter != null)
            mPresenter.attachView(this);

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true, 0.2f); //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        mImmersionBar.init();   //所有子类都将继承这些相同的属性

    }

    /**
     * 获取当前Activity的DataBinding对象
     * java 方式调用
     * @param <VT>
     * @return
     */
    protected <VT extends ViewDataBinding> VT creatDataBiding() {
        VT view = (VT) DataBindingUtil.setContentView(this, getLayoutId());
        if (view == null)
            throw new NullPointerException("This resource id is invalid.");
        return view;
    }

    /**
     * kotlin 方式调用
     * @return
     */
//    protected ViewDataBinding createDataBiding() {
//        binding=DataBindingUtil.setContentView(this, getLayoutId());
//        if (binding == null)
//            throw new NullPointerException("This resource id is invalid.");
//        return binding;
//    }


    protected <P extends BasePresenter> P getmPresenter() {

        return (P) mPresenter;
    }


    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();


    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }


    /**
     * @param str 显示一个内容为str的toast
     */
    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param contentId 显示一个内容为contentId指定的toast
     */
    public void toast(int contentId) {
        Toast.makeText(this, contentId, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param str 日志的处理
     */
    public void LogE(String str) {
        LogUtils.e(getClass().getSimpleName(), str);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        if (mPresenter != null)
            mPresenter.detachView();

        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

}


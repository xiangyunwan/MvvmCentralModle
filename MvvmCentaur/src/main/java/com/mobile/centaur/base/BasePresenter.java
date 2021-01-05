package com.mobile.centaur.base;


public abstract class BasePresenter<V extends IBaseView> {
    private V view;

    /**
     * @param iBaseView 绑定,一般在初始化中调用该方法
     */
    public void attachView(V iBaseView) {
        view = iBaseView;
    }

    /**
     * 防止内存的泄漏,清楚presenter与activity之间的绑定
     */
    public void detachView() {
        if (view != null) {
            view = null;
        }
    }

    /**
     * @return 获取View
     */
    public V getIView() {
        return view;
    }
}

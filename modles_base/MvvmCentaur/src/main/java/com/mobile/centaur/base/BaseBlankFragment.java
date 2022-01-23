package com.mobile.centaur.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;


/**
 * created by mengbenming on 2018/9/17
 *
 * @class
 */
public abstract class BaseBlankFragment extends Fragment implements IBaseView {
    protected View view;
    protected Context context;
    protected Bundle bundle;
    protected LayoutInflater mInflater;
    private ViewDataBinding binding;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bundle != null) {
            outState.putBundle("bundle", bundle);
        }
    }

    /**
     * 绑定activity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * 获取当前Fragment的DataBinding对象
     *
     * @param <VT>
     * @return
     */
//    protected <VT extends ViewDataBinding> VT creatDataBiding() {
//        VT view = (VT) binding;
//        if (view == null)
//            throw new NullPointerException("This resource id is invalid.");
//        return view;
//    }

    /**
     * 运行在onAttach之后
     * 可以接受别人传递过来的参数,实例化对象.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle("bundle");
        } else {
            bundle = getArguments() == null ? new Bundle() : getArguments();
        }
    }


    /**
     * 运行在onCreate之后
     * 生成view视图
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;

        try {
            binding = DataBindingUtil.inflate(mInflater, getLayoutId(), null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (binding == null) {
//            view = inflater.inflate(getLayoutId(), null);
//        } else {
//            view = binding.getRoot();
//        }
//        view = inflater.inflate(getLayoutId(), null);
        view = binding.getRoot();
        return view;
    }

    /**
     * 运行在onCreateView之后
     * 加载数据
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //由于fragment生命周期比较复杂,所以Presenter在onCreateView创建视图之后再进行绑定,不然会报空指针异常
        initView();
        initListener();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 跳转fragment
     *
     * @param fragment
     * @param tag
     */
//    public void gotoFragment(Fragment fragment, String tag) {
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.hide(this).add(R.id.enterprise_main_fl, fragment, tag);
//        fragmentTransaction.addToBackStack(tag);
//        fragmentTransaction.commitAllowingStateLoss();
//    }

    /**
     * 类似Activity的OnBackgress
     * fragment进行回退
     */
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    public abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();
}


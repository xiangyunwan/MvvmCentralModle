package com.example.testcentral.ui

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.testcentral.R
import com.example.testcentral.bean.LoginBean
import com.example.testcentral.databinding.ActivityMainBinding
import com.mobile.centaur.base.BaseBlankActivity

class MainActivity : BaseBlankActivity(){

    private lateinit var loginViewModel: LoginViewModel
    private var mBinding: ActivityMainBinding? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mBinding=creatDataBiding()
        loginViewModel= LoginViewModel()
        loginViewModel.liveData.observe(this,object : Observer<LoginBean> {
            override fun onChanged(result: LoginBean?) {
                mBinding?.tvCentral?.text = result?.name
            }
        })
    }

    override fun initListener() {

    }

    override fun initData() {
        loginViewModel.loadData(this,"aa","bb");
    }
}
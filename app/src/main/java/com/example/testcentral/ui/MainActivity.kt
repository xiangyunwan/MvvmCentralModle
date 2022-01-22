package com.example.testcentral.ui

import android.os.Debug
import android.util.Log
import com.example.testcentral.R
import com.mobile.centaur.base.BaseBlankActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseBlankActivity(){

    private lateinit var loginViewModel: LoginViewModel
    override fun getLayoutId(): Int {
        Debug.startMethodTracing("we_trace")
        return R.layout.activity_main
    }

    override fun initView() {
        loginViewModel= LoginViewModel()
        loginViewModel.liveData.observe(this,
            {
                    result -> tvCentral?.text = result?.name
            })
    }

    override fun initListener() {

    }
    private fun showMsg(msg: String, msg2: String) {
        Log.d("TAG", "msg=$msg")
    }

    override fun initData() {
//        loginViewModel.loadData(this,"aa","bb");
        loginViewModel.getTestData(this);
    }

    override fun onDestroy() {
        super.onDestroy()
        Debug.stopMethodTracing()
    }
}
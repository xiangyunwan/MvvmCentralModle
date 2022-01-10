package com.example.testcentral.ui

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testcentral.bean.LoginBean
import com.mobile.centaur.base.BaseModel
import com.mobile.centaur.network.ApiException


class LoginViewModel : ViewModel() {

    private val loginModle by lazy {
      LoginModle()
    }
    val liveData = MutableLiveData<LoginBean>()

    fun loadData(activity: Activity,
                 unionid: String,
                 info_type: String,) {

        loginModle.getLoginData(
            activity,
            unionid,
            info_type,
            object : BaseModel.InfoCallBack<LoginBean>() {

                override fun successInfo(data: LoginBean) {
                    liveData.value = data
                }

                override fun failThrowable(t: Throwable?) {
                    if (t is ApiException) {
                        liveData.value = LoginBean(t.message.toString(),"",t.message.toString())
                    }
                }

                override fun complete(message: String?) {
                    liveData.value = LoginBean(message,"",message)
                }

            })
    }

    fun getTestData(activity: Activity) {

        loginModle.getTestData(
            activity,
            object : BaseModel.InfoCallBack<LoginBean>() {

                override fun successInfo(data: LoginBean) {
                    liveData.value = data
                }

                override fun failThrowable(t: Throwable?) {
                    if (t is ApiException) {
                        liveData.value = LoginBean(t.message.toString(),"",t.message.toString())
                    }
                }

                override fun complete(message: String?) {
                    liveData.value = LoginBean(message,"",message)
                }

            })
    }

}
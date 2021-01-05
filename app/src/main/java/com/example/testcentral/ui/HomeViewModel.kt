package com.example.testcentral.ui

import android.app.Activity
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testcentral.bean.LoginBean
import com.mobile.centaur.base.BaseModel
import com.mobile.centaur.network.ApiException


class LoginViewModel : ViewModel() {

//    private val _text = MutableLiveData<LoginBean>().apply {
//        value = "This is login page"
//    }
//    val text: LiveData<LoginBean> = _text

//    data class BindingData(
//
//        @Bindable val data: MutableList<LoginBean>,
//
//        @Bindable var updateTime: String
//
//    ) : BaseObservable()
//
//
    private val loginModle by lazy {
      LoginModle()
    }
//    val bindingData: BindingData = BindingData(liveData, "")

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
//                    bindingData.data=data
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
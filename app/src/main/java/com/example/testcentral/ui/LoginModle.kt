package  com.example.testcentral.ui

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.example.testcentral.base.ExternalApiModels
import com.example.testcentral.bean.LoginBean
import com.mobile.centaur.network.ApiException
import com.mobile.centaur.network.BaseResponse
import com.mobile.centaur.network.RxSubscriber
import com.mobile.centaur.network.RxUtils
import java.util.*

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/6
 **/
class LoginModle : ExternalApiModels() {
    fun getLoginData(
        activity: Activity,
        unionid: String,
        info_type: String,
        callBack: InfoCallBack<LoginBean>
    ) {
        val queryMap = HashMap<String, String>()
        queryMap["unionid"] = unionid
        queryMap["info_type"] = info_type
        getDefaultApiService().getThirdLoginData(queryMap)
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(object : RxSubscriber<LoginBean>(activity) {
                override fun onNext(loginBean: LoginBean) {
                    super.onNext(loginBean)
                    callBack.successInfo(loginBean)
                }

                override fun onError(t: Throwable?) {
                    super.onError(t)
                    if (t!! is ApiException) {
                        callBack.failThrowable(t)
                    }else{
                        callBack.complete(t.message)
                    }
                }

                override fun onComplete() {
                    super.onComplete()
                    callBack.complete(null)
                }
            })
    }

    fun getTestData(activity: Activity,callBack: InfoCallBack<LoginBean>){
        getDefaultApiService().getAskSettingConfig("365688")
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(object : RxSubscriber<LoginBean>(activity) {
                override fun onNext(loginBean: LoginBean) {
                    super.onNext(loginBean)
                    callBack.successInfo(loginBean)
                }

                override fun onError(t: Throwable?) {
                    super.onError(t)
                    if (t!! is ApiException) {
                        callBack.failThrowable(t)
                    }else{
                        callBack.complete(t.message)
                    }
                }
                override fun onComplete() {
                    super.onComplete()
                    callBack.complete(null)
                }
            })
    }
}
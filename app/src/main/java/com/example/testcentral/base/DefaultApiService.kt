package com.example.testcentral.base

import com.example.testcentral.bean.LoginBean
import com.mobile.centaur.network.BaseResponse
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface DefaultApiService {

    /**
     * 请求接口
     *
     * @param queryMap
     * @return
     */
    @POST("v1/user.thirdLogin")
    fun getThirdLoginData(@QueryMap queryMap: Map<String, String>?): Flowable<BaseResponse<LoginBean?>?>

    @GET("user/v3/hospitalUser/acceptAskSettingConfig")
    fun getAskSettingConfig(@Query("userId") userId: String?): Flowable<BaseResponse<LoginBean?>>

}
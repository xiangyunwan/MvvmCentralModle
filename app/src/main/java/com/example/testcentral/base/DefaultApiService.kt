package com.example.testcentral.base

import com.example.testcentral.bean.LoginBean
import com.example.testcentral.bean.MyConstants
import com.mobile.centaur.network.BaseResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.*

interface MyApiService {

    /**
     * 请求接口
     *
     * @param queryMap
     * @return
     */
    @POST(MyConstants.THIRDLOGIN_URL)
    fun getThirdLoginData(@QueryMap queryMap: Map<String, String>?): Flowable<BaseResponse<LoginBean?>?>
}
package com.example.testcentral.base

import com.example.testcentral.bean.LoginBean
import com.mobile.centaur.network.BaseResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.*

//import io.reactivex.Flowable;
interface MyApiService {

    /**
     * 请求接口
     *
     * @param queryMap
     * @return
     */
//    @FormUrlEncoded
    @POST(MyConstants.THIRDLOGIN_URL)
    fun getThirdLoginData(@QueryMap queryMap: Map<String, String>?): Flowable<BaseResponse<LoginBean?>?>
}
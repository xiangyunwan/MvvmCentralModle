package com.example.testcentral.base

import com.mobile.centaur.network.BaseResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

/**
 * @Descirption:
 * @Author zzz
 * @Date 2021/12/17
 **/
interface AiliyunApiService {

    /**
     * 测试地址
     * @param productCodes
     * @return
     */
    @GET("user/v3/feedback/getIndications")
    fun getIndications(@Query("productCodes") productCodes: String?): Observable<BaseResponse<Any>>

    /**
     * 测试地址
     * @return
     */
    @POST("user/v3/feedback/submitScoreFeedback")
    fun submitScoreFeedback(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any?>>
}
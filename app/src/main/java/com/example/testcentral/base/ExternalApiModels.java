package com.example.testcentral.base

import com.mobile.centaur.base.BaseModel
import com.mobile.centaur.network.NetWorkManager

/**
 * 网络切换
 */
open class ExternalApiModels : BaseModel() {
    companion object {
        @Volatile
        protected var apiService: DefaultApiService? = null
        private var mAiliyunApiService: AiliyunApiService? = null

        /**
         * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
         */
        val defaultApiService: DefaultApiService?
            get() {
                if (apiService == null) {
                    synchronized(DefaultApiService::class.java) {
                        if (apiService == null) {
                            apiService = NetWorkManager.getRetrofit().create(
                                DefaultApiService::class.java
                            )
                        }
                    }
                }
                return apiService
            }
        val bjMainUserApi: AiliyunApiService?
            get() {
                if (mAiliyunApiService == null) {
                    synchronized(AiliyunApiService::class.java) {
                        if (mAiliyunApiService == null) {
                            mAiliyunApiService = NetWorkManager.getRetrofit().create(
                                AiliyunApiService::class.java
                            )
                        }
                    }
                }
                return mAiliyunApiService
            }
    }
}
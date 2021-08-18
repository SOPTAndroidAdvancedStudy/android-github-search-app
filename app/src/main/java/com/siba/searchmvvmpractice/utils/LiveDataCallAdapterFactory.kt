package com.siba.searchmvvmpractice.utils

import androidx.lifecycle.LiveData
import com.siba.searchmvvmpractice.remote.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Retrofit 통신 중에 LiveData로 값을 받고 싶을 때 사용합니다.
 *
 *
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if(getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0,returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if(rawObservableType != ApiResponse::class.java){
            throw IllegalArgumentException("type must be a resource")
        }
        if(observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0,observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }

}
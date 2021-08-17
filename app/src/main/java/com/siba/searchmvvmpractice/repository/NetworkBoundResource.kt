package com.siba.searchmvvmpractice.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.siba.searchmvvmpractice.application.AppExecutors
import com.siba.searchmvvmpractice.remote.ApiEmptyResponse
import com.siba.searchmvvmpractice.remote.ApiErrorResponse
import com.siba.searchmvvmpractice.remote.ApiResponse
import com.siba.searchmvvmpractice.remote.ApiSuccessResponse
import com.siba.searchmvvmpractice.vo.Resource

/**
 * A generic class that can provide a resource backed by both the sqlite database and network.
 *
 * SSong-develop
 * 네트워킹으로 데이터를 가져오거나 , Local에서 데이터를 가져올 떄 여러 상황이 있다.
 * 대표적으로 뭐 Success , Loading , Fail 이렇게 나누기도 하는데
 * 그 경우 처리하는 여러가지 방법이 존재하는 데 구글에서 제안한 방법중에 하나이다.
 * 코드를 차근차근히 살펴보면서 어떤 의미를 가지는 녀석인지 알아보면 좋을거 같다.
 *
 */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource){ data ->
            result.removeSource(dbSource)
            if(shouldFetch(data)){
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource){ newData ->
                    setValue(Resource.success(newData))
                }
            }
        }

    }

    @MainThread
    private fun setValue(newValue : Resource<ResultType>) {
        if(result.value != newValue){
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource : LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its lastest value quickly
        result.addSource(dbSource) {newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse){ response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when(response){
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            // we specially request a new live data
                            // otherwise we will get immediately last cached value.
                            // which my not be updated with latest results received from network
                            result.addSource(loadFromDb()) {newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()){ newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource){ newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data : ResultType) : Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall() : LiveData<ApiResponse<RequestType>>
}
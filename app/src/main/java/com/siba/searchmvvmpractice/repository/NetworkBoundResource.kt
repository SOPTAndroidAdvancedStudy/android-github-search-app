package com.siba.searchmvvmpractice.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.siba.searchmvvmpractice.AppExecutors
import com.siba.searchmvvmpractice.remote.ApiEmptyResponse
import com.siba.searchmvvmpractice.remote.ApiErrorResponse
import com.siba.searchmvvmpractice.remote.ApiResponse
import com.siba.searchmvvmpractice.remote.ApiSuccessResponse
import com.siba.searchmvvmpractice.vo.Resource

/**
 * A generic class that can provide a resource backed by both the sqlite database and network.
 *
 * 결과를 담아 놓는 MediatorLiveData인데 어떤 결과인가???
 *
 * ApiResponse Wrapper 클래스를 통해서 NoContent인 경우의 제어를 해줬다
 *
 * 그 결과로 받은 데이터는 User에게 보여지기 전까지의 상태가 있을 것이고 그것을 나타내는 것이 Resource Wrapper 클래스이다.
 *
 * 결과적으로 Resource Wrapper클래스의 형태로 사용자들에게 보여주도록 한다.
 *
 * ResultType과 RequestType이 존재 , 생성자로 appExecutor를 받는다.(Dagger를 통해 Inject 받는다)
 */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {
    /**
     * 결과를 받아 놓을 MediatorLiveData
     *
     * 결과라는 건 어떤 결과를 얘기하는 것일까????
     *
     * 이 NetworkBoundResource에서 결과란, Resource Wrapper class 로 wrapping 되어 오는 network되어 오는 ResultType을 받아낸다.
     *
     * 이때 network에는 localDatabase 또한 포함이다.
     *
     * shouldFetch 메서드를 통해 Networking 작업을 할지 안할지를 제어한다.
     *
     */
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        // Resource
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
package com.siba.searchmvvmpractice.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.db.GithubDb
import com.siba.searchmvvmpractice.remote.ApiEmptyResponse
import com.siba.searchmvvmpractice.remote.ApiErrorResponse
import com.siba.searchmvvmpractice.remote.ApiResponse
import com.siba.searchmvvmpractice.remote.ApiSuccessResponse
import com.siba.searchmvvmpractice.vo.RepoSearchResult
import com.siba.searchmvvmpractice.vo.Resource
import java.io.IOException

/**
 * A task that reads the search result in the database and fetches the next page, if it has one.
 *
 * SSong-develop
 * : 왜 Runnable 객체를 상속받아서 사용했는지를 알아야 합니다.
 *
 * : Runnable은 주로 Thread에서 사용하고 , 특정 쓰레드에서 원하는 작업을 작성하고자 할 떄 Runnable객체의 run()메소드를 override해 사용합니다.
 *
 * : 그렇게 생각해보면 Runnable은 쉽게 생각하면 Thread를 interface화 시킨것이라고 생각할 수 있습니다.
 *
 * : Thread를 상속하면 , 기본적으로 java와 kotlin은 단일 상속만 가능하기 때문에 여러개를 상속받을 수 없게 되고 그럴 때 Runnable을 사용해 보다 유연하게 사용할 수 있도록 한다.
 *
 */
class FetchNextSearchPageTask constructor(
    private val query: String,
    private val githubService: GithubService,
    private val db: GithubDb
) : Runnable {

    private val _liveData = MutableLiveData<Resource<Boolean>?>()
    val liveData: LiveData<Resource<Boolean>?> = _liveData

    override fun run() {
        val current = db.repoDao.findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        if (nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }
        val newValue = try {
            val response = githubService.searchRepos(query, nextPage).execute()
            when (val apiResponse = ApiResponse.create(response)) {
                is ApiSuccessResponse -> {
                    // we merge all repo ids into 1 list so that it is easier to fetch the
                    // result list
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.repoIds)

                    ids.addAll(apiResponse.body.items.map { it.id })
                    val merged = RepoSearchResult(
                        query, ids,
                        apiResponse.body.total, apiResponse.nextPage
                    )
                    db.runInTransaction {
                        db.repoDao.insert(merged)
                        db.repoDao.insertRepos(apiResponse.body.items)
                    }
                    Resource.success(apiResponse.nextPage != null)
                }
                is ApiEmptyResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }
            }
        } catch (exception: IOException) {
            Resource.error(exception.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}
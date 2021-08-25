package com.siba.searchmvvmpractice.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.siba.searchmvvmpractice.AppExecutors
import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.db.GithubDb
import com.siba.searchmvvmpractice.db.RepoDao
import com.siba.searchmvvmpractice.remote.ApiResponse
import com.siba.searchmvvmpractice.remote.ApiSuccessResponse
import com.siba.searchmvvmpractice.utils.AbsentLiveData
import com.siba.searchmvvmpractice.utils.RateLimiter
import com.siba.searchmvvmpractice.vo.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: GithubDb,
    private val repoDao: RepoDao,
    private val githubService: GithubService
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>(appExecutors) {
            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(item)
            }

            override fun shouldFetch(data: List<Repo>): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb(): LiveData<List<Repo>> = repoDao.loadRepositories(owner)

            override fun createCall(): LiveData<ApiResponse<List<Repo>>> =
                githubService.getRepos(owner)

            override fun onFetchFailed() {
                repoListRateLimit.reset(owner)
            }
        }.asLiveData()
    }

    fun loadRepo(owner: String, name: String): LiveData<Resource<Repo>> {
        return object : NetworkBoundResource<Repo, Repo>(appExecutors) {
            override fun saveCallResult(item: Repo) {
                repoDao.insert(item)
            }

            override fun shouldFetch(data: Repo): Boolean = data == null

            override fun loadFromDb(): LiveData<Repo> = repoDao.load(
                ownerLogin = owner,
                name = name
            )

            override fun createCall(): LiveData<ApiResponse<Repo>> = githubService.getRepo(
                owner = owner,
                name = name
            )
        }.asLiveData()
    }

    fun loadContributors(owner: String, name: String): LiveData<Resource<List<Contributor>>> {
        return object : NetworkBoundResource<List<Contributor>, List<Contributor>>(appExecutors) {
            override fun saveCallResult(item: List<Contributor>) {
                item.forEach {
                    it.repoName = name
                    it.repoOwner = owner
                }
                /**
                 * SSong-develop
                 * : runInTransaction을 왜 사용했는지???
                 */
                db.runInTransaction {
                    repoDao.createRepoIfNotExists(
                        Repo(
                            id = Repo.UNKNOWN_ID,
                            name = name,
                            fullName = "$owner/$name",
                            description = "",
                            owner = Repo.Owner(owner, null),
                            stars = 0
                        )
                    )
                    repoDao.insertContributors(item)
                }
            }

            override fun shouldFetch(data: List<Contributor>): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Contributor>> =
                repoDao.loadContributors(owner, name)

            override fun createCall(): LiveData<ApiResponse<List<Contributor>>> =
                githubService.getContributors(owner, name)
        }.asLiveData()
    }

    fun searchNextPage(query : String) : LiveData<Resource<Boolean>?> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
            query = query,
            githubService = githubService,
            db = db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    fun search(query : String) : LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>,RepoSearchResponse>(appExecutors) {
            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.items.map { it.id }
                val repoSearchResult = RepoSearchResult(
                    query = query,
                    repoIds = repoIds,
                    totalCount = item.total,
                    next = item.nextPage
                )
                db.runInTransaction {
                    repoDao.insertRepos(item.items)
                    repoDao.insert(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<Repo>): Boolean = data == null

            override fun loadFromDb(): LiveData<List<Repo>> {
                return repoDao.search(query).switchMap { searchData ->
                    if(searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        repoDao.loadOrdered(searchData.repoIds)
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<RepoSearchResponse>> = githubService.searchRepos(query)

            override fun processResponse(response: ApiSuccessResponse<RepoSearchResponse>): RepoSearchResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLiveData()
    }
}
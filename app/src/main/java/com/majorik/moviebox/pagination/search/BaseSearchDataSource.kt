package com.majorik.moviebox.pagination.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.majorik.data.repositories.SearchRepository
import com.majorik.domain.NetworkState
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseSearchDataSource<T>(
    private val repository: SearchRepository,
    private val query: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, T>() {

    protected var supervisorJob = SupervisorJob()
    protected val networkState = MutableLiveData<NetworkState>()
    protected var retryQuery: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // no-op
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren(null)
    }

    abstract fun executeQuery(
        page: Int,
        callback: (List<T>) -> Unit
    )

    protected fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Logger.e("Ошибка: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    fun getNetworkState(): LiveData<NetworkState> = networkState

    fun refresh() = this.invalidate()

    fun retryFailedQuery() {
        val previousQuery = retryQuery
        retryQuery = null
        previousQuery?.invoke()
    }
}

package com.majorik.moviebox.ui.tvTabCollections

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.enums.movie.TVCollectionType
import com.majorik.moviebox.pagination.collections.TVCollectionsDataSourceFactory
import com.majorik.moviebox.ui.base.BaseViewModel

class TVCollectionsViewModel(tvRepository: TVRepository, tvCollectionType: TVCollectionType) :
    BaseViewModel() {
    private val dataSourceFactory =
        TVCollectionsDataSourceFactory(
            tvRepository = tvRepository,
            scope = ioScope,
            tvCollectionType = tvCollectionType
        )

    val tvResults = LivePagedListBuilder(dataSourceFactory, pagedConfig()).build()

    val networkState: LiveData<NetworkState>? =
        Transformations.switchMap(dataSourceFactory.source) { it.getNetworkState() }

    fun fetchItems() {
        dataSourceFactory.refresh()
    }

    private fun pagedConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()

    fun refreshFailedRequest() = dataSourceFactory.getSource()?.retryFailedQuery()

    fun refreshAllList() = dataSourceFactory.getSource()?.refresh()
}

package com.majorik.moviebox.ui.movieTabCollections

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.majorik.data.repositories.MovieRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.enums.movie.MovieCollectionType
import com.majorik.moviebox.pagination.collections.MovieCollectionsDataSourceFactory

class MovieCollectionsViewModel(movieRepository: MovieRepository, movieCollectionType: MovieCollectionType) :
    ViewModel() {
    private val dataSourceFactory =
        MovieCollectionsDataSourceFactory(
            repository = movieRepository,
            scope = viewModelScope,
            movieCollectionType = movieCollectionType
        )

    val movieResults = LivePagedListBuilder(dataSourceFactory, pagedConfig()).build()

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

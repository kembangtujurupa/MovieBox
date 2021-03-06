package com.majorik.moviebox.pagination.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.data.repositories.SearchRepository
import com.majorik.domain.tmdbModels.person.Person
import kotlinx.coroutines.CoroutineScope

class SearchPeopleDataSourceFactory(
    private val repository: SearchRepository,
    private var query: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Person>() {
    val source = MutableLiveData<SearchPeopleDataSource>()

    override fun create(): DataSource<Int, Person> {
        val source = SearchPeopleDataSource(
            repository,
            query,
            scope
        )
        this.source.postValue(source)
        return source
    }

    fun getQuery() = query

    fun getSource() = source.value

    fun updateQuery(query: String) {
        this.query = query
        getSource()?.refresh()
    }
}

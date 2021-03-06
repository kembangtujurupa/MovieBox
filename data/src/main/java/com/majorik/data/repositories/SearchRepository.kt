package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.movie.MovieResponse
import com.majorik.domain.tmdbModels.person.PersonResponse
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.majorik.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.domain.tmdbModels.tv.TVResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SearchRepository(private val api: TmdbApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun multiSearch(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?
    ): ResultWrapper<MultiSearchResponse> {
        return safeApiCall(dispatcher) {
            api.multiSearch(language, query, page, includeAdult)
        }
    }

    suspend fun searchMovies(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?,
        region: String?,
        year: Int?,
        primaryReleaseYear: Int?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.searchMovies(
                language,
                query,
                page,
                includeAdult,
                region,
                year,
                primaryReleaseYear
            )
        }
    }

    suspend fun searchTVs(
        language: String?,
        query: String,
        page: Int?,
        firstAirDateYear: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) {
            api.searchTVSeries(language, query, page, firstAirDateYear)
        }
    }

    suspend fun searchPeoples(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?,
        region: String?
    ): ResultWrapper<PersonResponse> {
        return safeApiCall(dispatcher) {
            api.searchPeoples(language, query, page, includeAdult, region)
        }
    }
}

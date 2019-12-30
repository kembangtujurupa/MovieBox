package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.image.ImageDetails
import com.majorik.domain.tmdbModels.person.PersonDetails

class PersonRepository(private val api: TmdbApiService) : BaseRepository() {
    suspend fun getPersonById(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ): PersonDetails? {
        return safeApiCall(
            call = {
                api.getPersonById(personId, language, appendToResponse)
            },
            errorMessage = "Ошибка GET[getPersonById] (personId = $personId)"
        )
    }

    suspend fun getPersonTaggedImages(
        personId: Int,
        language: String?,
        page: Int?
    ): MutableList<ImageDetails>? {
        val personResponse = safeApiCall(
            call = {
                api.getPersonTaggedImages(personId, language, page)
            },
            errorMessage = "Ошибка GET[getPersonTaggedImages] (personId = $personId)"
        )

        return personResponse?.results?.toMutableList()
    }

    suspend fun getPersonPosters(
        personId: Int
    ): MutableList<ImageDetails>?{
        val personResponse = safeApiCall(
            call = {
                api.getPersonPosters(personId)
            },
            errorMessage = "Ошибка GET[getPersonPosters] (personId = $personId)"
        )

        return personResponse?.profiles?.toMutableList()
    }
}
package com.majorik.domain.tmdbModels.genre

import com.squareup.moshi.Json

data class GenreResponse(
    @field:Json(name = "genres") val genres: List<Genre>
)

package com.majorik.moviebox.viewholders.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.utils.GenresStorageObject
import kotlinx.android.synthetic.main.item_card_with_details.view.*

class SearchMovieViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {
    fun bindTo(item: Movie?) {
        item?.let {
            itemView.item_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            itemView.title.text = it.title

            if (!it.releaseDate.isNullOrEmpty()) {
                itemView.year.text = it.releaseDate!!.toDate().yearInt.toString()
            }

            itemView.vote_average.text = it.voteAverage.toString()

            itemView.genres.text = it.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                .joinToString(" - ") { (it ?: "").capitalize() }

            itemView.vote_count.text = it.voteCount.toString()
        }
    }
}

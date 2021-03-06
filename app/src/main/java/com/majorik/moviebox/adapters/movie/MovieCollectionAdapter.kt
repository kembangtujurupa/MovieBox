package com.majorik.moviebox.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.adapters.movie.MovieCollectionAdapter.MovieViewHolder
import com.majorik.moviebox.databinding.ItemSmallPosterCardBinding
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity
import kotlinx.android.synthetic.main.item_small_poster_card.view.*

class MovieCollectionAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSmallPosterCardBinding.inflate(layoutInflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(movies[position])

        holder.itemView.collection_card.setSafeOnClickListener {
            holder.itemView.context.startDetailsActivityWithId(
                movies[position].id,
                MovieDetailsActivity::class.java
            )
        }
    }

    internal fun addItems(items: List<Movie>) {
        val startPosition = movies.size
        movies = items
        notifyItemRangeInserted(startPosition, items.size)
    }

    override fun getItemId(position: Int): Long {
        return movies[position].id.toLong()
    }

    class MovieViewHolder(private val parent: ItemSmallPosterCardBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(movie: Movie) {
            parent.placeholderText.text = movie.title

            parent.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath
            )
        }
    }
}

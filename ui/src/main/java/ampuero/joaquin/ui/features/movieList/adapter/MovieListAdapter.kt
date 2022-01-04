package ampuero.joaquin.ui.features.movieList.adapter

import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.ui.databinding.ItemMovieGridBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import javax.inject.Inject

class MovieListAdapter @Inject constructor() :
    PagingDataAdapter<MovieEntity, MovieListViewHolder>(UserComparator) {

    private var movieCLickedListener: MovieCLickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemBinding =
            ItemMovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {

        getItem(position)?.let { movie ->
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                movie.id.let {
                    movieCLickedListener?.onMovieSelected(it)
                }
            }
        }
    }

    fun setOnClickListener(movieCLickedListener: MovieCLickedListener) {
        this.movieCLickedListener = movieCLickedListener
    }

    companion object {
        const val TAG = "MovieListAdapter"

        object UserComparator : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface MovieCLickedListener {
        fun onMovieSelected(movieId: Int)
    }
}
package ampuero.joaquin.ui.features.movieList.adapter

import ampuero.joaquin.data.Constants
import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.ui.databinding.ItemMovieGridBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieListViewHolder(private val binding: ItemMovieGridBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(movieModel: MovieEntity) {
        binding.itemMovieName.text = movieModel.title
        Glide.with(binding.itemMoviePoster.context).load("${Constants.IMAGE_URL}${movieModel.poster_path}").diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.itemMoviePoster)
    }
}
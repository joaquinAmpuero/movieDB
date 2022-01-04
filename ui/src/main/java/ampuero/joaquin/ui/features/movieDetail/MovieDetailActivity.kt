package ampuero.joaquin.ui.features.movieDetail

import ampuero.joaquin.data.Constants
import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.ui.R
import ampuero.joaquin.ui.databinding.ActivityMovieDetailBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getIntExtra(MOVIE_ID, -1).let {
            if (it != -1) {
                viewModel.getMovie(it)
            } else {
                // show message and finish
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when (state) {
                    is MovieDetailViewModel.DetailState.Error -> {

                    }
                    MovieDetailViewModel.DetailState.Loading -> {

                    }
                    is MovieDetailViewModel.DetailState.Success -> {
                        setMovieData(state.movie)
                    }
                }
            }
        }
    }

    private fun setMovieData(movie: MovieEntity) {
        binding.collapsingToolbar.title = movie.title
        binding.movieReleaseDate.text = getString(R.string.movie_release_date,movie.release_date)
        binding.movieRating.text = getString(R.string.movie_rating,movie.vote_average)
        binding.movieOverview.text = movie.overview
        Glide.with(this).load("${Constants.IMAGE_URL}${movie.backdrop_path}").diskCacheStrategy(
            DiskCacheStrategy.DATA
        ).into(binding.movieBackdrop)
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        fun startDetailActivity(movieId: Int, context: Context) {
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
            context.startActivity(intent)
        }
    }
}
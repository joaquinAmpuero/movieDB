package ampuero.joaquin.data.remote

import ampuero.joaquin.data.api.MovieService
import ampuero.joaquin.data.models.MovieModel
import javax.inject.Inject


class MovieRemoteDataSource @Inject constructor(private val movieService: MovieService) :
    IMovieRemoteDataSource {
    override suspend fun getRatedMovies(page: Int): List<MovieModel> {
        return movieService.getRatedMovies(page = page).results
    }

    override suspend fun getPopularMovies(page: Int): List<MovieModel> {
        return movieService.getPopularMovies(page = page).results
    }
}
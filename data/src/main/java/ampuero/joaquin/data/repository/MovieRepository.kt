package ampuero.joaquin.data.repository

import ampuero.joaquin.data.local.ILocalMoviesDataSource
import ampuero.joaquin.data.models.MovieModel
import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.domain.repositories.IMovieRepository
import ampuero.joaquin.data.remote.IMovieRemoteDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteDataSource: IMovieRemoteDataSource,
    private val localMoviesDataSource: ILocalMoviesDataSource
) : IMovieRepository {
    override suspend fun getRatedMovies(page: Int): List<MovieEntity> {
        val count = 20
        val offset = if (page > 0) {
            (page - 1) * count
        } else {
            0
        }
        var movies = listOf<MovieEntity>()
        if (page == 0) {
            return movies
        }
        try {
            val ratedMovies = movieRemoteDataSource.getRatedMovies(page)
            coroutineScope {
                launch {
                    localMoviesDataSource.insertAll(ratedMovies)
                    movies = mapMovies(localMoviesDataSource.getRatedMovies(count = count, offset = offset))
                }
            }
        } catch (e: Exception) {
            movies = mapMovies(localMoviesDataSource.getRatedMovies(count = 20, offset = offset))
            if (movies.isEmpty()) {
                throw e
            }
        }
        return movies
    }

    private fun mapMovies(movies: List<MovieModel>) =
        movies.map {
            mapMovie(it)!!
        }

    private fun mapMovie(movie : MovieModel?): MovieEntity? {
        movie?.let {
            return MovieEntity(
                movie.id,
                movie.poster_path,
                movie.overview,
                movie.release_date,
                movie.title,
                movie.backdrop_path,
                movie.popularity,
                movie.video,
                movie.vote_average
            )
        }
        return null
    }

    override suspend fun getPopularMovies(page: Int): List<MovieEntity> {
        val count = 20
        val offset = if (page > 0) {
            (page - 1) * count
        } else {
            0
        }
        var movies = listOf<MovieEntity>()
        if (page == 0) {
            return movies
        }
        try {
            val ratedMovies = movieRemoteDataSource.getPopularMovies(page)
            coroutineScope {
                launch {
                    localMoviesDataSource.insertAll(ratedMovies)
                    movies = mapMovies(localMoviesDataSource.getPopularMovies(count = count, offset = offset))
                }
            }
        } catch (e: Exception) {
            movies = mapMovies(localMoviesDataSource.getPopularMovies(count = 20, offset = offset))
            if (movies.isEmpty()) {
                throw e
            }
        }
        return movies
    }

    override suspend fun getMovie(movieId: Int): MovieEntity? {
        return mapMovie(localMoviesDataSource.getMovie(movieId))
    }

    companion object {
        val TAG by lazy { this.javaClass.simpleName.toString() }
    }
}

package ampuero.joaquin.domain.repositories

import ampuero.joaquin.domain.entities.MovieEntity


interface IMovieRepository {
    suspend fun getRatedMovies(page: Int = 1): List<MovieEntity>
    suspend fun getPopularMovies(page: Int = 1): List<MovieEntity>
    suspend fun getMovie(movieId: Int): MovieEntity?
}
package ampuero.joaquin.data.remote

import ampuero.joaquin.data.models.MovieModel

interface IMovieRemoteDataSource {
    suspend fun getRatedMovies(page:Int=1) : List<MovieModel>
    suspend fun getPopularMovies(page: Int=1): List<MovieModel>
}
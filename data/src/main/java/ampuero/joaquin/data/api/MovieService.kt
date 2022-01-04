package ampuero.joaquin.data.api

import ampuero.joaquin.data.Constants
import ampuero.joaquin.moviedb.data.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(Constants.RATED_MOVIES)
    suspend fun getRatedMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET(Constants.POPULAR)
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): MovieResponse

}
package ampuero.joaquin.data.remote

import ampuero.joaquin.data.api.MovieService
import ampuero.joaquin.data.models.MovieModel
import ampuero.joaquin.moviedb.data.models.MovieResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock

class MovieRemoteDataSourceTest{
    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN api response with one result WHEN calling getRatedMovies() THEN should return list with one movie`() = runBlockingTest {
        val movieService: MovieService = mock<MovieService>()
        Mockito.`when`(movieService.getRatedMovies(apiKey = any(), page = eq(1))).thenReturn(MOVIE_RESPONSE)
        val moveRemoteDataSource = MovieRemoteDataSource(movieService)
        val response = moveRemoteDataSource.getRatedMovies(1)
        assertEquals(response, MOVIES)
        assertEquals(response.size, 1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN no more results WHEN calling getRatedMovies() THEN should return empty list`() = runBlockingTest {
        val movieService: MovieService = mock<MovieService>()
        Mockito.`when`(movieService.getRatedMovies(apiKey = any(), page = eq(2) )).thenReturn(MOVIE_RESPONSE_NO_RESULTS)
        val moveRemoteDataSource = MovieRemoteDataSource(movieService)
        val response = moveRemoteDataSource.getRatedMovies(2)
        assertEquals(response, MOVIE_RESPONSE_NO_RESULTS.results)
        assertEquals(response.size, 0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN api response with one result WHEN calling getPopularMovies() THEN should return list with one movie`() = runBlockingTest {
        val movieService: MovieService = mock<MovieService>()
        Mockito.`when`(movieService.getPopularMovies(apiKey = any(), page = eq(1) )).thenReturn(MOVIE_RESPONSE)
        val moveRemoteDataSource = MovieRemoteDataSource(movieService)
        val response = moveRemoteDataSource.getPopularMovies(1)
        assertEquals(response, MOVIES)
        assertEquals(response.size, 1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN no more results WHEN calling getPopularMovies() THEN should return empty list`() = runBlockingTest {
        val movieService: MovieService = mock<MovieService>()
        Mockito.`when`(movieService.getPopularMovies(apiKey = any(), page = eq(2) )).thenReturn(MOVIE_RESPONSE_NO_RESULTS)
        val moveRemoteDataSource = MovieRemoteDataSource(movieService)
        val response = moveRemoteDataSource.getPopularMovies(2)
        assertEquals(response, MOVIE_RESPONSE_NO_RESULTS.results)
        assertEquals(response.size, 0)
    }

    companion object{
        private val MOVIES: List<MovieModel> = listOf(
            MovieModel(
                id = 278,
                poster_path =  "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg",
                overview = "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                release_date = "1994-09-10",
                title = "The Shawshank Redemption",
                backdrop_path ="/xBKGJQsAIeweesB79KC89FpBrVr.jpg" ,
                video = false,
                vote_average = 8.32,
                popularity = 6.741296
            )
        )
        val MOVIE_RESPONSE = MovieResponse(1, MOVIES)
        val MOVIE_RESPONSE_NO_RESULTS = MovieResponse(1, listOf())
    }
}
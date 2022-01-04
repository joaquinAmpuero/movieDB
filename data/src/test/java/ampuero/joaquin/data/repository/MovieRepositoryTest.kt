package ampuero.joaquin.data.repository

import ampuero.joaquin.data.local.ILocalMoviesDataSource
import ampuero.joaquin.data.models.MovieModel
import ampuero.joaquin.data.remote.IMovieRemoteDataSource
import ampuero.joaquin.moviedb.data.models.MovieResponse
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class MovieRepositoryTest {

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN page 0 WHEN calling getRatedMovies THEN response should be an empty list`() = runBlockingTest {
        val remote = mock<IMovieRemoteDataSource>()
        val db = mock<ILocalMoviesDataSource>()
        val movieRepository = MovieRepository(remote, db)
        val response = movieRepository.getRatedMovies(0)
        assertEquals(response.size,0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN page 0 WHEN calling getPopularMovies THEN response should be an empty list`() = runBlockingTest {
        val remote = mock<IMovieRemoteDataSource>()
        val db = mock<ILocalMoviesDataSource>()
        val movieRepository = MovieRepository(remote, db)
        val response = movieRepository.getPopularMovies(0)
        assertEquals(response.size,0)
    }

    @Test
    fun `GIVEN exception in remote source WHEN calling getRatedMovies, having cached local results THEN should return local source`() = runBlockingTest {
        val remote = mock<IMovieRemoteDataSource>()
        val db = mock<ILocalMoviesDataSource>()

        Mockito.`when`(remote.getRatedMovies(any())).then {
            throw Exception()
        }
        Mockito.`when`(db.getRatedMovies(any(), any())).thenReturn(MOVIES)

        val movieRepository = MovieRepository(remote, db)
        val response = movieRepository.getRatedMovies(1)
        assertEquals(response.size,1)
    }

    @Test
    fun `GIVEN exception in remote source WHEN calling getPopularMovies, having cached local results THEN should return local source`() = runBlockingTest {
        val remote = mock<IMovieRemoteDataSource>()
        val db = mock<ILocalMoviesDataSource>()

        Mockito.`when`(remote.getPopularMovies(any())).then {
            throw Exception()
        }
        Mockito.`when`(db.getPopularMovies(any(), any())).thenReturn(MOVIES)

        val movieRepository = MovieRepository(remote, db)
        val response = movieRepository.getPopularMovies(1)
        assertEquals(response.size,1)
    }

    @Test
    fun `GIVEN empty results from remote source WHEN calling getRatedMovies, and not having cached results THEN should return empty list`() = runBlockingTest {
        val remote = mock<IMovieRemoteDataSource>()
        val db = mock<ILocalMoviesDataSource>()

        Mockito.`when`(remote.getRatedMovies(any())).thenReturn(listOf())
        Mockito.`when`(db.getRatedMovies(any(), any())).thenReturn(listOf())

        val movieRepository = MovieRepository(remote, db)
        val response = movieRepository.getRatedMovies(1)
        assertEquals(response.size,0)
    }

    @Test
    fun `GIVEN empty results from remote source WHEN calling getPopularMovies, and not having cached results THEN should return empty list`() = runBlockingTest {
        val remote = mock<IMovieRemoteDataSource>()
        val db = mock<ILocalMoviesDataSource>()

        Mockito.`when`(remote.getPopularMovies(any())).thenReturn(listOf())
        Mockito.`when`(db.getPopularMovies(any(), any())).thenReturn(listOf())

        val movieRepository = MovieRepository(remote, db)
        val response = movieRepository.getPopularMovies(1)
        assertEquals(response.size,0)
    }

    @Test
    fun `GIVEN a response with one movie WHEN calling getPopularMovies() THEN should save that results and retrieve them from cached source`() = runBlockingTest {
        val remote = mock<IMovieRemoteDataSource>()
        val db = mock<ILocalMoviesDataSource>()

        Mockito.`when`(remote.getPopularMovies(any())).thenReturn(MOVIE_RESPONSE.results)
        Mockito.`when`(db.getPopularMovies(any(), any())).thenReturn(MOVIES)

        val movieRepository = MovieRepository(remote, db)
        val response = movieRepository.getPopularMovies(1)
        assertEquals(response.size,1)
    }

    @Test
    fun `GIVEN a response with one movie WHEN calling getRatedMovies() THEN should save that results and retrieve them from cached source`() = runBlockingTest {
        val remote = mock<IMovieRemoteDataSource>()
        val db = mock<ILocalMoviesDataSource>()

        Mockito.`when`(remote.getRatedMovies(any())).thenReturn(MOVIE_RESPONSE.results)
        Mockito.`when`(db.getRatedMovies(any(), any())).thenReturn(MOVIES)

        val movieRepository = MovieRepository(remote, db)
        val response = movieRepository.getRatedMovies(1)
        assertEquals(response.size,1)
    }

    companion object {

        val MOVIES: List<MovieModel> = listOf(
            MovieModel(
                id = 278,
                poster_path = "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg",
                overview = "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                release_date = "1994-09-10",
                title = "The Shawshank Redemption",
                backdrop_path = "/xBKGJQsAIeweesB79KC89FpBrVr.jpg",
                video = false,
                vote_average = 8.32,
                popularity = 6.741296
            )
        )

        val MOVIE_RESPONSE = MovieResponse(1, MOVIES)
    }
}
package ampuero.joaquin.domain.paging

import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.domain.repositories.IMovieRepository
import androidx.paging.PagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class PopularMoviePagingDataSourceTest{

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN repository response with list of movies WHEN calling load from PagingSource THEN Paging source should reflect load size and key`() =
        runBlockingTest {
            val repositoryMock = mock<IMovieRepository>()
            Mockito.`when`(repositoryMock.getPopularMovies(1)).thenReturn(MOVIES)
            val pagingDataSource = PopularMoviePagingDataSource(repositoryMock)
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = MOVIES,
                    prevKey = 0,
                    nextKey = 2
                ),
                pagingDataSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 1,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    @Throws
    fun `GIVEN repository throwing exception WHEN calling load from PagingSource THEN Paging source should reflect the Error`() = runBlockingTest {
        val repositoryMock = mock<IMovieRepository>()
        val exception = Exception("generic")
        Mockito.`when`(repositoryMock.getPopularMovies(1)).then {
            throw exception
        }
        val pagingDataSource = PopularMoviePagingDataSource(repositoryMock)
        assertEquals(
            PagingSource.LoadResult.Error<Int, MovieEntity>(
                exception
            ),
            pagingDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }


    companion object {
        val MOVIES: List<MovieEntity> = listOf(
            MovieEntity(
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
    }
}
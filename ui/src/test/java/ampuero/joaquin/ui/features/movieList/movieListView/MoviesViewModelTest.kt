package ampuero.joaquin.ui.features.movieList.movieListView

import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.domain.paging.PopularMoviePagingDataSource
import ampuero.joaquin.domain.paging.RatedMoviePagingDataSource
import ampuero.joaquin.domain.repositories.IMovieRepository
import ampuero.joaquin.moviedb.MainCoroutineRule
import androidx.paging.PagingSource
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class MoviesViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun asd() = runBlocking {

        val pagingData: PagingSource.LoadResult<Int, MovieEntity> =
            PagingSource.LoadResult.Page(
                MOVIES, 0,
                nextKey = 2
            )

        val rated = mock<RatedMoviePagingDataSource>()
        `when`(rated.load(any<PagingSource.LoadParams<Int>>())).thenReturn(pagingData)

        val popular = mock<PopularMoviePagingDataSource>()
        `when`(popular.load(any<PagingSource.LoadParams<Int>>())).thenReturn(pagingData)


        val viewModel = MoviesViewModel(popular, rated)
        viewModel.state.collectLatest {
            assertEquals(pagingData,it)
        }
        viewModel.getRatedMovies()

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
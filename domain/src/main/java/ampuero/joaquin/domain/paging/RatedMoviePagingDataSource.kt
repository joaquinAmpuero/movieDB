package ampuero.joaquin.domain.paging

import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.domain.repositories.IMovieRepository
import androidx.paging.PagingSource
import androidx.paging.PagingState

open class RatedMoviePagingDataSource constructor(private val repository: IMovieRepository) :
    PagingSource<Int, MovieEntity>() {

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val page = params.key ?: 1
        return try {
            val response = repository.getRatedMovies(page)
            if (response.isEmpty()) {
                return LoadResult.Error(Exception("SOMETHING WENT WRONG"))
            }
            LoadResult.Page(
                response, prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}

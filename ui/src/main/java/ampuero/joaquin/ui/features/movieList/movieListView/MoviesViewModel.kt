package ampuero.joaquin.ui.features.movieList.movieListView

import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.domain.paging.PopularMoviePagingDataSource
import ampuero.joaquin.domain.paging.RatedMoviePagingDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val popularRatedMoviesViewModel: PopularMoviePagingDataSource,
    private val ratedMoviePagingDataSource: RatedMoviePagingDataSource
) : ViewModel() {

    private var _state = flow<PagingData<MovieEntity>> { emit(PagingData.empty()) }
    val state: Flow<PagingData<MovieEntity>> get() = _state

    private fun getRatedMovies(pagingConfig: PagingConfig = getDefaultPageConfig()):  Pager<Int,MovieEntity> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ratedMoviePagingDataSource }
        )
    }

    private fun getPopularMovies(pagingConfig: PagingConfig = getDefaultPageConfig()): Pager<Int,MovieEntity> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { popularRatedMoviesViewModel }
        )
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 4,
            enablePlaceholders = false
        )
    }

    fun getRatedMovies() {
        viewModelScope.launch {
            _state = getRatedMovies(getDefaultPageConfig()).flow
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            _state = getPopularMovies(getDefaultPageConfig()).flow
        }
    }

}
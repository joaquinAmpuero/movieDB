package ampuero.joaquin.ui.features.movieDetail

import ampuero.joaquin.domain.entities.MovieEntity
import ampuero.joaquin.domain.repositories.IMovieRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieRepository: IMovieRepository) :
    ViewModel() {

    val state: Flow<DetailState> get() = _state
    private var _state = MutableStateFlow<DetailState>(DetailState.Loading)

    sealed class DetailState {
        class Success(val movie: MovieEntity) : DetailState()
        object Error : DetailState()
        object Loading : DetailState()
    }

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            val movie = movieRepository.getMovie(movieId)
            if (movie != null) {
                _state.value = DetailState.Success(movie)
            }else{
                _state.value = DetailState.Error
            }
        }
    }
}
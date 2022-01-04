package ampuero.joaquin.ui.di

import ampuero.joaquin.data.repository.MovieRepository
import ampuero.joaquin.data.api.MovieService
import ampuero.joaquin.data.local.ILocalMoviesDataSource
import ampuero.joaquin.data.remote.MovieRemoteDataSource
import ampuero.joaquin.domain.paging.PopularMoviePagingDataSource
import ampuero.joaquin.domain.paging.RatedMoviePagingDataSource
import ampuero.joaquin.domain.repositories.IMovieRepository
import ampuero.joaquin.data.remote.IMovieRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {

    @Provides
    fun provideMovieRepository(
        movieService: MovieService,
        localDataSource: ILocalMoviesDataSource
    ): IMovieRepository = MovieRepository(provideMovieRemoteDataSource(movieService), localDataSource)

    @Provides
    fun provideMovieRemoteDataSource(movieService: MovieService): IMovieRemoteDataSource =
        MovieRemoteDataSource(movieService)

    @Provides
    fun provideRatedMoviePagingDataSource(movieService: IMovieRepository): RatedMoviePagingDataSource =
        RatedMoviePagingDataSource(movieService)

    @Provides
    fun providePopularMoviePagingDataSource(movieService: IMovieRepository): PopularMoviePagingDataSource =
        PopularMoviePagingDataSource(movieService)

}
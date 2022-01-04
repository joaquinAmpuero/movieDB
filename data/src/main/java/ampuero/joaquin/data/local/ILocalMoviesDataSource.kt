package ampuero.joaquin.data.local

import ampuero.joaquin.data.models.MovieModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ILocalMoviesDataSource {

    @Query("SELECT * FROM movies ORDER BY vote_average DESC LIMIT :count OFFSET :offset")
    suspend fun getRatedMovies(count: Int = 20, offset: Int = 0): List<MovieModel>

    @Query("SELECT * FROM movies WHERE id=:movieId")
    suspend fun getMovie(movieId: Int): MovieModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MovieModel>): List<Long>

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    @Query("SELECT * FROM movies ORDER BY popularity DESC LIMIT :count OFFSET :offset")
    suspend fun getPopularMovies(count: Int, offset: Int): List<MovieModel>
}
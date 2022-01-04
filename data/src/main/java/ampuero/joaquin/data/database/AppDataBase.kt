package ampuero.joaquin.data.database

import ampuero.joaquin.data.local.ILocalMoviesDataSource
import ampuero.joaquin.data.models.MovieModel
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieModel::class],version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun movieDao(): ILocalMoviesDataSource
}
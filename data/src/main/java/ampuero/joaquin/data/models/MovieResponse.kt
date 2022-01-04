package ampuero.joaquin.moviedb.data.models

import ampuero.joaquin.data.models.MovieModel

data class MovieResponse(
    val page:Int,
    val results:List<MovieModel>)
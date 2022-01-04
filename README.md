# movieDB
Example displaying list of movies from themoviedb


Is an example containing a list of featured movies and popular movies.
Was builded with Kotlin using MVVM Architecure and using three layers data, domain and ui. For the local cache I used Room.
There’s unit test in domain and data layer.

* For future updates is going to have a search field displaying the results in another Activity, following the same architecture and repository, datasource.
* For the videos display in detail themoviedb provides a list of videos and the source of them, there’s two options, going to play the video in the platform providing the video, like Youtube. The other option is use Exoplayer and play the videos in a video activity inside the Application.

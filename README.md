# movieDB
Example containing a list of featured movies and popular movies.

## Architecture.
I used MVVM Arquitectural pattern following Clean Architecture, the project is divided in three modules, Domain, Data and Ui.

### Domain
Domain module is the inner layer in the Clean Architecture, is coded only in Kotlin and don't have Android dependencies.
In this module I defined the Entity ( MoviesEntitiy ) the repository interface and the PagingSources(Paging3 library, androidx.paging:paging-common-ktx, doesn’t have android dependencies).

This Paging source consume the repository interface methods to get popular and rated movies and create a PagingSource flow with the data retrived from the repository.

I Added unit test for PagingSources, using mockito.

#### Libraries used in this domain:
- paging-common
- kotlin
- coroutines
- mockito

### Data
Data module have domain dependency and is the module that handle the data sources, in this case : remote(themovieDb) and local(db).

#### Remote data source
For this data source(IMovieRemoteDataSource) I used Retrofit and created a module with this implementation using Hilt for the dependency injections and usage of this, and suspended functions to handle the asynchronous calls.
There’s a interface MovieService that has the retrofit definitions of the get classes that are implemented, for get the rated movies, and popular movies.
This remote data source was unit tested.

#### Local data source
This data source is a local database implemented using room, has a single table saving the MovieModel in the local database, and provide the same methods that remote data source.

#### RepositoryImplementation
This implements the interface definition that was created in **Domain** module, hold the logic for handling the call for the remote data source, saving that in the local data source (DB) and retrieving from the local data source, to handle only one source of data.
This was unit tested using mockito handling the different test cases for when we have success response, empty responses, etc.

#### libraries used
- room
- retrofit
- hilt 
- coroutines
- mockito
- domain

### UI
This module have use the MVVM arquitectural pattern, and use ViewModel from Android Jetpack, corotuines, flows, Paging3 library, ConstraintLayout.
This module has 2 features, the lists and detail

#### Movie list feature.
This feature has a viewpager, builded with ViewPager2 Library. that display two fragments that display list of Popular movies and Most Rated movies.
This Fragment is injecting the viewmodel that uses the Paging sources from domain that build flow of data for rated and popular movies.
The fragment class is collecting the flows from this PagingSources and handling the different states for loading the data and handling the errors and loading states.
this view use a recyclerview for the list and a pagingAdapter with the respective viewHolder, displaying a poster image and the name of the movie

#### Movie detail feature.

This feature show the name of the movie, the description, rating and a backdrop image, Also use Coordinator layout for having parallax animation when scrolling.
This Activity receive the id of the movie, and retrieve the data from the repository the get the data from the local source.

#### libraries used
- room
- retrofit
- hilt 
- coroutines
- mockito
- paging3
- constraintlayout
- material
- glide
- viepager2
- viewmodel
- gson
- domain
- data

### Future developments

#### Search
For this feature I will do the following:
* I will add to the repository interface in **Domain** module, a method definition to get movies from a string to search.
* Add the method to get searched results in the remote datasource in **Data** module
* Add the method to get searched results in the local datasource **Data** module
* Add the method to handle the call to the remote datasource and saving and retrieving from the local datasource. **Data** module
* Unit test this datasource and repository implementation **Data** module
* Create a PagingSource for the search results in  **Domain** module
* Implement a search component in the MoviesActivity class and launching a activity result with a recyclerview and pagingAdapter showing the results of the search, using the same logic and implementations that the other list, I will reuse the same MoviesFragment.

#### Video
* I will add to the repository interface in **Domain** module, a method definition to get videos for a movie 
* Add the method to get videos for a movie in the remote datasource in **Data** module
* Add the method to get videos for a movie in the local datasource **Data** module
* Add the method to handle the call to the remote datasource and saving and retrieving from the local datasource. **Data** module
* Unit test this datasource and repository implementation **Data** module
* Create an Adapter and viewHolder for the search results in **UI** module
* Add a recyclerview to the Detail view that use this adapter that display the videos for a movie. in **UI** module
* Implement an Exoplayer Activity for playing videos within the application.
* Pass a video url when the user tap on a video from the list in the detail activity, will take you to the exoplayer activity that will play the video.


package ampuero.joaquin.ui.features.movieList.movieListView

import ampuero.joaquin.ui.features.movieDetail.MovieDetailActivity
import ampuero.joaquin.ui.features.movieList.adapter.MovieListAdapter
import ampuero.joaquin.ui.features.movieList.adapter.MovieLoadStateAdapter
import ampuero.joaquin.ui.databinding.ActivityMovieListBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment :  Fragment(), MovieListAdapter.MovieCLickedListener {

    lateinit var adapter: MovieListAdapter

    private lateinit var binding: ActivityMovieListBinding
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        adapter = MovieListAdapter()
        binding = ActivityMovieListBinding.inflate(inflater,container,false)
        if(arguments!=null && arguments!!.getBoolean(RATED)){
            viewModel.getRatedMovies()
        }else {
            viewModel.getPopularMovies()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        viewModelStates()

    }

    private fun setAdapter() {
        val footer = MovieLoadStateAdapter { adapter.retry() }
        val concatAdapter = ConcatAdapter(
            ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
            adapter.withLoadStateFooter(footer),
            MovieLoadStateAdapter { adapter.retry() }
        )

        binding.movieRV.layoutManager = GridLayoutManager(activity, 2).apply {
            spanSizeLookup = spanSizeLookup(concatAdapter)
        }

        binding.movieRV.adapter = concatAdapter
        handleError()
        adapter.setOnClickListener(this)
    }

    private fun spanSizeLookup(concatAdapter: ConcatAdapter) =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (concatAdapter.getItemViewType(position) == 1) {
                    2
                } else {
                    1
                }
            }
        }

    private fun handleError() {
        lifecycleScope.launchWhenResumed {
            adapter.loadStateFlow.collectLatest { loadState ->
                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                if (error != null) {
                    binding.movieEmptyState.visibility = View.VISIBLE
                } else {
                    binding.movieEmptyState.visibility = View.GONE
                }
                if (loadState.refresh is LoadState.Loading) {
                    binding.movieProgress.visibility = View.VISIBLE
                } else {
                    binding.movieProgress.visibility = View.GONE
                }
            }
        }
    }

    private fun viewModelStates() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { data ->
                binding.movieRV.visibility = View.VISIBLE
                binding.movieProgress.visibility = View.GONE
                binding.movieEmptyState.visibility = View.GONE
                adapter.submitData(data)
            }
        }
    }

    override fun onMovieSelected(movieId: Int) {
        activity?.let { MovieDetailActivity.startDetailActivity(movieId, it) }
    }

    companion object {
        private const val RATED = "RATED"
        fun newInstancePopular(): MoviesFragment {
            return MoviesFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(RATED, true)
                }
            }
        }

        fun newInstanceRated(): MoviesFragment {
            return MoviesFragment()
        }
    }
}
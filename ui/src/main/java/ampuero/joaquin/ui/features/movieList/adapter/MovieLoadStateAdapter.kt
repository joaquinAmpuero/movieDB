package ampuero.joaquin.ui.features.movieList.adapter

import ampuero.joaquin.ui.databinding.ItemLoadStateBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class MovieLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState,retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    class LoadStateViewHolder(private val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState, retry: () -> Unit) {
            val progress = binding.loadStateProgress
            val btnRetry = binding.loadStateRetry
            val txtErrorMessage = binding.loadStateErrorMessage

            btnRetry.isVisible = loadState !is LoadState.Loading
            txtErrorMessage.isVisible = loadState !is LoadState.Loading
            progress.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error){
                txtErrorMessage.text = loadState.error.localizedMessage
            }

            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

    }
}
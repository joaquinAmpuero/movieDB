package ampuero.joaquin.ui.features.movieList

import ampuero.joaquin.ui.features.movieList.adapter.AppViewPagerAdapter
import ampuero.joaquin.ui.databinding.ActivityMovieBinding
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : FragmentActivity() {

    lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = AppViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabs, binding.viewPager
        ) { tab, position ->
            if(position==0){
                tab.text = "Most Rated"
            }else{
                tab.text = "Most Popular"
            }
        }.attach()
    }
}
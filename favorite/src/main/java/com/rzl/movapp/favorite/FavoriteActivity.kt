package com.rzl.movapp.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rzl.movapp.core.ui.PopularAdapter
import com.rzl.movapp.detail.DetailActivity
import com.rzl.movapp.favorite.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding
    private val favoriteViewModel : FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadKoinModules(favoriteModule)


        val popularAdapter = PopularAdapter()
        popularAdapter.onItemClick = {selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }


        favoriteViewModel.favoritePopuler.observe(this,{ dataPopular ->

            popularAdapter.setAllPopularList(dataPopular)

        })

        with(binding.rvFavorite){
            layoutManager  = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = popularAdapter
        }
    }
}
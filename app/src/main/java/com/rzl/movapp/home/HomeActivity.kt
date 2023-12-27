package com.rzl.movapp.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rzl.movapp.core.data.Resource
import com.rzl.movapp.core.ui.PopularAdapter
import com.rzl.movapp.databinding.ActivityHomeBinding
import com.rzl.movapp.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    private val homeViewModel : HomeViewModel by viewModel()

    private val popularAdapter = PopularAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnFavorite.setOnClickListener{
            val uri = Uri.parse("movapp://favorite")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }


        popularAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        homeViewModel.popular.observe(this, { popular ->
            Log.d("HomePage", "Data observed: ${Gson().toJson(popular)}")
            if (popular != null) {
                when (popular) {

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        popularAdapter.setAllPopularList(popular.data)

                        Log.d("HomePage", "Data loaded successfully: ${popular.data}")
                        Log.d("HomePage", "Full Response: ${Gson().toJson(popular)}")

                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Log.d("HomePage", "Loading data...")
                    }



                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Log.e("HomePage", "Error loading data: ${popular.message}")
                    }
                }
            }
        })
        with(binding.rvHome) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = popularAdapter
        }

    }
}
package com.rzl.movapp.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.rzl.movapp.R
import com.rzl.movapp.databinding.ActivityDetailBinding
import com.rzl.movapp.core.domain.model.Popular
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel : DetailViewModel by viewModel()





    companion object{
        const val EXTRA_DATA = "EXTRA_DATA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailPopular = intent.getParcelableExtra<Popular>(EXTRA_DATA)
        showDetailPopular(detailPopular)


    }

    private fun showDetailPopular(detailPopular: Popular?) {
            detailPopular?.let {
                binding.tvDeskripsi.text = detailPopular.overview
                binding.tvTitle.text = detailPopular.title
                binding.tvRealeseDate.text = detailPopular.releaseDate
                Glide.with(this@DetailActivity)
                    .load("https://image.tmdb.org/t/p/w780/${detailPopular.backdropPath}")
                    .into(binding.imgBackdrop)


                var statusFavorite = detailPopular.isFavorite
                setStatusFavorite(statusFavorite)
                binding.btnFavorite.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailViewModel.setFavoritePopular(detailPopular, statusFavorite)
                    setStatusFavorite(statusFavorite)
                }

            }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_favorite))
        } else {
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_favorite_border))
        }
    }


}
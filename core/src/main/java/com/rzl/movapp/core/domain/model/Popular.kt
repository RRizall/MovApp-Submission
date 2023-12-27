package com.rzl.movapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Popular(
    val id: Int,
    val overview: String,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    var isFavorite: Boolean
):Parcelable
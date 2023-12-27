package com.rzl.movapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListPopularResponse(
	@SerializedName("results")
	val results: List<PopularResponse>
)

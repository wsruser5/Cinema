package com.mrz.worldcinema.data

data class LastVideoInfo(
    val age: String,
    val description: String,
    val images: List<String>,
    val movieId: String,
    val name: String,
    val poster: String,
    val tags: List<TagX>
)
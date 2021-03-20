package com.mrz.worldcinema.data

data class MoviesListItem(
    val age: String,
    val description: String,
    val images: List<String>,
    val movieId: String,
    val name: String,
    val poster: String,
    val tags: List<Tag>
)
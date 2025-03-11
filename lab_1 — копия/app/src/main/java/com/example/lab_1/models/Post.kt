package com.example.lab_1.models

data class Post(
    val id: Int,
    val text: String,
    val imageUrl: String?,
    var likes: Int,
    var comments: Int,
    var isLikedByAdmin: Boolean = false
)
package com.stevecampos.composecleansample.data.entities

data class GetPostsResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

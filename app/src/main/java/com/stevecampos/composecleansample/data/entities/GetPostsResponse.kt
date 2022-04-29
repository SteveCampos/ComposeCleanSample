package com.stevecampos.composecleansample.data.entities

import androidx.room.Entity

data class GetPostsResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

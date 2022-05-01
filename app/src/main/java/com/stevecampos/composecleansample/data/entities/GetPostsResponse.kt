package com.stevecampos.composecleansample.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GetPostsResponse(
    val userId: Int,
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String
)

package com.stevecampos.composecleansample.data.local

import com.stevecampos.composecleansample.data.entities.GetPostsRequest
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getUsers(request: GetUsersRequest): Flow<List<GetUsersResponse>>
    suspend fun addUsers(users: List<GetUsersResponse>)

    fun getPosts(request: GetPostsRequest): Flow<List<GetPostsResponse>>
    suspend fun addPosts(posts: List<GetPostsResponse>)
}
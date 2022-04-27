package com.stevecampos.composecleansample.data

import com.stevecampos.composecleansample.data.entities.GetPostsRequest
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import kotlinx.coroutines.flow.Flow

interface DataSource {

    fun getUsers(request: GetUsersRequest): Flow<Result<List<GetUsersResponse>>>
    fun getPosts(request: GetPostsRequest): Flow<Result<List<GetPostsResponse>>>
}
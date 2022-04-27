package com.stevecampos.composecleansample.data.repository

import com.stevecampos.composecleansample.data.DataSource
import com.stevecampos.composecleansample.data.entities.GetPostsRequest
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    //private val local: DataSource,
    private val remote: DataSource
) : DataSource {
    override fun getUsers(request: GetUsersRequest): Flow<Result<List<GetUsersResponse>>> {
        return remote.getUsers(request)
    }

    override fun getPosts(request: GetPostsRequest): Flow<Result<List<GetPostsResponse>>> {
        return remote.getPosts(request)
    }
}